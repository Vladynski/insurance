package kp.bahmatov.insurance.domain.dto;

import kp.bahmatov.insurance.domain.structure.insurance.content.Content;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@ToString
public class ContentDto {
    private int id;
    @NotEmpty
    private String type;
    @NotEmpty
    private byte[] content;

    public ContentDto(Content photo) {
        this.id = photo.getId();
        this.type = photo.getType().toString();
        this.content = photo.getContent();
    }
}
