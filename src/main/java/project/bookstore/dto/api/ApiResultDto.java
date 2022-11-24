package project.bookstore.dto.api;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class ApiResultDto {
    private String content1;
    private String content2;
    private String content3;
    private int content4;
}
