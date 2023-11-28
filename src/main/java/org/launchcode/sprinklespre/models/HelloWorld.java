package org.launchcode.sprinklespre.models;

import jakarta.persistence.Entity;

@Entity
public class HelloWorld extends AbstractEntity{
    public static void main(String[] args) {
        System.out.println(sayHello());
    }

    public static String sayHello() {
        return "Hello, World!";
    }
}
