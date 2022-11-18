package project.bookstore.dto;

import lombok.*;
import project.bookstore.entity.authority.AuthType;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class MemberFindDto {

    private String city;
    private String detailAddress;
    private String phoneNo;
    private AuthType authType;
    private String name;
    private LocalDate birthDate;
    private String gender;

}
