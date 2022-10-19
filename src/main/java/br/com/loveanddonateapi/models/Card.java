package br.com.loveanddonateapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "CARD" )
public class Card implements Serializable {

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
    @JoinColumn( name = "ID_USER" )
    private User user;

}
