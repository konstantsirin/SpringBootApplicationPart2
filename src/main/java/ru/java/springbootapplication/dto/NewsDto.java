package ru.java.springbootapplication.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewsDto {
    private Long id;
    private String title;
    private String text;
    @JsonIgnore
    @JsonProperty("category_id")
    private Long categoryId;
    private String category;
    private Instant date;
}
