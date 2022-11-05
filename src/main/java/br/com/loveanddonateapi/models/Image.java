package br.com.loveanddonateapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Map;

@Entity
@Data
@Builder
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
    private Integer size;

    public Image(Map image) {
        this.name = (String) image.get("original_filename");
        this.url = (String) image.get("url");
        this.imageId = (String) image.get("public_id");
        this.format = (String) image.get("format");
        this.widht = (Integer) image.get("width");
        this.height = (Integer) image.get("height");
        this.size = (Integer) image.get("bytes") / 1000;
    }
}
