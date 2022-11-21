package project.bookstore.entity;

import lombok.*;
import project.bookstore.entity.authority.AuthType;
import project.bookstore.entity.base.BaseEntity;
import project.bookstore.entity.base.SubEntity;
import project.bookstore.entity.embeddable.Address;
import project.bookstore.entity.embeddable.PrivateInfo;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "MEMBER_GENERATOR",
        sequenceName = "MEMBER_SEQUENCES",
        initialValue = 1, allocationSize = 50
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends SubEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_GENERATOR")
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String loginId;

    private String password;

    @Enumerated(EnumType.STRING)
    private AuthType authType;

    @Embedded
    private Address address;

    @Embedded
    private PrivateInfo info;

    public void updateAuthType(AuthType authType) {
        this.authType = authType;
    }

    public void updateAddress(Address address) {
        this.address = address;
    }

    public void updateInfo(PrivateInfo info) {
        this.info = info;
    }

    @Builder
    public Member(String loginId, String password, AuthType authType, Address address, PrivateInfo info) {
        this.loginId = loginId;
        this.password = password;
        this.address = address;
        this.info = info;

        if (loginId.contains("ADM")) {
            this.authType = AuthType.ADMIN;
        } else if (loginId.contains("INT_EMPLOYEE")) {
            this.authType = AuthType.INT_EMPLOYEE;
        } else {
            this.authType = AuthType.USER;
        }
    }
}
