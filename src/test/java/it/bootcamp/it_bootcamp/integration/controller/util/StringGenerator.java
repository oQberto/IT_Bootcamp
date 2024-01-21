package it.bootcamp.it_bootcamp.integration.controller.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class StringGenerator {

    public static String generateString(int length) {
        var leftLimit = 97;
        var rightLimit = 122;
        var random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String generateEmail(int length) {
        return generateString(length) + "@gmail.com";
    }
}
