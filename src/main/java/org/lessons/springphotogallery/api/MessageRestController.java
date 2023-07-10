package org.lessons.springphotogallery.api;

import jakarta.validation.Valid;
import org.lessons.springphotogallery.exceptions.PhotoNotFoundException;
import org.lessons.springphotogallery.model.Message;
import org.lessons.springphotogallery.repository.MessageRepository;
import org.lessons.springphotogallery.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/message")
public class MessageRestController {
    @Autowired
    private MessageRepository messagerepository;
    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<Message> index(@RequestParam Optional<String> keyword) {
        return messageService.getAll(keyword);
    }

    @GetMapping("/{id}")
    public Message get(@PathVariable Integer id) {
        try {
            return messageService.getById(id);
        } catch (PhotoNotFoundException | ChangeSetPersister.NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        return messageService.create(message);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        messagerepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Message update(@PathVariable Integer id, @Valid @RequestBody Message message) {
        message.setId(id);
        return messagerepository.save(message);
    }
}
