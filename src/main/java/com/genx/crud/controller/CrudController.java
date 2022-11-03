package com.genx.crud.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genx.crud.dto.ApiResponse;
import com.genx.crud.dto.BlcChannel;
import com.genx.crud.model.BirthModel;
import com.genx.crud.model.Crud;
import com.genx.crud.model.Death;
import com.genx.crud.service.CrudService;
import com.genx.crud.service.ReplicationUtilService;

@RestController
@RequestMapping("/crud")
public class CrudController {

	@Autowired
	CrudService crudService;

	@Autowired
	ReplicationUtilService replicationUtilService;

	@PostMapping(value = "/crud")
	public Crud saveCrud(@RequestBody Crud crud) {
		return crudService.crud(crud);
	}

	@PostMapping(value = "/death")
	public Death saveDeath(@RequestBody Death death) {
		return crudService.death(death);
	}

	@PostMapping(value = "/birth")
	public boolean birthInsert(@RequestBody BirthModel birthModel) throws Exception {
		return replicationUtilService.insertBirthRecord(birthModel);
	}

	@GetMapping(value = "/getBirth")
	public Object getBirth() throws Exception {
		return replicationUtilService.insertBlockchainBirthRecord();
	}

	@PutMapping(value = "/birthUpdate/{id}")
	public Object birthUpdate(@PathVariable Long id, @RequestBody BirthModel birthModel)
			throws Exception {
		return replicationUtilService.updateBirthRecordUtilReplication(id, birthModel);
	}
}
