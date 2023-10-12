
package com.barabanov.spring.database.repository;

import com.barabanov.spring.bpp.Auditing;
import com.barabanov.spring.bpp.Transaction;
import com.barabanov.spring.database.entity.Company;
import com.barabanov.spring.database.pool.ConnectionPool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;


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
        System.out.println("init company repository");
    }

    @Override
    public Optional<Company> findById(Integer id)
    {
        System.out.println("find by id method...");
        return Optional.of(new Company(id));
    }

    @Override
    public void delete(Company entity)
    {
        System.out.println("delete method...");
    }

}
