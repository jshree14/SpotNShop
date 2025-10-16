package com.spotnshop.service;

import com.spotnshop.model.ChatMessage;
import com.spotnshop.model.User;
import com.spotnshop.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ChatService {
    
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    
    public ChatMessage sendMessage(User sender, User receiver, String message) {
        ChatMessage chatMessage = new ChatMessage(sender, receiver, message);
        return chatMessageRepository.save(chatMessage);
    }
    
    public List<ChatMessage> getConversation(User user1, User user2) {
        return chatMessageRepository.findConversationBetweenUsers(user1, user2);
    }
    
    public List<User> getChatPartners(User user) {
        return chatMessageRepository.findChatPartners(user);
    }
    
    public Long getUnreadMessageCount(User user) {
        return chatMessageRepository.countUnreadMessages(user);
    }
    
    @Transactional
    public void markMessagesAsRead(User sender, User receiver) {
        List<ChatMessage> unreadMessages = chatMessageRepository.findConversationBetweenUsers(sender, receiver)
                .stream()
                .filter(msg -> msg.getReceiver().equals(receiver) && !msg.isRead())
                .toList();
        
        for (ChatMessage message : unreadMessages) {
            message.setRead(true);
            chatMessageRepository.save(message);
        }
    }
}