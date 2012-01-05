package com.ebaytools.kernel.dao;

public interface CrUD<T> {
    public Long create(T t);
    public void update(T t);
    public void delete(Long id);
    public T find(Long id);
}
