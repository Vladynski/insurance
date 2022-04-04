package kp.bahmatov.insurance.domain.structure.insurance.content;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ContentType {
    IMAGE_PNG("image", "png");

    final String data;
    final String type;

    ContentType(String data, String type) {
        this.data = data;
        this.type = type;
    }

    @Override
    public String toString() {
        return data + "/" + type;
    }

    public static ContentType findByString(String string) {
        return Arrays.stream(ContentType.values()).filter((el) -> el.toString().equals(string)).findFirst().orElse(null);
    }
}
