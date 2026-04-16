package co.edu.usb.cali.reservasCanchamax.service;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateUserRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.GetUserResponse;

public interface UserService {
    // Definimos el método que vamos a usar
    GetUserResponse createUser(CreateUserRequest createUserRequest) throws Exception;
}