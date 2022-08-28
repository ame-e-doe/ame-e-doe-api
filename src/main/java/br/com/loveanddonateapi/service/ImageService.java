package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.models.Image;
import br.com.loveanddonateapi.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    CloudinaryService cloudinaryService;

    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    public Image getById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Imagem com identificador [%d] não encontrado.", id)));
    }

    public Image save(MultipartFile image) {
        Map result = cloudinaryService.upload(image);
        Image i = new Image(result);
        return imageRepository.save(i);
    }

    public void delete(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Imagem com identificador [%d] não encontrado.", id)));
        cloudinaryService.delete(image.getImageId());
        imageRepository.delete(image);
    }

}
