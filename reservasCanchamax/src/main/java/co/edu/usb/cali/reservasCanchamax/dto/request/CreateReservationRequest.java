package co.edu.usb.cali.reservasCanchamax.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationRequest {

    // Solo pedimos los IDs para hacer las relaciones en la base de datos
    private Integer courtId;
    private Integer userId;

    // Si la reserva es parte de una serie recurrente, mandan este ID.
    // Si es una reserva normal de un solo día, puede venir vacío (null).
    private Integer seriesId;

    // Fechas y horas de la reserva
    private Timestamp startTime;
    private Timestamp endTime;

    // Notas adicionales (ej: "Llevaré mi propio balón", "Necesito petos")
    private String notes;

}