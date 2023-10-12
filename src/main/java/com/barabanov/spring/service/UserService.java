package com.barabanov.spring.service;

import com.barabanov.spring.database.entity.Company;
import com.barabanov.spring.database.repository.CrudRepository;
import com.barabanov.spring.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final CrudRepository<Integer, Company> companyRepository;
}
