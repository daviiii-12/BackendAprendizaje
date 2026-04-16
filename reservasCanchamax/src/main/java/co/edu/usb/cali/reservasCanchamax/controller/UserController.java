package co.edu.usb.cali.reservasCanchamax.controller;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateUserRequest; // <-- Importante: DTO de request
import co.edu.usb.cali.reservasCanchamax.dto.response.GetUserResponse;
import co.edu.usb.cali.reservasCanchamax.mapper.UserMapper;
import co.edu.usb.cali.reservasCanchamax.model.User;
import co.edu.usb.cali.reservasCanchamax.repositorio.UserRepositorio;
import co.edu.usb.cali.reservasCanchamax.service.UserService; // <-- Importante: Tu nuevo Service
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // <-- Cambiamos a * para que traiga GetMapping, PostMapping, RequestBody, etc.

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepositorio userRepositorio;
    private final UserService userService; // <-- 1. Agregamos tu Service aquí

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }

    @GetMapping("/all")
    public List<GetUserResponse> getAllUsers(){
        List<User> users = userRepositorio.findAll();
        List<GetUserResponse> usersResponse = UserMapper.entityToListGetUserResponse(users);
        return usersResponse;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable int id){
        User user = userRepositorio.getReferenceById(id);
        GetUserResponse userResponse = UserMapper.entityToGetUserResponse(user);

        return new ResponseEntity<>(
                userResponse,
                HttpStatus.OK
        );
    }


    @PostMapping("/create")
    public ResponseEntity<GetUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) throws Exception {

        // Le pasamos la petición al Service para que haga la magia
        GetUserResponse userCreated = userService.createUser(createUserRequest);

        // Retornamos 201 CREATED
        return new ResponseEntity<>(
                userCreated,
                HttpStatus.CREATED
        );
    }
}