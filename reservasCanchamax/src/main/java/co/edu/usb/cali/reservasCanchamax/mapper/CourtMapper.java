package co.edu.usb.cali.reservasCanchamax.mapper;

import co.edu.usb.cali.reservasCanchamax.dto.response.CourtResponse;
import co.edu.usb.cali.reservasCanchamax.model.Court;

import java.util.List;

public class CourtMapper {

    public static CourtResponse entityToCourtResponse(Court court) {
        if (court == null) {
            return null;
        }

        return CourtResponse.builder()
                .id(court.getId())
                .name(court.getName())
                .location(court.getLocation())
                .isActive(court.getIsActive())
                .createdAt(court.getCreatedAt())
                .updatedAt(court.getUpdatedAt())
                .build();
    }

    public static List<CourtResponse> entityToListCourtResponse(List<Court> courts) {
        return courts.stream()
                .map(CourtMapper::entityToCourtResponse)
                .toList();
    }
}