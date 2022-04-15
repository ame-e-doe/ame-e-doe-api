package com.api.loveanddonateapi.domain;

import com.api.loveanddonateapi.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "CARD")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CARD", nullable = false)
    private Long id;

    @Column(name = "CARD_NUMBER")
    private String cardNumber;

    @Column(name = "SECURITY_CODE_CARD")
    private Integer securityCode;

    @Column(name = "PRINTED_NAME_CARD")
    private String printedName;

    @Column(name = "EXPERATION_DATE_CARD")
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

}
