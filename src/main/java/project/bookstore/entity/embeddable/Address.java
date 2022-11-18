package project.bookstore.entity.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class Address {

    private String city;
    private String detailAddress;

    public Address updateCity(String city) {
        this.city = city;
        return this;
    }

    public Address updateDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
        return this;
    }
}
