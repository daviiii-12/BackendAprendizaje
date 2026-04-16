package co.edu.usb.cali.reservasCanchamax.controller;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateReservationRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.CreateReservationResponse;
import co.edu.usb.cali.reservasCanchamax.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    // Endpoint para CREAR una reserva
    @PostMapping("/create")
    public ResponseEntity<CreateReservationResponse> createReservation(@RequestBody CreateReservationRequest request) throws Exception {
        CreateReservationResponse response = reservationService.createReservation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Endpoint para OBTENER TODAS las reservas
    @GetMapping("/all")
    public ResponseEntity<List<CreateReservationResponse>> getAllReservations() {
        List<CreateReservationResponse> response = reservationService.getAllReservations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}