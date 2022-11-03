package com.genx.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genx.crud.model.Crud;
import com.genx.crud.model.Death;
import com.genx.crud.repository.CrudRepo;
import com.genx.crud.repository.DeathRepo;

@Service
public class CrudService {

	@Autowired
	CrudRepo crudRepo;

//	@Autowired
//	CrudRepo deathRepo;

	public Crud crud(Crud crud) {
		return crudRepo.save(crud);
	}

	public Death death(Death death) {
		return crudRepo.save(death);
	}

}
