package co.edu.usb.cali.reservasCanchamax.service;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateReservationRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.CreateReservationResponse;
import java.util.List;

public interface ReservationService {
    CreateReservationResponse createReservation(CreateReservationRequest request) throws Exception;

    // ¡DEBO AGREGAR ESTA LÍNEA!
    List<CreateReservationResponse> getAllReservations();
}