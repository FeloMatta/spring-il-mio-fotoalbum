package org.lessons.springphotogallery.controller;

import jakarta.validation.Valid;
import org.lessons.springphotogallery.model.Photo;
import org.lessons.springphotogallery.repository.CategoryRepository;
import org.lessons.springphotogallery.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {
    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "/photos/edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("photo") Photo formPhoto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryList", categoryRepository.findAll());
            return "/photos/edit";
        }
        photoRepository.save(formPhoto);
        return "redirect:/photos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Photo photo = getPhotoById(id);
        model.addAttribute("photo", photo);
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "/photos/edit";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(
            @PathVariable Integer id,
            @Valid @ModelAttribute("photo") Photo formPhoto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        Photo photoToEdit = getPhotoById(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryList", categoryRepository.findAll());
            return "/photos/edit";
        }

        formPhoto.setId(photoToEdit.getId());
        photoRepository.save(formPhoto);

        return "redirect:/photos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Photo photoToDelete = getPhotoById(id);
        photoRepository.delete(photoToDelete);
        return "redirect:/photos";
    }


    private Photo getPhotoById(Integer id) {
        Optional<Photo> result = photoRepository.findById(id);

        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "photo with id " + id + " not found");
        }
        return result.get();
    }


}
