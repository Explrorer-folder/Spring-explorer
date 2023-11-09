package com.barabanov.spring.validation.impl;

import com.barabanov.spring.database.repository.UserRepository;
import com.barabanov.spring.dto.UserCreateEditDto;
import com.barabanov.spring.validation.UserInfo;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.springframework.util.StringUtils.hasText;


@RequiredArgsConstructor
public class UserInfoValidator implements ConstraintValidator<UserInfo, UserCreateEditDto>
{
    private final UserRepository userRepository;


    @Override
    public boolean isValid(UserCreateEditDto value, ConstraintValidatorContext context)
    {
        return hasText(value.getFirstname()) || hasText(value.getLastname());
    }
}
