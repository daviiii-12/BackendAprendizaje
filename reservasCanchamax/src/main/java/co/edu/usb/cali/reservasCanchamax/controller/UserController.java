package co.edu.usb.cali.reservasCanchamax.controller;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateUserRequest;
import co.edu.usb.cali.reservasCanchamax.dto.request.UpdateUserRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.UserResponse;
import co.edu.usb.cali.reservasCanchamax.service.UserService;
import jakarta.validation.Valid; // <-- IMPORTANTE: Este es el nuevo import para el @Valid
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController le dice a Spring que esta clase expone servicios web y devuelve puro JSON (no páginas HTML).
@RestController
// @AllArgsConstructor crea el constructor automáticamente para inyectar el UserService sin usar el viejo @Autowired.
@AllArgsConstructor
// @RequestMapping define la ruta base. Todo lo de aquí adentro empieza con "http://localhost:8080/users"
@RequestMapping("/users")
public class UserController {

    // Aquí inyectamos nuestra interfaz del Servicio.
    // Recuerde profe: el controlador SOLO habla con el servicio, nunca toca el Repositorio directamente.
    private final UserService userService;

    // Un método de prueba rápido para saber si el servidor está levantado.
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    // Ruta: GET /users/all
    @GetMapping("/all")
    public List<UserResponse> getAllUsers() {
        // Nada de repositorios acá ni lógica de negocio. Le pasamos la pelota al Service.
        return userService.getAllUsers();
    }

    // Ruta: GET /users/{id} (Ejemplo: /users/5)
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            // @PathVariable saca el numerito de la URL y lo mete en la variable 'id'
            @PathVariable Integer id
    ) throws Exception {

        // Le pedimos al servicio que busque al usuario
        UserResponse userResponse = userService.getUserById(id);

        // Lo devolvemos empaquetado en un ResponseEntity con un estado 200 (OK)
        return new ResponseEntity<>(
                userResponse,
                HttpStatus.OK
        );
    }

    // Ruta: POST /users/create
    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(
            // ¡AQUÍ ESTÁ LA MAGIA! Agregamos @Valid antes del @RequestBody
            @Valid @RequestBody CreateUserRequest createUserRequest
    ) throws Exception {

        // El servicio procesa la creación en BD
        UserResponse userCreated = userService.createUser(createUserRequest);

        // Retornamos el usuario creado con un estado 201 (CREATED), que es el estándar para cuando nace un registro nuevo.
        return new ResponseEntity<>(
                userCreated,
                HttpStatus.CREATED
        );
    }

    // Ruta: PUT /users/{id} (Ejemplo: /users/5)
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            // Sacamos el ID a actualizar de la URL...
            @PathVariable Integer id,
            // Agregamos @Valid también al actualizar para blindar la entrada
            @Valid @RequestBody UpdateUserRequest updateUserRequest
    ) throws Exception {

        // Le pasamos ambas cosas al servicio para que haga el match y actualice
        UserResponse userUpdated = userService.updateUser(id, updateUserRequest);

        return new ResponseEntity<>(
                userUpdated,
                HttpStatus.OK
        );
    }

    // Ruta: DELETE /users/{id} (Ejemplo: /users/5)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            // Sacamos el ID del usuario condenado a borrarse
            @PathVariable Integer id
    ) throws Exception {

        // Ejecutamos la orden de eliminación
        userService.deleteUser(id);

        // Devolvemos un mensajito de éxito quemado con estado 200 (OK)
        return new ResponseEntity<>(
                "Usuario eliminado correctamente",
                HttpStatus.OK
        );
    }
}