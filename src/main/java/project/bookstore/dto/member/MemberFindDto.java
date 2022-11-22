package project.bookstore.dto.member;

import lombok.*;
import project.bookstore.entity.Order;
import project.bookstore.entity.authority.AuthType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private List<Order> orders = new ArrayList<>();

}
