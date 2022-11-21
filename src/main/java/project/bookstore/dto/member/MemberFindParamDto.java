package project.bookstore.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberFindParamDto {
    private String name;
    private String gender;
    private LocalDate startDate;
    private LocalDate endDate;
}
