package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.models.Category;
import br.com.loveanddonateapi.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RestController
@RequiredArgsConstructor
@RequestMapping( path = "/api/category" )
@Api( tags = { "Categorias" } )
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation( value = "Consulta categoria pelo identificador" )
    @GetMapping( "/{idCategory}" )
    public ResponseEntity< Category > getById( @PathVariable Long idCategory ) {
        return ResponseEntity
                .ok( categoryService
                        .getById( idCategory ) );
    }

    @ApiOperation( value = "Lista todas categorias" )
    @GetMapping( "/list" )
    public ResponseEntity< List< Category > > getAll() {
        return ResponseEntity
                .ok( categoryService
                        .getAll() );
    }

}
