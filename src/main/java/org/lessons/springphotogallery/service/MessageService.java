package org.lessons.springphotogallery.service;

import org.lessons.springphotogallery.model.Message;
import org.lessons.springphotogallery.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    public Message create(Message message) throws IllegalArgumentException {
        Message messageToPersist = new Message();
        messageToPersist.setId(message.getId());
        messageToPersist.setEmail(message.getEmail());
        messageToPersist.setTesto(message.getTesto());
        return messageRepository.save(messageToPersist);
    }

    public List<Message> getAll(Optional<String> keywordOpt) {
        if (keywordOpt.isEmpty()) {
            return messageRepository.findAll();
        } else {
            return messageRepository.findByEmail(keywordOpt.get());
        }
    }

    public Message getById(Integer id) throws ChangeSetPersister.NotFoundException {
        Optional<Message> messageOpt = messageRepository.findById(id);
        if (messageOpt.isPresent()) {
            return messageOpt.get();
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    private boolean isUniqueId(Message formMessage) {
        Optional<Message> result = messageRepository.findById(formMessage.getId());
        return result.isEmpty();
    }
}
