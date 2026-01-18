package com.localsphere.server.controller;

import com.localsphere.server.data.ChatMessage;
import com.localsphere.server.data.User;
import com.localsphere.server.dto.ChatMessageDto;
import com.localsphere.server.dto.CreateChatMessageRequest;
import com.localsphere.server.dto.CreateUserRequest;
import com.localsphere.server.repository.ChatMessageRepository;
import com.localsphere.server.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatController(UserRepository userRepository, ChatMessageRepository chatMessageRepository) {
        this.userRepository = userRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/users/{userId}/messages")
    public ResponseEntity<List<ChatMessageDto>> getMessages(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        List<ChatMessage> messages = chatMessageRepository.findByUser(user);
        List<ChatMessageDto> messageDtos = messages.stream()
                .map(message -> new ChatMessageDto(message.getId(), message.getMessage(), message.getTimestamp()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(messageDtos);
    }

    @PostMapping("/users/{userId}/messages")
    public ResponseEntity<ChatMessageDto> postMessage(@PathVariable Long userId, @RequestBody CreateChatMessageRequest createChatMessageRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUser(user);
        chatMessage.setMessage(createChatMessageRequest.getMessage());
        chatMessage.setTimestamp(LocalDateTime.now());

        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        ChatMessageDto messageDto = new ChatMessageDto(savedMessage.getId(), savedMessage.getMessage(), savedMessage.getTimestamp());
        return ResponseEntity.ok(messageDto);
    }
}
