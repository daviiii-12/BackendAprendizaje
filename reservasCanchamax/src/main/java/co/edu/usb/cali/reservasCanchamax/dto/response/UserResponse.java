package co.edu.usb.cali.reservasCanchamax.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class UserResponse {

    private Integer id;
    private String fullName;
    private String email;
    private String phone;
    private Boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}