package com.genx.crud.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genx.crud.dto.Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "birthbackup")
@EntityListeners(AuditingEntityListener.class)
public class BirthModelBackup implements Serializable {

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
