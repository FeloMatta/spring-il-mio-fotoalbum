package org.lessons.springphotogallery.repository;

import org.lessons.springphotogallery.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}