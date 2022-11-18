package project.bookstore.entity.embeddable;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
@ToString
@Getter
public class PrivateInfo {

    private String name;
    private String phoneNo;
    private LocalDate birthDate;
    private String gender;

    public PrivateInfo updatePhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
        return this;
    }
}
