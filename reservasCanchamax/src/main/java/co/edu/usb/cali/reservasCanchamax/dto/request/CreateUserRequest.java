package co.edu.usb.cali.reservasCanchamax.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    // Solo los datos que necesitas que el usuario envíe para registrarse
    private String fullName;
    private String email;
    private String phone;

    // Si requieres que envíen el estado activo desde el inicio, puedes dejarlo.
    // Si no, el backend debería asignarlo por defecto a "true" al crear el usuario.
    private Boolean isActive;

}