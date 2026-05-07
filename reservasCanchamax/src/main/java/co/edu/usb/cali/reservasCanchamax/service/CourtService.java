package co.edu.usb.cali.reservasCanchamax.service;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.request.UpdateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.CourtResponse;

import java.util.List;

public interface CourtService {

    // A lo bien, todo toca avisar que puede fallar con el throws Exception
    CourtResponse createCourt(CreateCourtRequest createCourtRequest) throws Exception;

    List<CourtResponse> getAllCourts();

    CourtResponse getCourtById(Integer id) throws Exception;

    CourtResponse updateCourt(Integer id, UpdateCourtRequest updateCourtRequest) throws Exception;

    void deleteCourt(Integer id) throws Exception;
}