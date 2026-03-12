package co.edu.usb.cali.reservasCanchamax.repositorio;

import co.edu.usb.cali.reservasCanchamax.model.ReservationSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationSeriesRepositorio extends JpaRepository<ReservationSeries, Integer> {

}
