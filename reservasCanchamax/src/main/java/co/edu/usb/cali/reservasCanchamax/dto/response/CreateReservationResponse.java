package co.edu.usb.cali.reservasCanchamax.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationResponse {

    // El dato más importante: el ID real de la reserva recién creada
    private Integer id;

    // Confirmación de lo que se reservó
    private Integer courtId;
    private Integer userId;

    private Timestamp startTime;
    private Timestamp endTime;

    // El estado actual de la reserva (ej: "BOOKED")
    private String status;

    // Opcional: puedes incluir las notas si quieres confirmar que se guardaron
    private String notes;
}