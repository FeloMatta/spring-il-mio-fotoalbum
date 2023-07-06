package org.lessons.springphotogallery.repository;

import org.lessons.springphotogallery.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {

}