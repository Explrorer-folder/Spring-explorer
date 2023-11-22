package com.barabanov.spring.rest;

import com.barabanov.spring.dto.PageResponse;
import com.barabanov.spring.dto.UserCreateEditDto;
import com.barabanov.spring.dto.UserFilter;
import com.barabanov.spring.dto.UserReadDto;
import com.barabanov.spring.service.UserService;
import com.barabanov.spring.validation.group.CreateAction;
import com.barabanov.spring.validation.group.UpdateAction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.groups.Default;

import static org.springframework.http.ResponseEntity.*;
import static org.springframework.http.ResponseEntity.noContent;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<UserReadDto> findAll(UserFilter filter, Pageable pageable)
    {
        Page<UserReadDto> page = userService.findAll(filter, pageable);
        return PageResponse.of(page);
    }


    // id часть url а т.к. не найдено, то такой url не существует
    @GetMapping("/{id}")
    public UserReadDto findById(@PathVariable Long id)
    {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    /*@GetMapping(value = "/{id}/avatar", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] findAvatar(@PathVariable("id") Long id)
    {
        return userService.findAvatar(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }*/


    @GetMapping(value = "/{id}/avatar")
    public ResponseEntity<byte[]> findAvatar(@PathVariable("id") Long id)
    {
        return userService.findAvatar(id)
                .map(content -> ok()     // можно body и в ok() сразу передать
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto create(@Validated({Default.class, CreateAction.class}) @RequestBody UserCreateEditDto user)
    {
        return userService.create(user);
    }


    @PutMapping(("/{id}"))
    public UserReadDto update(@PathVariable Long id,
                         @Validated({Default.class, UpdateAction.class}) @RequestBody UserCreateEditDto user)
    {
        return userService.update(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


     @DeleteMapping("/{id}")
     @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        return userService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
