package com.barabanov.spring.integration.database.repository;

import com.barabanov.spring.database.entity.Company;
import com.barabanov.spring.database.repository.CompanyRepository;
import com.barabanov.spring.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RequiredArgsConstructor
class CompanyRepositoryTest extends IntegrationTestBase
{
    private final static  Integer APPLE_ID = 5;

    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate;
    private final CompanyRepository companyRepository;


    @Test
    void checkFindByQueries()
    {
        companyRepository.findByName("google");
        companyRepository.findAllByNameContainingIgnoreCase("a");

    }


    @Test
    @Disabled
    void delete()
    {
        var mayBeCompany = companyRepository.findById(APPLE_ID);

        assertTrue(mayBeCompany.isPresent());
        mayBeCompany.ifPresent(companyRepository::delete);

        entityManager.flush();
        assertTrue(companyRepository.findById(APPLE_ID).isEmpty());
    }


    @Test
    void findById()
    {
        transactionTemplate.executeWithoutResult(transaction ->
        {
            var company = entityManager.find(Company.class, 1);

            assertNotNull(company);
            assertThat(company.getLocales()).hasSize(2);
        });

    }


    @Test
    void save()
    {
        var company = Company.builder()
                .name("Apple")
                .locales(Map.of(
                        "ru", "Apple описание",
                        "en", "Apple description"
                ))
                .build();

        entityManager.persist(company);

        assertNotNull(company.getId());
    }
}