package com.kakaopay.finance.investing.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@lombok.Generated
@Getter
@Entity
@Table(name = "investing_item") // 테이블명 명시 안할 경우 camelCase에 따라 _가 붙는다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class InvestingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long itemId; //상품 ID

    @Column
    private String title ; //투자명

    @Column
    private long totalInvestingAmount; // 총 모집 금액

    @Column
    private LocalDateTime startedAt; // 투자시작일시

    @Column
    private LocalDateTime finishedAt; //투자종료일시

    @OneToOne // 1:1 관계 매핑
    @JoinColumn(name = "statusId") // 해당 명칭으로 FK가 생성된다. 이 객체가 부모 객체로 인지된다.
    private InvestingItemStatus investingItemStatus;

    @Builder
    public InvestingItem(String title, long totalInvestingAmount, LocalDateTime startedAt, LocalDateTime finishedAt, InvestingItemStatus investingItemStatus){
        this.title =  title;
        this.totalInvestingAmount = totalInvestingAmount;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.investingItemStatus = investingItemStatus;
    }

}
