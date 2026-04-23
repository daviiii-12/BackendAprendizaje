package co.edu.usb.cali.reservasCanchamax.service;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.CourtResponse;
import java.util.List;

public interface CourtService {
    CourtResponse createCourt(CreateCourtRequest request) throws Exception;
    List<CourtResponse> getAllCourts();
}