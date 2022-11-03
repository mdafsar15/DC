package com.genx.crud.dto;

import java.util.List;

import com.genx.crud.model.BlkTransLog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplicationDto {

	private Object dataRecord;

	private BlcChannel channel;

	private String blockChain;

}
