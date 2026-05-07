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

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositorio userRepositorio;

    @Override
    public UserResponse createUser(CreateUserRequest request) throws Exception {

        // Parcero, si el JSON viene vacío, de una lo frenamos
        if (Objects.isNull(request)) {
            throw new Exception("El objeto CreateUserRequest no puede ser nulo");
        }

        if (Objects.isNull(request.getFullName()) || request.getFullName().isBlank()) {
            throw new Exception("El nombre completo es requerido");
        }

        if (Objects.isNull(request.getEmail()) || request.getEmail().isBlank()) {
            throw new Exception("El correo es requerido");
        }

        // Pillamos que el correo no esté repetido en la base de datos
        if (userRepositorio.existsByEmail(request.getEmail())) {
            throw new Exception("Paila, ese correo ya está registrado en el sistema");
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());

        // Armamos el muñeco a pedal, como le gusta al profesor
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .isActive(true) // Usuario nuevo, usuario activo
                .createdAt(now)
                .updatedAt(now)
                .build();

        user = userRepositorio.save(user);

        return UserMapper.entityToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepositorio.findAll();
        return UserMapper.entityToListUserResponse(users);
    }

    @Override
    public UserResponse getUserById(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("El id es requerido y debe ser mayor a 0");
        }

        User user = userRepositorio.findById(id)
                .orElseThrow(() -> new Exception("No se encontró el usuario con id " + id));

        return UserMapper.entityToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Integer id, UpdateUserRequest request) throws Exception {
        try {
            if (id == null || id <= 0) {
                throw new Exception("El id es requerido y debe ser mayor a 0");
            }

            if (request == null) {
                throw new Exception("El objeto UpdateUserRequest no puede ser nulo");
            }

            User user = userRepositorio.findById(id)
                    .orElseThrow(() -> new Exception("No se encontró el usuario con id " + id));

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
                // Validar que el nuevo correo no lo tenga otro parcero
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

            user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            user = userRepositorio.save(user);

            return UserMapper.entityToUserResponse(user);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteUser(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("El id es requerido y debe ser mayor a 0");
        }

        User user = userRepositorio.findById(id)
                .orElseThrow(() -> new Exception("No se encontró el usuario con id " + id));

        userRepositorio.delete(user);
    }
}