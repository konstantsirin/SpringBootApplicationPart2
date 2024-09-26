package ru.java.springbootapplication.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.java.springbootapplication.dto.NewsDto;
import ru.java.springbootapplication.entity.Category;
import ru.java.springbootapplication.entity.News;
import ru.java.springbootapplication.repositories.CategoryRepository;
import ru.java.springbootapplication.repositories.NewsRepository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
public class NewsCRUDService implements CRUDService<NewsDto> {

    private final CategoryRepository categoryRepository;
    private final NewsRepository newsRepository;

    @Override
    public ResponseEntity<?> getById(Long id) {
        log.info("Get news by ID: " + id);
        try {
            News news = newsRepository.findById(id).orElseThrow();
            return ResponseEntity.ok(mapToDto(news));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CategoryCRUDService.ErrorMessage("Новость с ID " + id + " не найдена"));
        }
    }

    public ResponseEntity<?> getNewsByCategoryId(Long categoryId) {
        log.info("Get news by category ID: " + categoryId);
        try {
            List<NewsDto> news = newsRepository.findByCategoryId(categoryId)
                    .stream()
                    .map(NewsCRUDService::mapToDto)
                    .toList();

            return ResponseEntity.ok(news);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CategoryCRUDService.ErrorMessage("Новости с ID категории " + categoryId + " не найдена"));
        }
    }

    @Override
    public Collection<NewsDto> getAll() {
        log.info("Get all news");
        return newsRepository.findAll()
                .stream()
                .map(NewsCRUDService::mapToDto)
                .toList();
    }

    @Override
    public NewsDto create(NewsDto newsDto) {
        log.info("Create news");
        News news = mapToEntity(newsDto);
        Category category = findCategory(newsDto);
        news.setCategory(category);
        newsRepository.save(news);
        return mapToDto(news);
    }

    @Override
    public NewsDto update(NewsDto newsDto) {
        log.info("Update news");
        News news = mapToEntity(newsDto);
        Category category = findCategory(newsDto);
        news.setCategory(category);
        news.setDate(Instant.now());
        newsRepository.save(news);
        return mapToDto(news);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        log.info("Delete news by id " + id);
        try {
            newsRepository.findById(id).orElseThrow();
            newsRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Новость с ID " + id + " не найдена"));
        }

    }

    public static NewsDto mapToDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setCategoryId(news.getCategory().getId());
        if (news.getCategory() != null) {
            newsDto.setCategory(news.getCategory().getTitle());
        }
        newsDto.setTitle(news.getTitle());
        newsDto.setText(news.getText());
        newsDto.setDate(news.getDate());
        return newsDto;
    }

    public static News mapToEntity(NewsDto newsDto) {
        News news = new News();
        news.setId(newsDto.getId());
        news.setTitle(newsDto.getTitle());
        news.setText(newsDto.getText());
        news.setDate(newsDto.getDate());
        return news;
    }

    @Getter
    static class ErrorMessage {
        private final String message;

        public ErrorMessage(String message) {
            this.message = message;
        }
    }

    private Category findCategory(NewsDto newsDto) {
        Category category = null;
        Long categoryId = newsDto.getCategoryId();
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId).orElseThrow();
        } else if (newsDto.getCategory() != null) {
            category = categoryRepository.findByTitle(newsDto.getCategory()).orElseThrow();
        } else {
            throw new RuntimeException("Не передан один из обязательных параметров Category Id или Category Title");
        }

        return category;
    }
}
