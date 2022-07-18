package com.borovyk.trimmed;

import com.borovyk.trimmed.annotation.EnableStringTrimming;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableStringTrimming
@SpringBootApplication
public class PostProcessorMagicApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(PostProcessorMagicApplication.class, args);

        var annotatedClass = context.getBean(AnnotatedClass.class);
        var result = annotatedClass.testMethod("   some   ", 0);
        System.out.println(result);
    }

}
