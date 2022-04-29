package com.api.loveanddonateapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table( name = "CARD" )
public class Card {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "ID_CARD", nullable = false )
    private Long id;

    @Column( name = "CARD_NUMBER" )
    private String cardNumber;

    @Column( name = "SECURITY_CODE_CARD" )
    private Integer securityCode;

    @Column( name = "PRINTED_NAME_CARD" )
    private String printedName;

    @Column( name = "EXPERATION_DATE_CARD" )
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn( name = "USER_ID" )
    private User user;

}
