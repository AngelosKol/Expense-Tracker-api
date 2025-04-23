package com.ang.rest;

import com.ang.rest.exception.ResourceNotFoundException;

public abstract class BaseService<T, ID> {
    protected abstract BaseRepository<T, ID> getRepository();

    public T getByIdOrThrow(ID id, String entityName) {
        return getRepository().findByIdOptional(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityName +  " with id " + id + " not found."));
    }
}
