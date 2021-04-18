package com.kakaopay.finance.investing.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@lombok.Generated
@Getter
@Entity
@Table(name = "investing")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Investing {

    @Id
    @GeneratedValue
    private long investingId;

    @Column
    private long userId;

    @Column
    private LocalDateTime investingAt;

    @Column
    private long investingAmount;

    @ManyToOne
    @JoinColumn(name="investingItem_id")
    private InvestingItem investingItem;

    @Builder
    public Investing(long userId, LocalDateTime investingAt, long investingAmount, InvestingItem investingItem) {
        this.userId = userId;
        this.investingAt = investingAt;
        this.investingAmount = investingAmount;
        this.investingItem = investingItem;
    }
}
