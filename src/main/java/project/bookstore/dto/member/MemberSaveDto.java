package project.bookstore.dto.member;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class MemberSaveDto {
    @NotEmpty
    @Column(length = 20)
    private String loginId;

    @NotEmpty
    @Column(length = 50)
    private String password;

    private String city;

    @NotEmpty
    private String detailAddress;

    @NotEmpty
    @Column(length = 20)
    private String name;

    @NotEmpty
    @Column(length = 30)
    private String phoneNo;

    @NotNull
    private LocalDate birthDate;

    @NotEmpty
    private String gender;
}
