package com.localsphere.server.repository;

import com.localsphere.server.data.ChatMessage;
import com.localsphere.server.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByUser(User user);
}
