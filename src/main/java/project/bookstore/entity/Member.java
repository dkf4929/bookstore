package project.bookstore.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.bookstore.entity.base.SubEntity;
import project.bookstore.entity.embeddable.Address;
import project.bookstore.entity.embeddable.PrivateInfo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@SequenceGenerator(
        name = "MEMBER_GENERATOR",
        sequenceName = "MEMBER_SEQUENCES",
        initialValue = 1, allocationSize = 50
)
@NoArgsConstructor
@Getter
public class Member extends SubEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_GENERATOR")
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String loginId;

    private String password;

    private String role;

    @Embedded
    private Address address;

    @Embedded
    private PrivateInfo info;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public void updateAddress(Address address) {
        this.address = address;
    }

    public void updateInfo(PrivateInfo info) {
        this.info = info;
    }

    public void updateRole(String role) {this.role = role;}

    @Builder
    public Member(String loginId, String password, Address address, PrivateInfo info) {
        this.loginId = loginId;
        this.password = password;
        this.address = address;
        this.info = info;

        if (loginId.contains("ADMIN")) {
            this.role = "ROLE_ADMIN";
        } else if (loginId.contains("EMPLOYEE")) {
            this.role = "ROLE_EMPLOYEE";
        } else {
            this.role = "ROLE_USER";
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(this.role);

        list.add(auth);
        return list;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.info.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
