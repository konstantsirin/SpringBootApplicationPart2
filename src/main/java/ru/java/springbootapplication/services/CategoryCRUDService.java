package ru.java.springbootapplication.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.java.springbootapplication.dto.CategoryDto;
import ru.java.springbootapplication.entity.Category;
import ru.java.springbootapplication.repositories.CategoryRepository;
import ru.java.springbootapplication.repositories.NewsRepository;

import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryCRUDService implements CRUDService<CategoryDto> {

    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> getById(Long id) {
        log.info("Get category by ID " + id);
        try {
            Category category = categoryRepository.findById(id).orElseThrow();
            return ResponseEntity.ok(mapToDto(category));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Категория с ID " + id + " не найдена"));
        }

    }

    @Override
    public Collection<CategoryDto> getAll() {
        log.info("Get all category");
        return categoryRepository.findAll()
                .stream()
                .map(CategoryCRUDService::mapToDto)
                .toList();
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        log.info("Create category");
        Category category = mapToEntity(categoryDto);
        categoryRepository.save(category);
        return mapToDto(category);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        log.info("Update category");
        Category category = mapToEntity(categoryDto);
        categoryRepository.save(category);
        return mapToDto(category);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        log.info("Delete category by id " + id);
        try {
            categoryRepository.findById(id).orElseThrow();
            categoryRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NewsCRUDService.ErrorMessage("Категоря с ID " + id + " не найдена"));
        }

    }

    public static CategoryDto mapToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());
        return categoryDto;
    }

    public static Category mapToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setTitle(categoryDto.getTitle());

        return category;
    }

    @Getter
    static class ErrorMessage {
        private final String message;

        public ErrorMessage(String message) {
            this.message = message;
        }
    }
}
