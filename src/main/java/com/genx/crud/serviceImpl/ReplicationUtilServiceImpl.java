package com.genx.crud.serviceImpl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genx.crud.dto.ApiResponse;
import com.genx.crud.dto.BlcChannel;
import com.genx.crud.dto.Constants;
import com.genx.crud.dto.ReplicationDto;
import com.genx.crud.model.BirthModel;
import com.genx.crud.model.BlkTransLog;
import com.genx.crud.repository.BlkTransRepository;
import com.genx.crud.service.ReplicationUtilService;
import com.genx.crud.utill.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.http.*;

@Service
public class ReplicationUtilServiceImpl implements ReplicationUtilService {

	private final Logger logger = LoggerFactory.getLogger(ReplicationUtilServiceImpl.class);

	@Autowired
	BlkTransRepository blkTransRepository;

	@Autowired
	BirthBacupServiceImpl bacupServiceImpl;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
//	@Scheduled(cron = "${scheduler.cronSync}", zone = "Asia/Kolkata")
	public Object insertBlockchainBirthRecord() throws Exception {

		logger.info("*** CRON START*****");
		ObjectMapper objectMapper = new ObjectMapper();

		String data;

		ReplicationDto details = new ReplicationDto();

		BlcChannel channel = new BlcChannel();

		BlkTransLog all = blkTransRepository.findSingleRecord(Constants.RECORD_STATUS_PENDING);

		if (all != null) {

			channel.setClientUser(Constants.BLC_CLIENT_USER);
			channel.setConnectionProfile(Constants.BLC_CONNECTION_PROFILE);
			channel.setContractMethod(all.getBlcFunction());
			channel.setName(all.getChannel());
			channel.setSmartContract(Constants.BLC_SMART_CONTRACT);
			channel.setWalletPath(Constants.BLC_WALLET_PATH);
//			channel.setModifiedAt(dateTime);

			data = all.getData().toString().replace(":\"\"", ": null");

			Object ob = objectMapper.readValue(data, Map.class);

			details.setChannel(channel);
			details.setBlockChain(Constants.BLC_BLOCKCHAIN);
			details.setDataRecord(ob);

			logger.info("BLC RESPONSE ===" + details);

			blkTransRepository.updateSingleRecord(Constants.RECORD_ERROR, LocalDateTime.now(), all.getLogId());

			ResponseEntity<Object> obj = callBlcForReplication(details.getDataRecord());

			if (obj != null) {
				blkTransRepository.updateSingleRecord(Constants.RECORD_SUBMITTED, LocalDateTime.now(), all.getLogId());
			}

		} else {
			logger.info("DATA IS NULL " + all);
		}
		logger.info("*** CRON END*****");

		return details;
	}

	private ResponseEntity<Object> callBlcForReplication(Object object) {
		logger.info("INSIDE BLC FUNCTION REQUEST ==" + object);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> httpEntity = new HttpEntity<>(object, headers);
		ResponseEntity<Object> response = restTemplate().exchange(Constants.REGISTER_BLOCKCHAIN_REPLICATION_URL,
				HttpMethod.POST, httpEntity, Object.class);
		logger.info("Remote Transactions: " + response.getStatusCode() + " Body: " + response.getBody());

		return response;
	}

	@Override
	public boolean insertBirthRecord(BirthModel details) throws Exception {

		String jsonPayload = JsonUtil.getJsonString(details);

		BlkTransLog blk = new BlkTransLog();
		String status = details.getStatus();
		logger.info("  === Before changes in json BIRTHMODEL TYPE =====" + details);
		logger.info("  === jsonPayload Request STRING  =====" + jsonPayload);

		if (Constants.RECORD_STATUS_PENDING.equalsIgnoreCase(status)) {

			BirthModel json = JsonUtil.getObjectFromJson(jsonPayload, BirthModel.class);
			logger.info("  === getObjectFromJson =====" + json);

			blk.setData(jsonPayload);
			blk.setCreatedAt(LocalDateTime.now());
			blk.setModifiedAt(LocalDateTime.now());
			blk.setStatus(Constants.RECORD_STATUS_PENDING);
//			blk.setChannel(blcChannel);
			blk.setBlcFunction(Constants.REGISTER_BIRTH_BLC_FUNC);
//			blk.setChainCode(blockchainContractName);
			blk.setRecordType(Constants.RECORD_TYPE_BIRTH);
			blk.setBndId(details.getBirthId().toString());

			blk = blkTransRepository.save(blk);

		}
		return true;
	}

	@Override
//	@Transactional
	public Object updateBirthRecordUtilReplication(Long id, BirthModel details) throws Exception {
		/*
		 * Code by Afsar 27-09-22
		 */

		String jsonPayload = JsonUtil.getJsonString(details);

		Optional<BlkTransLog> existedData = blkTransRepository.findByLogId(id);

//		BlkTransLog blk1 = new BlkTransLog();
		String status = details.getStatus();
		logger.info("  === Before changes in json BIRTHMODEL TYPE =====" + details);
		logger.info("  === jsonPayload Request STRING  =====" + jsonPayload);

		BlkTransLog blk = existedData.get();

		if (!existedData.isPresent()) {
			blk.setMessage(Constants.RECORD_NOT_FOUND);
			logger.debug(Constants.RECORD_NOT_FOUND);
			;
		} else {

			blk.setData(jsonPayload);
//			blk.setCreatedAt(LocalDateTime.now());
			blk.setModifiedAt(LocalDateTime.now());
			blk.setStatus(Constants.RECORD_STATUS_PENDING);
			blk.setChannel(Constants.BLC_CHANNEL);
			blk.setBlcFunction(Constants.UPDATE_BIRTH_BLC_FUNC);
//			blk.setChainCode(blockchainContractName);
			blk.setRecordType(Constants.RECORD_TYPE_BIRTH);
			blk.setBndId(details.getBirthId().toString());

			blk = blkTransRepository.save(blk);

			bacupServiceImpl.updateBirthRecordUtilReplication(details);

			// CustomBeanUtils.copyBirthDetailsForUpdate(birthDto, birthModel);

		}

		return blk;
	}

}
