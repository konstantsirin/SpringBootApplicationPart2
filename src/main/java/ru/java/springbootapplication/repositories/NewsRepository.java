package ru.java.springbootapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.java.springbootapplication.entity.News;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByCategoryId(Long categoryId);
}
