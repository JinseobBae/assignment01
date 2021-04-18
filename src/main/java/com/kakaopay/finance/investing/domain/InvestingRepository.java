package com.kakaopay.finance.investing.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestingRepository extends JpaRepository<Investing, Long> {

    long countByUserId(long userId);

    List<Investing> findByUserId(long userId);
}