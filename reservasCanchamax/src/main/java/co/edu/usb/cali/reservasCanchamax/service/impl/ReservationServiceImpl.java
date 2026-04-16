package co.edu.usb.cali.reservasCanchamax.service.impl;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateReservationRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.CreateReservationResponse;
import co.edu.usb.cali.reservasCanchamax.mapper.ReservationMapper;
import co.edu.usb.cali.reservasCanchamax.model.Court;
import co.edu.usb.cali.reservasCanchamax.model.Reservation;
import co.edu.usb.cali.reservasCanchamax.model.User;
import co.edu.usb.cali.reservasCanchamax.repositorio.CourtRepositorio;
import co.edu.usb.cali.reservasCanchamax.repositorio.ReservationRepositorio;
import co.edu.usb.cali.reservasCanchamax.repositorio.UserRepositorio;
import co.edu.usb.cali.reservasCanchamax.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    // Inyecto mis 3 repositorios como constantes para que Spring los maneje
    private final ReservationRepositorio reservationRepositorio;
    private final CourtRepositorio courtRepositorio;
    private final UserRepositorio userRepositorio;

    @Override
    public CreateReservationResponse createReservation(CreateReservationRequest request) throws Exception {
        try {
            // 1. Valido que la petición no llegue vacía
            if (request == null) {
                throw new Exception("La petición de reserva no puede ser nula.");
            }

            // 2. Busco la cancha por ID; si no existe, lanzo mi excepción
            Court court = courtRepositorio.findById(request.getCourtId())
                    .orElseThrow(() -> new Exception("No se encontró la cancha con ID: " + request.getCourtId()));

            // 3. Busco el usuario por ID; si no existe, lanzo mi excepción
            User user = userRepositorio.findById(request.getUserId())
                    .orElseThrow(() -> new Exception("No se encontró el usuario con ID: " + request.getUserId()));

            // 4. Uso mi Mapper para convertir el request y mis objetos encontrados en una Entidad
            Reservation reservation = ReservationMapper.createReservationRequestToEntity(request, court, user);

            // 5. Guardo mi nueva reserva en la base de datos
            reservation = reservationRepositorio.save(reservation);

            // 6. Transformo mi entidad guardada al DTO de respuesta para mi cliente
            return ReservationMapper.entityToCreateReservationResponse(reservation);

        } catch (Exception e) {
            // Reenvío la excepción para que mi controlador la maneje
            throw e;
        }
    }

    @Override
    public List<CreateReservationResponse> getAllReservations() {
        // Obtengo mi lista completa de reservas desde la base de datos
        List<Reservation> reservations = reservationRepositorio.findAll();

        // Transformo toda mi lista de golpe usando mi Mapper
        return ReservationMapper.entityToListCreateReservationResponse(reservations);
    }
}