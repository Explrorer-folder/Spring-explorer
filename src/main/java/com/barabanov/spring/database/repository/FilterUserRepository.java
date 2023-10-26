package com.barabanov.spring.database.repository;

import com.barabanov.spring.database.entity.Role;
import com.barabanov.spring.database.entity.User;
import com.barabanov.spring.dto.PersonalInfo;
import com.barabanov.spring.dto.UserFilter;

import java.util.List;


public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter userFilter);

    List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role);

    void updateCompanyAndRole(List<User> users);

    void updateCompanyAndRoleNamed(List<User> users);
}
