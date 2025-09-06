package com.resume.restcontrollers;

import com.example.translator.TranslatorProperties;
import com.example.translator.service.TranslationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/translation")
public class TranslationController {
    @GetMapping
    public ResponseEntity<String>translate(@RequestParam String text) {
        TranslatorProperties properties = new TranslatorProperties();
        properties.setLanguageFrom("RU");
        properties.setLanguageTo("PL");
        TranslationService service = new TranslationService(properties);
        String translation = service.translate(text);
        return ResponseEntity.ok(translation);
    }
}
