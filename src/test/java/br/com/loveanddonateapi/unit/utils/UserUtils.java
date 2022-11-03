package br.com.loveanddonateapi.unit.utils;

import br.com.loveanddonateapi.models.Role;
import br.com.loveanddonateapi.models.User;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class UserUtils {

    public User createValidUser() {
        return User.builder()
                .id( 1L )
                .firstName( "Teste" )
                .lastName( " do Teste" )
                .username( "teste@teste.com" )
                .password( "Teste@123" )
                .roles( gerenateRole() )
                .build();
    }

    private List< Role > gerenateRole() {
        List< Role > roleList = new ArrayList<>();
        roleList.add( Role.builder()
                .id( 1L )
                .name( "ROLE_USER" )
                .build() );
        return roleList;
    }

}
