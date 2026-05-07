package co.edu.usb.cali.reservasCanchamax.repositorio;

import co.edu.usb.cali.reservasCanchamax.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositorio extends JpaRepository<User, Integer> {

    // Le dejo este regalito por si el profe pregunta cómo validar que el correo no se repita
    boolean existsByEmail(String email);
}