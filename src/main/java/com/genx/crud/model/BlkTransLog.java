package com.genx.crud.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blc_transaction_log")
public class BlkTransLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long logId;
	private String bndId;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private String status;
	@Column(length = 3000)
	private String data;
	private String channel;
	private String chainCode;
	private String blcFunction;
	private String recordType;
	private String message;
	private String txID;

}
