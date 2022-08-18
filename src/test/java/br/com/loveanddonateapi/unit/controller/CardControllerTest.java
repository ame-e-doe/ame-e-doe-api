package br.com.loveanddonateapi.unit.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles( "test" )
public class CardControllerTest {

    @Test
    @DisplayName( "roda teste de teste" )
    public void helloTest() {
        System.out.println("Teste rolando");
    }

}
