package org.bolivar.socketchat.service;

import java.util.Optional;

import org.bolivar.socketchat.entity.ChatDetails;
import org.bolivar.socketchat.repository.ChatDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatDetailsService {

    @Autowired
    private ChatDetailsRepository repository;

    public ChatDetails getByChatAndUser(Integer chatId, Integer userId){
        Optional<ChatDetails> details = repository.findByChatIdAndUserId(chatId, userId);
        if(details.isEmpty()) return null;
        return details.get();
    }

    public ChatDetails updateChatDetails (ChatDetails newDetails, Integer chatId, Integer userId) {
        Optional<ChatDetails> oldDetails = repository.findByChatIdAndUserId(chatId, userId);

        if(oldDetails.isEmpty()) return null;

        if(newDetails.getMaxMessageId() != null) {
            oldDetails.get().setMaxMessageId(newDetails.getMaxMessageId());
            repository.save(oldDetails.get());
        }

        return oldDetails.get();
    }
}
