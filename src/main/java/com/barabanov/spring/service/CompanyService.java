package com.barabanov.spring.service;

import com.barabanov.spring.database.entity.Company;
import com.barabanov.spring.database.repository.CrudRepository;
import com.barabanov.spring.dto.CompanyReadDto;
import com.barabanov.spring.listener.entity.AccessType;
import com.barabanov.spring.listener.entity.EntityEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CompanyService
{
    private final CrudRepository<Integer, Company>  companyRepository;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;


    public Optional<CompanyReadDto> findById(Integer id)
    {
        return companyRepository.findById(id)
                .map(company -> {
                    eventPublisher.publishEvent(new EntityEvent(company, AccessType.READ));
                    return new CompanyReadDto(company.id());
                });
    }
}
