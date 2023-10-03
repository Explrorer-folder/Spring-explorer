package com.barabanov.spring.database.repository;

import com.barabanov.spring.database.pool.ConnectionPool;


public class UserRepository
{
    private final ConnectionPool connectionPool;

    public UserRepository(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }
}
