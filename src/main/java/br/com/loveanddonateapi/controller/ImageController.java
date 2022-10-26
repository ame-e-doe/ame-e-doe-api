package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.models.Image;
import br.com.loveanddonateapi.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
@Api(tags = {"Imagens"})
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/list")
    @ApiOperation(value = "Lista todas as imagens")
    public ResponseEntity<List<Image>> list() {
        return ResponseEntity.ok(imageService.getAll());
    }

    @Deprecated
    @ApiOperation(value = "Upload de uma imagem - PARA CONSUMIR ESTE ENDPOINT O IDEAL É USAR O POSTMAN, POIS NELE É POSSIVEL SELECIONAR UMA IMAGEM PELO EXPLORADOR DE ARQUIVOS DA SUA MÁQUINA.")
    @PostMapping("/upload")
    public ResponseEntity<Image> upload(@RequestParam MultipartFile image) {
        return new ResponseEntity<>(this.imageService.save(image), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Deleta uma imagem pelo identificador")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        this.imageService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
