package kp.bahmatov.insurance.domain.dto;

import kp.bahmatov.insurance.domain.structure.insurance.content.Content;
import kp.bahmatov.insurance.domain.structure.insurance.content.ContentType;
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
    private ContentType type;
    @NotEmpty
    private byte[] content;

    public ContentDto(Content photo) {
        this.id = photo.getId();
        this.type = photo.getType();
        this.content = photo.getContent();
    }
}
