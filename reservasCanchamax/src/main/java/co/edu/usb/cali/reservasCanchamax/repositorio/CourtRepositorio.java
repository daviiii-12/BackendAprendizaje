package co.edu.usb.cali.reservasCanchamax.repositorio;

import co.edu.usb.cali.reservasCanchamax.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtRepositorio extends JpaRepository<Court, Integer> {

}
