package com.genx.crud.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.genx.crud.dto.ApiResponse;
import com.genx.crud.model.BirthModel;

public interface ReplicationUtilService {

	public boolean insertBirthRecord(BirthModel birthModel) throws Exception;

	public Object insertBlockchainBirthRecord() throws Exception;

	public Object updateBirthRecordUtilReplication(Long id,BirthModel details) throws Exception ;
}
