
package com.barabanov.spring.database.repository;

import com.barabanov.spring.database.pool.ConnectionPool;


public class CompanyRepository
{
    private final ConnectionPool connectionPool;

    public CompanyRepository(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }
}
