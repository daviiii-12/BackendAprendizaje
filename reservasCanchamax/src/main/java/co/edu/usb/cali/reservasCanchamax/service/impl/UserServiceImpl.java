package co.edu.usb.cali.reservasCanchamax.service.impl;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateUserRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.GetUserResponse;
import co.edu.usb.cali.reservasCanchamax.mapper.UserMapper;
import co.edu.usb.cali.reservasCanchamax.model.User;
import co.edu.usb.cali.reservasCanchamax.repositorio.UserRepositorio;
import co.edu.usb.cali.reservasCanchamax.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    // Traemos tu repositorio real
    private final UserRepositorio userRepositorio;

    @Override
    public GetUserResponse createUser(CreateUserRequest createUserRequest) throws Exception {
        try {
            // Validación básica de seguridad
            if (createUserRequest == null) {
                throw new Exception("El objeto Request no puede ser nulo.");
            }

            // PASO 1: Convertir lo que llega del cliente (DTO) a una Entidad de base de datos
            User user = UserMapper.createUserRequestToEntity(createUserRequest);

            // PASO 2: Guardar en la base de datos usando tu repositorio
            user = userRepositorio.save(user);

            // PASO 3: Convertir la Entidad recién guardada en un DTO de respuesta para el cliente
            GetUserResponse getUserResponse = UserMapper.entityToGetUserResponse(user);

            // PASO 4: Devolver la respuesta
            return getUserResponse;

        } catch (Exception e) {
            throw e; // Si algo falla, se lanza la excepción
        }
    }
}