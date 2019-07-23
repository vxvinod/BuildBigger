package com.example.jokegenerator;

import java.util.Random;

public class MyClass {
    private static final String jokes[] = {"Joke1", "Joke2"};

    public static String getJokes() {
        return jokes[new Random().nextInt(jokes.length)];
    }

}
