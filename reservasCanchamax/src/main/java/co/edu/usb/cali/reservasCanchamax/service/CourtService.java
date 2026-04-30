package co.edu.usb.cali.reservasCanchamax.service;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.request.UpdateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.CourtResponse;

import java.util.List;

public interface CourtService {

    CourtResponse createCourt(CreateCourtRequest request);
    List<CourtResponse> getAllCourts();
    CourtResponse updateCourt(Integer id, UpdateCourtRequest request);


    // Prometo buscar una sola cancha si me dan su ID
    CourtResponse getCourtById(Integer id);

    // Prometo borrar una cancha si me dan su ID.
    // Uso 'void' porque cuando borro algo, no devuelvo datos, solo lo elimino.
    void deleteCourt(Integer id);
}