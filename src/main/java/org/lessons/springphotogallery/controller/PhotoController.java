package org.lessons.springphotogallery.controller;

import jakarta.validation.Valid;
import org.lessons.springphotogallery.model.Photo;
import org.lessons.springphotogallery.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {
    @Autowired
    private PhotoRepository photoRepository;

//    @GetMapping
//    public String list(Model model) {
//        List<Photo> photos = photoRepository.findAll();
//        model.addAttribute("photoList", photos);
//        return "/photos/list";
//    }

    @GetMapping
    public String list(
            @RequestParam(name = "keyword", required = false) String searchString, Model model) {
        List<Photo> photos;

        if (searchString == null || searchString.isBlank()) {
            photos = photoRepository.findAll();
        } else {
            photos = photoRepository.findByTitleContainingIgnoreCase(searchString);
        }

        model.addAttribute("photoList", photos);
        model.addAttribute("searchInput", searchString == null ? "" : searchString);
        return "/photos/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer photoId, Model model) {
        Optional<Photo> result = photoRepository.findById(photoId);
        if (result.isPresent()) {
            model.addAttribute("photo", result.get());
            return "/photos/detail";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo with id" + photoId + " not found");
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("photo", new Photo());
        return "/photos/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("photo") Photo formPhoto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/photos/create";
        }
        photoRepository.save(formPhoto);
        return "redirect:/photos";
    }

}
