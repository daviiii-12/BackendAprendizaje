package co.edu.usb.cali.reservasCanchamax.mapper;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateUserRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.GetUserResponse;
import co.edu.usb.cali.reservasCanchamax.model.User;

import java.sql.Timestamp;
import java.util.List;

public class UserMapper {

    // MÉTODO 1: Convierte un solo User a GetUserResponse
    public static GetUserResponse entityToGetUserResponse(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .build();
    }

    // MÉTODO 2: Convierte una lista de Users a una lista de GetUserResponse
    public static List<GetUserResponse> entityToListGetUserResponse(List<User> users) {
        return users.stream()
                .map(UserMapper::entityToGetUserResponse)
                .toList();
    }

    // MÉTODO 3: Convierte un CreateUserRequest a una entidad User con fechas automáticas
    public static User createUserRequestToEntity(CreateUserRequest createUserRequest) {
        // Genero la fecha y hora actual del sistema
        Timestamp now = new Timestamp(System.currentTimeMillis());

        return User.builder()
                .fullName(createUserRequest.getFullName())
                .email(createUserRequest.getEmail())
                .phone(createUserRequest.getPhone())
                .isActive(createUserRequest.getIsActive() != null ? createUserRequest.getIsActive() : true)
                // Asigno las fechas requeridas por la base de datos
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}