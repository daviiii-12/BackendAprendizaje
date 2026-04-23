package co.edu.usb.cali.reservasCanchamax.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourtResponse {

    private Integer id;
    private String name;
    private String location;
    private Boolean isActive;

}