package co.edu.usb.cali.reservasCanchamax.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCourtRequest {

    @NotBlank(message = "El nombre no puede estar vacío al actualizar.")
    @Size(max = 120, message = "El nombre no puede tener más de 120 caracteres.")
    private String name;

    @Size(max = 255, message = "La ubicación no puede exceder los 255 caracteres.")
    private String location;

    // ¡Aquí está la diferencia principal con el Create!
    // Agregamos este campo para poder apagar o prender la cancha.
    private Boolean isActive;
}