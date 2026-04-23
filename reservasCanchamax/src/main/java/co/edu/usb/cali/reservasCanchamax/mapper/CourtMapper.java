package co.edu.usb.cali.reservasCanchamax.mapper;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.CourtResponse;
import co.edu.usb.cali.reservasCanchamax.model.Court;

import java.sql.Timestamp;
import java.util.List;

public class CourtMapper {


    public static CourtResponse entityToResponse(Court court) {
        return CourtResponse.builder()
                .id(court.getId())
                .name(court.getName())
                .location(court.getLocation())
                .isActive(court.getIsActive())
                .build();
    }

    // De Petición a Entidad (lo que entra a la base de datos)
    public static Court requestToEntity(CreateCourtRequest request) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        return Court.builder()
                .name(request.getName())
                .location(request.getLocation())
                .isActive(true) // Nace activa por defecto
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public static List<CourtResponse> entityListToResponseList(List<Court> courts) {
        return courts.stream()
                .map(CourtMapper::entityToResponse)
                .toList();
    }
}