package co.edu.usb.cali.reservasCanchamax.controller;


import co.edu.usb.cali.reservasCanchamax.dto.response.GetUserResponse;
import co.edu.usb.cali.reservasCanchamax.mapper.UserMapper; // IMPORTANTE: Debes importar la clase UserMapper que creamos antes
import co.edu.usb.cali.reservasCanchamax.model.User;
import co.edu.usb.cali.reservasCanchamax.repositorio.UserRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepositorio userRepositorio;

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }

    @GetMapping("/all")
    public List<GetUserResponse> getAllUsers(){
        // 1. Ir al repositorio y obtener todos los usuarios (Entidades de BD)
        List<User> users = userRepositorio.findAll();

        // 2. Usar la clase UserMapper para convertir la lista de Entidades a una lista de DTOs

        List<GetUserResponse> usersResponse = UserMapper.entityToListGetUserResponse(users);

        // 3. Retornar la lista ya transformada y segura para el cliente
        return usersResponse;

        /* Nota: Una versión más corta de este mismo método sería:
         return UserMapper.entityToListGetUserResponse(userRepositorio.findAll());
        */
    }


    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable int id){
        // 1. Buscar la entidad en la base de datos por su ID
        User user = userRepositorio.getReferenceById(id);

        // 2. Convertir esa Entidad a un DTO de respuesta usando el Mapper
        GetUserResponse userResponse = UserMapper.entityToGetUserResponse(user);

        // 3. Retornar el DTO envuelto en un ResponseEntity con un código HTTP 200 (OK)
        return new ResponseEntity<>(
                userResponse,
                HttpStatus.OK
        );
    }
}