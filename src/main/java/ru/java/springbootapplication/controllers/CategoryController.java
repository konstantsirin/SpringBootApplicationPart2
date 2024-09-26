package ru.java.springbootapplication.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.java.springbootapplication.dto.CategoryDto;
import ru.java.springbootapplication.services.CategoryCRUDService;

import java.util.Collection;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryCRUDService categoryCRUDService;

    public CategoryController(CategoryCRUDService categoryCRUDService) {
        this.categoryCRUDService = categoryCRUDService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {return categoryCRUDService.getById(id);}

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<CategoryDto> getAllCategory() {
        return categoryCRUDService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryCRUDService.create(categoryDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) {
        System.out.println(categoryDto);
        return categoryCRUDService.update(categoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return categoryCRUDService.delete(id);
    }

}
