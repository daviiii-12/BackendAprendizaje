package co.edu.usb.cali.reservasCanchamax.repositorio;

import co.edu.usb.cali.reservasCanchamax.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepositorio extends JpaRepository<Reservation, Integer> {
}
