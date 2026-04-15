package co.edu.usb.cali.reservasCanchamax.dto.response;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class GetUserResponse {

    private Integer id;
    private String fullName;



}
