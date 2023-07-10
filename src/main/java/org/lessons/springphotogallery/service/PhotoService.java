package org.lessons.springphotogallery.service;

import org.lessons.springphotogallery.exceptions.PhotoNotFoundException;
import org.lessons.springphotogallery.model.Photo;
import org.lessons.springphotogallery.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {
    @Autowired
    PhotoRepository photoRepository;

    public List<Photo> getAll(Optional<String> keywordOpt) {
        if (keywordOpt.isEmpty()) {
            return photoRepository.findAll();
        } else {
            return photoRepository.findByTitle(keywordOpt.get());
        }
    }

    public Photo getById(Integer id) throws PhotoNotFoundException {
        Optional<Photo> photoOpt = photoRepository.findById(id);
        if (photoOpt.isPresent()) {
            return photoOpt.get();
        } else {
            throw new PhotoNotFoundException("Photo with id" + id);
        }
    }

    public Photo create(Photo photo) {
        Photo photoToPersist = new Photo();
        photoToPersist.setTitle(photo.getTitle());
        photoToPersist.setDescription(photo.getDescription());
        photoToPersist.setUrl(photo.getUrl());
        return photoRepository.save(photoToPersist);
    }
}
