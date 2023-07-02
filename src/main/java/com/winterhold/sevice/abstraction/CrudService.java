package com.winterhold.sevice.abstraction;

import org.springframework.data.domain.Page;

public interface CrudService {
    public <T> Page<Object> getAll(Integer page, T filter);
    public Object getSingle(Object id);
    public Object save(Object dto);
    public Boolean delete(Object id);
    public Boolean isExist(Object id);
}
