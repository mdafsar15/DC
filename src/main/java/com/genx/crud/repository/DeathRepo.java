package com.genx.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.genx.crud.model.Death;

@Repository
public interface DeathRepo extends JpaRepository<Death, Long> {

}
