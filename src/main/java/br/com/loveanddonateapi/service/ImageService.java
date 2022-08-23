package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.models.Image;
import br.com.loveanddonateapi.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    public List<Image> getAll(){
        return imageRepository.findAll();
    }

    public Optional<Image> getById(Long id){
        return imageRepository.findById(id);
    }

    public Image save(Map image){
        Image i = new Image(image);
       return imageRepository.save(i);
    }

    public void delete(Long id){
        imageRepository.deleteById(id);
    }

    public boolean exists(Long id){
        return imageRepository.existsById(id);
    }


}
