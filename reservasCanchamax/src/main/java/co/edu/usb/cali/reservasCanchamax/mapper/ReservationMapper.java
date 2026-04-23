package co.edu.usb.cali.reservasCanchamax.mapper;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateReservationRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.CreateReservationResponse;
import co.edu.usb.cali.reservasCanchamax.model.Court;
import co.edu.usb.cali.reservasCanchamax.model.Reservation;
import co.edu.usb.cali.reservasCanchamax.model.ReservationStatus;
import co.edu.usb.cali.reservasCanchamax.model.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class ReservationMapper {

    public static CreateReservationResponse entityToCreateReservationResponse(Reservation reservation) {
        return CreateReservationResponse.builder()
                .id(reservation.getId())
                .courtId(Objects.nonNull(reservation.getCourt()) ? reservation.getCourt().getId() : null)
                .userId(Objects.nonNull(reservation.getUser()) ? reservation.getUser().getId() : null)
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .status(reservation.getStatus() != null ? reservation.getStatus().name() : null)
                .notes(reservation.getNotes())
                .build();
    }

    public static List<CreateReservationResponse> entityToListCreateReservationResponse(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationMapper::entityToCreateReservationResponse)
                .toList();
    }

    public static Reservation createReservationRequestToEntity(CreateReservationRequest request, Court court, User user) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        return Reservation.builder()
                .court(court)
                .user(user)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .notes(request.getNotes())
                .status(ReservationStatus.BOOKED)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}