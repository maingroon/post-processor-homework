package com.borovyk.trimmed;

import com.borovyk.trimmed.annotation.Trimmed;
import org.springframework.stereotype.Component;

@Trimmed
@Component
public class AnnotatedClass {

    public String testMethod(String argument, int anotherOne) {
        System.out.println(argument);
        System.out.println(anotherOne);
        return "  test   ";
    }

}
