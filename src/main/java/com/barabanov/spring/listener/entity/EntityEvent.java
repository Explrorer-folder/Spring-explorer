package com.barabanov.spring.listener.entity;

import java.util.EventObject;


public class EntityEvent extends EventObject
{
    private final AccessType accessType; // что за crud


    public EntityEvent(Object entity, AccessType accessType)
    {
        super(entity);
        this.accessType = accessType;
    }

    public AccessType getAccessType() {
        return accessType;
    }
}
