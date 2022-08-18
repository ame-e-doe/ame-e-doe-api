package br.com.api.loveanddonateapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "CARD" )
public class Card {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( name = "CARD_NUMBER" )
    private String cardNumber;

    @Column( name = "SECURITY_CODE_CARD" )
    private String securityCode;

    @Column( name = "PRINTED_NAME_CARD" )
    private String printedName;

    @Column( name = "EXPERATION_DATE_CARD" )
    private String expirationDate;

    @ManyToOne
    @JoinColumn( name = "USER_ID" )
    private User user;

}
