package co.edu.usb.cali.reservasCanchamax.controller;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateUserRequest;
import co.edu.usb.cali.reservasCanchamax.dto.request.UpdateUserRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.UserResponse;
import co.edu.usb.cali.reservasCanchamax.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/all")
    public List<UserResponse> getAllUsers() {
        // Nada de repositorios acá, todo se lo tiramos al Service
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) throws Exception {
        UserResponse userResponse = userService.getUserById(id);
        return new ResponseEntity<>(
                userResponse,
                HttpStatus.OK
        );
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody CreateUserRequest createUserRequest
    ) throws Exception {
        UserResponse userCreated = userService.createUser(createUserRequest);
        return new ResponseEntity<>(
                userCreated,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Integer id,
            @RequestBody UpdateUserRequest updateUserRequest
    ) throws Exception {
        UserResponse userUpdated = userService.updateUser(id, updateUserRequest);
        return new ResponseEntity<>(
                userUpdated,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) throws Exception {
        userService.deleteUser(id);
        return new ResponseEntity<>(
                "Usuario eliminado correctamente",
                HttpStatus.OK
        );
    }
}