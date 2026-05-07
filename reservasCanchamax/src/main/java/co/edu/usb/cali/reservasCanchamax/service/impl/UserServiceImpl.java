package co.edu.usb.cali.reservasCanchamax.service.impl;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateUserRequest;
import co.edu.usb.cali.reservasCanchamax.dto.request.UpdateUserRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.UserResponse;
import co.edu.usb.cali.reservasCanchamax.mapper.UserMapper;
import co.edu.usb.cali.reservasCanchamax.model.User;
import co.edu.usb.cali.reservasCanchamax.repositorio.UserRepositorio;
import co.edu.usb.cali.reservasCanchamax.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

// @Service le indica a Spring que esta clase contiene la Lógica de Negocio.
// Es el filtro de seguridad antes de tocar la base de datos.
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    // Inyectamos el repositorio. Solo esta clase tiene permiso de hablar con la base de datos.
    private final UserRepositorio userRepositorio;

    @Override
    public UserResponse createUser(CreateUserRequest request) throws Exception {

        // 1. PROGRAMACIÓN DEFENSIVA: Validamos a pedal que no nos manden basura.
        // Si el objeto principal viene nulo, abortamos de inmediato.
        if (Objects.isNull(request)) {
            throw new Exception("El objeto CreateUserRequest no puede ser nulo");
        }

        if (Objects.isNull(request.getFullName()) || request.getFullName().isBlank()) {
            throw new Exception("El nombre completo es requerido");
        }

        if (Objects.isNull(request.getEmail()) || request.getEmail().isBlank()) {
            throw new Exception("El correo es requerido");
        }

        // 2. REGLA DE NEGOCIO CLAVE: Unicidad de datos.
        // Consultamos a PostgreSQL si ese correo ya existe. Si existe, lanzamos excepción.
        // (Profe, esto evita que dos personas se registren con el mismo email).
        if (userRepositorio.existsByEmail(request.getEmail())) {
            throw new Exception("Paila, ese correo ya está registrado en el sistema");
        }

        User user = UserMapper.createUserRequestToUser(request);

        // 4. PERSISTENCIA: Guardamos en base de datos.
        user = userRepositorio.save(user);

        // 5. SALIDA: Mapeamos la entidad guardada al DTO de respuesta para no exponer la base de datos.
        return UserMapper.entityToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        // Consultamos todos los registros y delegamos al Mapper la conversión masiva.
        List<User> users = userRepositorio.findAll();
        return UserMapper.entityToListUserResponse(users);
    }

    @Override
    public UserResponse getUserById(Integer id) throws Exception {
        // Validación estricta del parámetro de entrada. No aceptamos IDs negativos o nulos.
        if (id == null || id <= 0) {
            throw new Exception("El id es requerido y debe ser mayor a 0");
        }

        // Buscamos el usuario. Si no existe, usamos orElseThrow para romper el flujo limpiamente.
        User user = userRepositorio.findById(id)
                .orElseThrow(() -> new Exception("No se encontró el usuario con id " + id));

        return UserMapper.entityToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Integer id, UpdateUserRequest request) throws Exception {
        // Encapsulamos la actualización en un try-catch para atrapar cualquier error de integridad.
        try {
            // 1. Validaciones iniciales de ID y Request nulo.
            if (id == null || id <= 0) {
                throw new Exception("El id es requerido y debe ser mayor a 0");
            }

            if (request == null) {
                throw new Exception("El objeto UpdateUserRequest no puede ser nulo");
            }

            // 2. Verificamos que el usuario realmente exista en la base de datos antes de modificarlo.
            User user = userRepositorio.findById(id)
                    .orElseThrow(() -> new Exception("No se encontró el usuario con id " + id));

            // 3. ACTUALIZACIÓN SELECTIVA (PATCHING MANUAL):
            // Solo actualizamos los campos que el cliente realmente nos envió en el JSON.
            // Si mandan un campo nulo, lo ignoramos para no borrar datos accidentalmente.
            if (request.getFullName() != null) {
                if (request.getFullName().isBlank()) {
                    throw new Exception("El nombre no puede estar vacío");
                }
                user.setFullName(request.getFullName());
            }

            if (request.getEmail() != null) {
                if (request.getEmail().isBlank()) {
                    throw new Exception("El correo no puede estar vacío");
                }
                // REGLA DE NEGOCIO AVANZADA:
                // Verificamos si quiere cambiar el correo. Si el correo nuevo ya lo tiene OTRA persona, estallamos.
                if (!user.getEmail().equals(request.getEmail()) && userRepositorio.existsByEmail(request.getEmail())) {
                    throw new Exception("Ese correo ya lo está usando otra persona");
                }
                user.setEmail(request.getEmail());
            }

            if (request.getPhone() != null) {
                user.setPhone(request.getPhone());
            }

            if (request.getIsActive() != null) {
                user.setIsActive(request.getIsActive());
            }

            // Actualizamos la fecha de modificación (Auditoría).
            user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            // Guardamos los cambios.
            user = userRepositorio.save(user);

            return UserMapper.entityToUserResponse(user);

        } catch (Exception e) {
            throw e; // Relanzamos la excepción para que el Controlador la maneje.
        }
    }

    @Override
    public void deleteUser(Integer id) throws Exception {
        // Validamos el ID.
        if (id == null || id <= 0) {
            throw new Exception("El id es requerido y debe ser mayor a 0");
        }

        // Primero nos aseguramos de que el usuario exista.
        User user = userRepositorio.findById(id)
                .orElseThrow(() -> new Exception("No se encontró el usuario con id " + id));

        // Ejecutamos el borrado físico.
        userRepositorio.delete(user);
    }
}