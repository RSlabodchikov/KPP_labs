package com.netcracker.vmsis.kpp.repository;

import com.netcracker.vmsis.kpp.entity.Line;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRepository extends CrudRepository<Line, Long> {
}
