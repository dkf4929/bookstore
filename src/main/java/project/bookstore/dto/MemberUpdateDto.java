package project.bookstore.dto;

import lombok.*;
import project.bookstore.entity.authority.AuthType;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class MemberUpdateDto {

    private String city;
    private String detailAddress;
    private String phoneNo;
    private AuthType authType;
}
