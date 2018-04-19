package cn.wzy.entity;
import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Blog_tag {
    private Integer id;

    private String tagName;

}