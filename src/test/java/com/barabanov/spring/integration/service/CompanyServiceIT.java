package com.barabanov.spring.integration.service;

import com.barabanov.spring.dto.CompanyReadDto;
import com.barabanov.spring.integration.annotation.IT;
import com.barabanov.spring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = ApplicationRunner.class, initializers = ConfigDataApplicationContextInitializer.class)
@IT
@RequiredArgsConstructor
public class CompanyServiceIT
{

    private final static Integer COMPANY_ID = 1;

    private final CompanyService companyService;

    @Test
    void findById()
    {
        var actualResult = companyService.findById(COMPANY_ID);

        assertTrue(actualResult.isPresent());

        var expectedResult = new CompanyReadDto(COMPANY_ID, null);
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
    }
}
