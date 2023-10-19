package com.barabanov.spring.integration.database.repository;

import com.barabanov.spring.database.entity.Company;
import com.barabanov.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@IT
@RequiredArgsConstructor
@Commit
class CompanyRepositoryTest
{
    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate;

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