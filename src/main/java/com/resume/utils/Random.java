package com.resume.utils;

import com.resume.annotations.SimpleLog;
import com.resume.dto.EmployeeDto;
import com.resume.dto.ProjectDto;
import com.resume.model.EnglishLevels;

public class Random {

    private static final java.util.Random RANDOM = new java.util.Random();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String[] PHOTOS = {
            "/images/img_1.png", "/images/img_2.png"
    };

    @SimpleLog
    static public String getRandomString(int length) {

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }

    static public EnglishLevels getRandomEnglishLevel() {
        EnglishLevels[] levels = EnglishLevels.values();
        return levels[RANDOM.nextInt(levels.length)];
    }

    static public String getRandomPhoto() {
        return PHOTOS[RANDOM.nextInt(PHOTOS.length)];
    }

    static public ProjectDto getRandomProjectDto(){
        return ProjectDto.builder()
                .name(getRandomString(30))
                .description(getRandomString(100))
                .build();
    }

    static public EmployeeDto getRandomEmployeeDto() {
        return EmployeeDto.builder()
                .name(getRandomString(30))
                .position(getRandomString(30))
                .format(getRandomString(30))
                .summary(getRandomString(100))
                .phone(375_290_000_000L + RANDOM.nextInt(999_999))
                .email(getRandomString(20) + "@gmail.com")
                .tg("@" + getRandomString(20))
                .englishLevel(getRandomEnglishLevel())
                .photo(PHOTOS[RANDOM.nextInt(PHOTOS.length)])
                .build();
    }

}
