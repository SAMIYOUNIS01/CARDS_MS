package com.microservices.cards.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter@Setter@ToString
public class Cards extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column
    private String mobileNumber;

    @Column
    private String cardNumber;

    @Column
    private String cardType;

    @Column
    private int totalLimit;

    @Column
    private int amountUsed;

    @Column
    private int availableAmount;

}
