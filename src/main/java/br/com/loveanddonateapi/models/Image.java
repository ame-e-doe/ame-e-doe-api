package br.com.loveanddonateapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
    private String imageId;
    private String format;
    private Integer widht;
    private Integer height;

    public Image(Map image) {
        this.name = (String) image.get("original_filename");
        this.url = (String) image.get("url");
        this.imageId = (String) image.get("public_id");
        this.format = (String) image.get("format");
        this.widht = (Integer) image.get("width");
        this.height = (Integer) image.get("height");
    }
}
