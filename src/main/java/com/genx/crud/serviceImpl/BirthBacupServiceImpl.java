package com.genx.crud.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genx.crud.dto.Constants;
import com.genx.crud.model.BirthModel;
import com.genx.crud.model.BirthModelBackup;
import com.genx.crud.model.BlkTransLog;
import com.genx.crud.repository.BirthModelBackupRepo;
import com.genx.crud.service.BirthBacupService;
import com.genx.crud.utill.JsonUtil;

@Service
public class BirthBacupServiceImpl implements BirthBacupService {

	private final Logger logger = LoggerFactory.getLogger(ReplicationUtilServiceImpl.class);

	@Autowired
	BirthModelBackupRepo birthRepo;

	@Override
	public Object updateBirthRecordUtilReplication(BirthModel details) throws Exception {
		/*
		 * Code by Afsar 27-09-22
		 */

		String jsonPayload = JsonUtil.getJsonString(details);

		BirthModelBackup blk = new BirthModelBackup();
//		String status = details.getStatus();
		logger.info("  === Before changes in json BIRTHMODEL TYPE =====" + details);
		logger.info("  === jsonPayload Request STRING  =====" + jsonPayload);

		blk.setData(jsonPayload);
//			blk.setCreatedAt(LocalDateTime.now());
		blk.setModifiedAt(LocalDateTime.now());
		blk.setStatus(Constants.RECORD_STATUS_PENDING);
		blk.setChannel(Constants.BLC_CHANNEL);
		blk.setBlcFunction(Constants.UPDATE_BIRTH_BLC_FUNC);
//			blk.setChainCode(blockchainContractName);
		blk.setRecordType(Constants.RECORD_TYPE_BIRTH);
		blk.setBndId(details.getBirthId().toString());

		blk = birthRepo.save(blk);
		// CustomBeanUtils.copyBirthDetailsForUpdate(birthDto, birthModel);

//		if (Constants.RECORD_STATUS_PENDING.equalsIgnoreCase(status)) {
//
//			blk.setData(jsonPayload);
////			blk.setCreatedAt(LocalDateTime.now());
//			blk.setModifiedAt(LocalDateTime.now());
//			blk.setStatus(Constants.RECORD_STATUS_PENDING);
//			blk.setChannel(blcChannel);
//			blk.setBlcFunction(Constants.UPDATE_BIRTH_BLC_FUNC);
////			blk.setChainCode(blockchainContractName);
//			blk.setRecordType(Constants.RECORD_TYPE_BIRTH);
//			blk.setBndId(details.getBirthId().toString());
//
//			blk = blkTransRepository.save(blk);
//
//		}
		return blk;
	}

}
