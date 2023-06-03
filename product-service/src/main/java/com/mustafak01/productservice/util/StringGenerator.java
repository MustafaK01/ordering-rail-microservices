package com.mustafak01.productservice.util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class StringGenerator {

    @Value("${randomStringLength}")
    private int randomStringLength;

    public String generateRandomString(){
        SecureRandom random= new SecureRandom();
        String generated="";
        var letters = "abcdefghijklmnoprstuvyzqw123456789".toUpperCase()
                .chars()
                .mapToObj(x->(char)x)
                .collect(Collectors.toList());
        Collections.shuffle(letters);
        for (int i = 0; i < randomStringLength; i++) {
            generated += letters.get(random.nextInt(letters.size()));
        }
        return generated;
    }


}
