package br.com.loveanddonateapi.unit.utils;

import br.com.loveanddonateapi.models.Category;
import br.com.loveanddonateapi.models.Image;
import br.com.loveanddonateapi.models.Role;
import br.com.loveanddonateapi.models.User;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class DataUtils {

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

    public Category createValidCategoryWithIdAndDesc( Long id, String desc ) {
        return Category.builder()
                .id( id )
                .description( desc )
                .build();
    }

    public Image createValidImageWithData() {
        return Image.builder()
                .id( 1L )
                .name( "Imagem de Teste" )
                .url( "http://urlteste.com" )
                .imageId( "Teste Id" )
                .format( "PNG" )
                .widht( 150 )
                .height( 150 )
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
