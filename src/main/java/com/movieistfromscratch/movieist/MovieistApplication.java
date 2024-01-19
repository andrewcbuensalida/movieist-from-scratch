package com.movieistfromscratch.movieist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication // This single annotation is equivalent to using @Configuration, @EnableAutoConfiguration, and @ComponentScan. As a result, when we run this Spring Boot application, it will automatically scan the components in the current package and its sub-packages. Thus it will register them in Spring’s Application Context, and allow us to inject beans using @Autowired. we can use autowiring on properties, setters, and constructors. When we annotate a property using @Autowired, this searches the spring container for any objects (aka beans) aka classes that are annotated with @Component. If it was annotated with @Component("laptop1"), can use @Autowired @Qualifier("laptop1") to specify which object, in case there are more than one.
@RestController
public class MovieistApplication {

  public static void main(String[] args) {
    SpringApplication.run(MovieistApplication.class, args);
  } // run creates a spring container which will create an object (aka bean) of the classes that you annotate with @Component. if you annotate the class with @Scope(value="prototype"), it will only create an object if you context.getBean it.
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(false).maxAge(3600);
      }
    };
  }
}
