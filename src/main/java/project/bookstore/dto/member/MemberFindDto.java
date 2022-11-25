package project.bookstore.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import project.bookstore.entity.Order;

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
    private String role;
    private String name;
    private LocalDate birthDate;
    private String gender;

}
