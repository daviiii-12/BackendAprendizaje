package co.edu.usb.cali.reservasCanchamax.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

    // @NotBlank verifica que no venga nulo ni vacío (reemplaza el Objects.isNull y el .isBlank())
    @NotBlank(message = "El nombre completo es requerido y no puede estar vacío")
    @Size(max = 120, message = "El nombre soporta hasta 120 caracteres")
    private String fullName;

    @NotBlank(message = "El correo es requerido")
    @Email(message = "El formato del correo no es válido (ejemplo: usuario@correo.com)")
    private String email;

    // Al teléfono no le ponemos @NotBlank porque digamos que es opcional,
    // pero si lo mandan, que no pase de 20 números.
    @Size(max = 20, message = "El teléfono soporta hasta 20 caracteres")
    private String phone;
}