"use client";

import { useState } from 'react';

export default function Home() {
  const [messages, setMessages] = useState([
    { id: 1, text: "Hello! How can I help you today?", sender: "bot" },
    { id: 2, text: "I want to create a logo.", sender: "user" },
    { id: 3, text: "Sure, what kind of logo are you looking for?", sender: "bot" },
  ]);
  const [inputText, setInputText] = useState("");
  const [selectedModel, setSelectedModel] = useState("Ollama");

  const handleSendMessage = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (inputText.trim() === "") return;

    const newMessage = {
      id: messages.length + 1,
      text: inputText,
      sender: "user",
    };
    setMessages([...messages, newMessage]);
    setInputText("");

    // Simulate bot response
    setTimeout(() => {
      const botResponse = {
        id: messages.length + 2,
        text: `You selected ${selectedModel}. Here is a response for "${inputText}"`,
        sender: "bot",
      };
      setMessages((prevMessages) => [...prevMessages, botResponse]);
    }, 1000);
  };

  return (
    <div className="flex h-screen flex-col bg-gray-900 text-white">
      <header className="flex items-center justify-between border-b border-gray-700 p-4">
        <h1 className="text-xl font-bold">LocalSphere</h1>
        <div className="flex items-center gap-4">
          <span className="text-sm">Model:</span>
          <select
            value={selectedModel}
            onChange={(e) => setSelectedModel(e.target.value)}
            className="rounded-md bg-gray-800 px-3 py-1 text-white"
          >
            <option value="Ollama">Ollama</option>
            <option value="Hugging Face">Hugging Face</option>
            <option value="Gemini">Gemini</option>
            <option value="OpenAI">OpenAI</option>
            <option value="Claude">Claude</option>
          </select>
        </div>
      </header>
      <main className="flex-1 overflow-y-auto p-6">
        <div className="space-y-4">
          {messages.map((message) => (
            <div
              key={message.id}
              className={`flex ${
                message.sender === "user" ? "justify-end" : "justify-start"
              }`}
            >
              <div
                className={`max-w-lg rounded-lg px-4 py-2 ${
                  message.sender === "user"
                    ? "bg-blue-600"
                    : "bg-gray-700"
                }`}
              >
                {message.text}
              </div>
            </div>
          ))}
        </div>
      </main>
      <footer className="border-t border-gray-700 p-4">
        <form onSubmit={handleSendMessage} className="flex items-center gap-4">
          <input
            type="text"
            value={inputText}
            onChange={(e) => setInputText(e.target.value)}
            placeholder="Type your message..."
            className="flex-1 rounded-md bg-gray-800 px-4 py-2 text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <button
            type="submit"
            className="rounded-md bg-blue-600 px-4 py-2 font-bold text-white hover:bg-blue-700"
          >
            Send
          </button>
        </form>
      </footer>
    </div>
  );
}
