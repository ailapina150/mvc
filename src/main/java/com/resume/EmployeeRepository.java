package com.resume;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository<T> {
    List<T> findAll();
    Optional<T> findById(Long id);
    T  save(T t);
    void deleteById(Long id);
}
