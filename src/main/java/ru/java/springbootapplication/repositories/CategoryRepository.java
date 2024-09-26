package ru.java.springbootapplication.repositories;

import org.springframework.stereotype.Repository;
import ru.java.springbootapplication.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String title);
}
