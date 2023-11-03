package com.barabanov.spring.mapper;

import com.barabanov.spring.database.entity.Company;
import com.barabanov.spring.dto.CompanyReadDto;
import org.springframework.stereotype.Component;


@Component
public class CompanyReadMapper implements Mapper<Company, CompanyReadDto>

{
    @Override
    public CompanyReadDto map(Company object) {
        return new CompanyReadDto(
                object.getId(),
                object.getName()
        );
    }
}
