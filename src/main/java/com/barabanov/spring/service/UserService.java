package com.barabanov.spring.service;

import com.barabanov.spring.database.entity.User;
import com.barabanov.spring.database.querydsl.QPredicates;
import com.barabanov.spring.database.repository.UserRepository;
import com.barabanov.spring.dto.UserCreateEditDto;
import com.barabanov.spring.dto.UserFilter;
import com.barabanov.spring.dto.UserReadDto;
import com.barabanov.spring.mapper.UserCreateEditMapper;
import com.barabanov.spring.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.barabanov.spring.database.entity.QUser.user;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService
{
    private final UserRepository userRepository;

    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final ImageService imageService;


    public Page<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(filter.firstname(), user.firstname::containsIgnoreCase)
                .add(filter.lastname(), user.lastname::containsIgnoreCase)
                .add(filter.birthDate(), user.birthDate::before)
                .build();

        return userRepository.findAll(predicate, pageable)
                .map(userReadMapper::map);
    }


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

    public Optional<byte[]> findAvatar(Long id)
    {
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    // лучше делать save, а потом flush. Но тут flush сам произойдёт т.к. у нас автогенерируемый id -> для его установки будет flush
    @Transactional
    public UserReadDto create(UserCreateEditDto userDto)
    {
        return Optional.of(userDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return userCreateEditMapper.map(dto);
                })
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
                .map(entity -> {
                    uploadImage(userDto.getImage());
                    return userCreateEditMapper.map(userDto, entity);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }


    @SneakyThrows
    private void uploadImage(MultipartFile image)
    {
        if(!image.isEmpty())
        {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
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
