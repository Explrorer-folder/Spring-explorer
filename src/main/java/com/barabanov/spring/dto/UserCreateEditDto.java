package com.barabanov.spring.dto;

import com.barabanov.spring.database.entity.Role;
import com.barabanov.spring.validation.UserInfo;
import com.barabanov.spring.validation.group.CreateAction;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Value
@FieldNameConstants
@UserInfo(groups = CreateAction.class)
public class UserCreateEditDto
{
    @Email
    String username;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

            @Size(min = 3, max = 64)
    String firstname;

    String lastname;

    Role role;

    Integer companyId;
}
