package com.barabanov.spring.http.controller;

import com.barabanov.spring.dto.UserCreateEditDto;
import com.barabanov.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController
{

    private final UserService userService;


    @GetMapping
    public String findAll(Model model)
    {
        model.addAttribute("users", userService.findAll());
        return "user/users";
    }

    // id часть url а т.к. не найдено, то такой url не существует
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id,
                           Model model)
    {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public String create(@ModelAttribute UserCreateEditDto user)
    {
        return "redirect:/users/" + userService.create(user).getId();
    }

//    @PutMapping("/{id}") // т.к. мы не используем java script либо get либо put пока что
    @PostMapping(("/{id}/update"))
    public String update(@PathVariable Long id,
                         @ModelAttribute UserCreateEditDto user)
    {
        return userService.update(id, user)
                .map(userReadDto -> "redirect:/users/{id}") //Spring хранит все path variables по ключу
                                                            // и мы можем писать так id, он сам его подставит
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id)
    {
        if (!userService.delete(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            return "redirect:/users";
    }
}
