package co.edu.usb.cali.reservasCanchamax.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {


    private String fullName;
    private String email;
    private String phone;

}