package br.com.loveanddonateapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorObject {

    private String message;
    private String addInformation;

}
