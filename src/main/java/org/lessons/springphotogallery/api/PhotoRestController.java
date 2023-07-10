package org.lessons.springphotogallery.api;

import jakarta.validation.Valid;
import org.lessons.springphotogallery.exceptions.PhotoNotFoundException;
import org.lessons.springphotogallery.model.Photo;
import org.lessons.springphotogallery.repository.PhotoRepository;
import org.lessons.springphotogallery.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/photos")
public class PhotoRestController {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoService photoService;

    @GetMapping
    public List<Photo> index(@RequestParam Optional<String> keyword) {
        return photoService.getAll(keyword);
    }

    @GetMapping("/{id}")
    public Photo get(@PathVariable Integer id) {
        try {
            return photoService.getById(id);
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Photo create(@Valid @RequestBody Photo photo) {
        return photoService.create(photo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        photoRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Photo update(@PathVariable Integer id, @Valid @RequestBody Photo photo) {
        photo.setId(id);
        return photoRepository.save(photo);
    }

    @GetMapping("/page")
    public Page<Photo> page(
            Pageable pageable
    ) {
        return photoRepository.findAll(pageable);
    }
}
