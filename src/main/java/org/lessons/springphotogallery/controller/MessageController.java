package org.lessons.springphotogallery.controller;

import org.lessons.springphotogallery.model.Message;
import org.lessons.springphotogallery.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageRepository messageRepository;

    @GetMapping
    public String list(Model model) {
        List<Message> message = messageRepository.findAll();
        model.addAttribute("messageList", message);
        return "/message/index";
    }
}
