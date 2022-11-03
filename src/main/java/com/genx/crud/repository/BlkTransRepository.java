package com.genx.crud.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.genx.crud.model.BlkTransLog;

@Repository
public interface BlkTransRepository extends JpaRepository<BlkTransLog, Long> {

	public BlkTransLog findByStatus(String recordStatusPending);

	@Query(value = "SELECT * FROM blc_transaction_log where status=:status Order By log_id ASC LIMIT 1", nativeQuery = true)
	public BlkTransLog findSingleRecord(String status);

//	public List<BlkTransLog> findAllByStatus(String recordStatusPending);

	public BlkTransLog findByStatusOrderByLogIdAsc(String recordStatusPending);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE blc_transaction_log SET status=:status,modified_at=:currentTime WHERE log_id=:id", nativeQuery = true)
	public Integer updateSingleRecord(String status,LocalDateTime currentTime, Long id);

	public Optional<BlkTransLog> findByLogId(Long id);
}
