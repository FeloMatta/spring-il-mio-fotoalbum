package org.lessons.springphotogallery.controller;

import org.lessons.springphotogallery.model.Photo;
import org.lessons.springphotogallery.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/photos")
public class PhotoController {
    @Autowired
    private PhotoRepository photoRepository;

    @GetMapping
    public String list(Model model) {
        List<Photo> photos = photoRepository.findAll();
        model.addAttribute("photoList", photos);
        return "/photos/list";
    }
}
