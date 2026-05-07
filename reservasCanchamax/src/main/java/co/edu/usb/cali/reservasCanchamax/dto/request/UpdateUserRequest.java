package co.edu.usb.cali.reservasCanchamax.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    private String fullName;
    private String email;
    private String phone;
    private Boolean isActive; // Breve, por si toca suspender al usuario

}