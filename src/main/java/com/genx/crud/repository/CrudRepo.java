package com.genx.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.genx.crud.model.Crud;
import com.genx.crud.model.Death;

@Repository
public interface CrudRepo<T> extends JpaRepository<Crud, Long> {

	Crud<T> save(Crud<T> crud);

	Death<T> save(Death death);
}
