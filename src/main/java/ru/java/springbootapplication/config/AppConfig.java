package ru.java.springbootapplication.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
@EnableJpaAuditing
public class AppConfig {
    private String name;

    @PostConstruct
    public void printConfig() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "name='" + name + '\'' +
                '}';
    }
}
