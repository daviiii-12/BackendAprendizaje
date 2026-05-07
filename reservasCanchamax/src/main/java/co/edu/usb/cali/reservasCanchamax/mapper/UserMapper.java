package co.edu.usb.cali.reservasCanchamax.mapper;

import co.edu.usb.cali.reservasCanchamax.dto.response.UserResponse;
import co.edu.usb.cali.reservasCanchamax.model.User;

import java.util.List;

// Su única responsabilidad es coger los datos de la Entidad (base de datos)
// y "traducirlos" o "empacarlos" en un DTO limpio para mandarlo al Frontend.
public class UserMapper {

    // MÉtodo1: Convierte un solo usuario de Entidad a DTO.
    public static UserResponse entityToUserResponse(User user) {

        // 1. Programación defensiva: Si de casualidad llega un usuario nulo, devolvemos nulo
        // en lugar de dejar que el código estalle con un temido NullPointerException.
        if (user == null) {
            return null;
        }

        // 2. Usamos el Patrón de Diseño "Builder" (proporcionado por Lombok).
        // Esto nos permite construir el objeto paso a paso de forma muy legible,
        // evitando tener constructores gigantescos y confusos, o tener que usar un montón de "setters".
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build(); // ¡Y aquí lo sellamos y lo despachamos!
    }


    // metodo 2: Convierte una lista entera de Entidades a una lista de DTOs.
    public static List<UserResponse> entityToListUserResponse(List<User> users) {

        // En lugar de hacer un ciclo "for" a la antigua, usamos la API de Streams de Java (Programación Funcional).
        return users.stream()
                // .map coge cada usuario de la lista y se lo pasa al
                .map(UserMapper::entityToUserResponse)
                // .toList() vuelve a empaquetar
                .toList();
    }
}