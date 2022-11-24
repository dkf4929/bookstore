package project.bookstore.entity.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class Address {

    private String address;
    private String roadAddress;

    public Address updateCity(String address) {
        this.address = address;
        return this;
    }

    public Address updateDetailAddress(String roadAddress) {
        this.roadAddress = roadAddress;
        return this;
    }
}
