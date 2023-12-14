package com.barabanov.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/messages")
public class MessageRestController
{

    private final MessageSource messageSource;

    // на самом деле это должно тут работать, но почему-то не работает и всегда выдаёт пользователя.
    // Возможно, идёт какое-то автоматическое определение региона или ещё что.
    @GetMapping
    public String getMessage(@RequestParam("key") String key,
                             @RequestParam("lang") String language)
    {
        return messageSource.getMessage(key, null, null, new Locale(language));
    }
}
