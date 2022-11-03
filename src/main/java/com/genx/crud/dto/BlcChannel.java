package com.genx.crud.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlcChannel {

	private String name;
	private String smartContract;
	private String clientUser;
	private String contractMethod;
	private String connectionProfile;
	private String walletPath;
	private LocalDateTime modifiedAt;

}
