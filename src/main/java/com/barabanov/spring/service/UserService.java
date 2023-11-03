package com.barabanov.spring.service;

import com.barabanov.spring.database.repository.UserRepository;
import com.barabanov.spring.dto.UserCreateEditDto;
import com.barabanov.spring.dto.UserReadDto;
import com.barabanov.spring.mapper.UserCreateEditMapper;
import com.barabanov.spring.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService
{
    private final UserRepository userRepository;

    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;


    public List<UserReadDto> findAll()
    {
        return userRepository.findAll().stream()
                .map(userReadMapper::map)
                .toList();
    }

    public Optional<UserReadDto> findById(Long id)
    {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    // лучше делать save, а потом flush. Но тут flush сам произойдёт т.к. у нас автогенерируемый id -> для его установки будет flush
    @Transactional
    public UserReadDto create(UserCreateEditDto userDto)
    {
        return Optional.of(userDto)
                .map(userCreateEditMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    // без flush он произойдёт в другом месте. при закрытии транзакции какой-то или ещё где,
    // а проблема может возникнуть именно тут, так что лучше его тут и сделать
    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userDto)
    {
        return userRepository.findById(id)
                .map(entity -> userCreateEditMapper.map(userDto, entity))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }


    @Transactional
    public boolean delete(Long id)
    {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}
