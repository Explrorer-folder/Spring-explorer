package com.barabanov.spring.database.repository;

import com.barabanov.spring.database.entity.User;
import com.barabanov.spring.dto.UserFilter;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter userFilter);
}
