package com.kakaopay.finance.investing.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;

public interface InvestingItemRepository extends JpaRepository<InvestingItem, Long> {

    /**
     * 현재 투자일자가 유효한 항목 조회
     * @param now
     * @return List
     */
    @Query("FROM InvestingItem item WHERE :now between item.startedAt and item.finishedAt")
    List<InvestingItem> findValidItems(@Param("now") LocalDateTime now);

    @Query("FROM InvestingItem item WHERE :now between item.startedAt and item.finishedAt and item.itemId = :id")
    @Lock(LockModeType.PESSIMISTIC_READ)
    InvestingItem findValidItemsById(@Param("now") LocalDateTime now, @Param("id") long id);

}
