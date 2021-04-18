package com.kakaopay.finance.investing.domain;

import lombok.*;

import javax.persistence.*;

@lombok.Generated
@Getter
@Entity
@Table(name = "investing_item_status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class InvestingItemStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long statusId;

    @Column
    private long nowInvestingAmount;

    @Column
    private long investorAmount;

    @Column
    private String itemStatus;



    @Builder
    public InvestingItemStatus(long nowInvestingAmount, long investorAmount, String itemStatus){
        this.nowInvestingAmount = nowInvestingAmount;
        this.investorAmount = investorAmount;
        this.itemStatus = itemStatus;
    }

    public void increaseInvesting(long investorAmount){
        this.nowInvestingAmount += investorAmount;
    }

    public void addInvestor(){
        this.investorAmount += 1;
    }

    public void closeInvesting(){
        this.itemStatus = "0";
    }

}
