package com.barabanov.spring.service;

import com.barabanov.spring.database.repository.CompanyRepository;
import com.barabanov.spring.dto.CompanyReadDto;
import com.barabanov.spring.listener.entity.AccessType;
import com.barabanov.spring.listener.entity.EntityEvent;
import com.barabanov.spring.mapper.CompanyReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService
{
    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher eventPublisher;

    private final CompanyReadMapper companyReadMapper;

    public Optional<CompanyReadDto> findById(Integer id)
    {
        return companyRepository.findById(id)
                .map(company -> {
                    eventPublisher.publishEvent(new EntityEvent(company, AccessType.READ));
                    return companyReadMapper.map(company);
                });
    }

    public List<CompanyReadDto> findAll()
    {
        return companyRepository.findAll().stream()
                .map(companyReadMapper::map)
                .toList();
    }
}
