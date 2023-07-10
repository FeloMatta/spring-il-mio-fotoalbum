package org.lessons.springphotogallery.repository;

import org.lessons.springphotogallery.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByEmail(String email);
}
