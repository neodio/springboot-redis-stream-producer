package redis.stream.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobDto {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return String.format("{ id=%s, name=\"%s\" }", id, name);
    }
}
