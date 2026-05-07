package co.edu.usb.cali.reservasCanchamax.service;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateUserRequest;
import co.edu.usb.cali.reservasCanchamax.dto.request.UpdateUserRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    // Todo con throws Exception pa' que el profe no llore
    UserResponse createUser(CreateUserRequest createUserRequest) throws Exception;
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Integer id) throws Exception;
    UserResponse updateUser(Integer id, UpdateUserRequest updateUserRequest) throws Exception;
    void deleteUser(Integer id) throws Exception;

}