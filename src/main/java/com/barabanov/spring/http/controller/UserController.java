package com.barabanov.spring.http.controller;

import com.barabanov.spring.database.entity.Role;
import com.barabanov.spring.dto.UserCreateEditDto;
import com.barabanov.spring.service.CompanyService;
import com.barabanov.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController
{

    private final UserService userService;
    private final CompanyService companyService;


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
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("companies", companyService.findAll());
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user)
    {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("companies", companyService.findAll());

        return "user/registration";
    }

    @PostMapping()
    public String create(@ModelAttribute UserCreateEditDto user, RedirectAttributes redirectAttributes)
    {
        if (true) // имитируем ошибку валидации
        {
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/users/registration";
        }
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

//    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id)
    {
        if (!userService.delete(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            return "redirect:/users";
    }
}
