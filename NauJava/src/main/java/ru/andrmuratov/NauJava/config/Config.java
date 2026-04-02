package ru.andrmuratov.NauJava.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;
import ru.andrmuratov.NauJava.entity.Task;
import ru.andrmuratov.NauJava.console.CommandProcessor;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Config {

    @Value("${app.name:TaskFlow}")
    private String appName;

    @Value("${app.version:1.0.0}")
    private String appVersion;

    @PostConstruct
    public void init() {
        System.out.println("App: " + appName + " v" + appVersion);
    }

    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<Task> taskContainer() {
        return new ArrayList<>();
    }

//    @Bean
//    public CommandLineRunner run(CommandProcessor processor) {
//        return args -> processor.start();
//    }
}