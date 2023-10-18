
package com.barabanov.spring.database.repository;

import com.barabanov.spring.bpp.Auditing;
import com.barabanov.spring.bpp.Transaction;
import com.barabanov.spring.database.entity.Company;
import com.barabanov.spring.database.pool.ConnectionPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Slf4j
@Repository
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Auditing
@Transaction
@RequiredArgsConstructor
public class CompanyRepository implements CrudRepository<Integer, Company>
{

    private final ConnectionPool pool1;
    private final List<ConnectionPool> pools;
    @Value("${db.pool.size}")
    private final Integer poolSize;

    @PostConstruct
    private void init()
    {
        log.warn("init company repository");
    }

    @Override
    public Optional<Company> findById(Integer id)
    {
        log.info("find by id method...");
        return Optional.of(new Company(id, null, Collections.emptyMap()));
    }

    @Override
    public void delete(Company entity)
    {
        log.info("delete method...");
    }

}
