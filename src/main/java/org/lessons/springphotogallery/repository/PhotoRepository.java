package org.lessons.springphotogallery.repository;

import org.lessons.springphotogallery.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    List<Photo> findByTitle(String title);

    List<Photo> findByTitleContainingIgnoreCase(String title);
}