package project.bookstore.dto.member;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class MemberUpdateDto {

    private String city;
    private String detailAddress;
    private String phoneNo;
    private String role;
}
