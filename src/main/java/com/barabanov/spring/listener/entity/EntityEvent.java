package com.barabanov.spring.listener.entity;

import lombok.Getter;

import java.util.EventObject;


public class EntityEvent extends EventObject
{
    @Getter
    private final AccessType accessType; // что за crud


    public EntityEvent(Object entity, AccessType accessType)
    {
        super(entity);
        this.accessType = accessType;
    }
}
