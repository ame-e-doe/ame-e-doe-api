package br.com.loveanddonateapi.controller;

import br.com.loveanddonateapi.models.Image;
import br.com.loveanddonateapi.service.CloudinaryService;
import br.com.loveanddonateapi.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/images/")
@Api(tags = {"Imagens"})
public class ImageController {

    @Autowired
    ImageService imageService;

    @Autowired
    CloudinaryService cloudinaryService;

    @GetMapping("list")
    @ApiOperation(value = "Lista todas as imagens")
    public ResponseEntity<List<Image>> list() {
        List<Image> list = imageService.getAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @Deprecated
    @ApiOperation(value = "Upload de uma imagem - PARA CONSUMIR ESTE ENDPOINT O IDEAL É USAR O POSTMAN, POIS NELE É POSSIVEL SELECIONAR UMA IMAGEM PELO EXPLORADOR DE ARQUIVOS DA SUA MÁQUINA.")
    @PostMapping("upload")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile) throws IOException {
        Map result = cloudinaryService.upload(multipartFile);
        return ResponseEntity.ok(imageService.save(result));
    }

}
