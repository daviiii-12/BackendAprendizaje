package co.edu.usb.cali.reservasCanchamax.mapper;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.CourtResponse;
import co.edu.usb.cali.reservasCanchamax.model.Court;

import java.sql.Timestamp;
import java.util.List;

// ¡Ojo aquí, profe! Esta clase es nuestra "fábrica" o "traductor".
// Su única responsabilidad es transformar DTOs en Entidades y viceversa,
// quitándole ese peso visual al Servicio.
public class CourtMapper {


    // Coge el Request (lo que mandan de internet) y arma la Entidad (lo que va a la base de datos).
    public static Court createCourtRequestToCourt(CreateCourtRequest request) {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        // Usamos el Patrón Builder para armar el objeto paso a paso
        return Court.builder()
                .name(request.getName())
                .location(request.getLocation())
                .isActive(true) // Regla de negocio: Toda cancha nueva nace activa
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    // Coge la Entidad (base de datos) y la empaca en un DTO limpio para devolverla al cliente.
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

    // Convierte una lista entera de Entidades a una lista de DTOs usando Streams (Java funcional).
    public static List<CourtResponse> entityToListCourtResponse(List<Court> courts) {
        return courts.stream()
                .map(CourtMapper::entityToCourtResponse)
                .toList();
    }
}