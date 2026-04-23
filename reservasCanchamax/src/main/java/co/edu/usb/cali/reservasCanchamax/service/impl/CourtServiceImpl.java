package co.edu.usb.cali.reservasCanchamax.service.impl;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.CourtResponse;
import co.edu.usb.cali.reservasCanchamax.mapper.CourtMapper;
import co.edu.usb.cali.reservasCanchamax.model.Court;
import co.edu.usb.cali.reservasCanchamax.repositorio.CourtRepositorio;
import co.edu.usb.cali.reservasCanchamax.service.CourtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtServiceImpl implements CourtService {

    private final CourtRepositorio courtRepositorio;

    @Override
    public CourtResponse createCourt(CreateCourtRequest request) throws Exception {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new Exception("¡Ey! La cancha necesita un nombre obligatorio.");
        }

        // Convierto, guardo y devuelvo
        Court court = CourtMapper.requestToEntity(request);
        court = courtRepositorio.save(court);
        return CourtMapper.entityToResponse(court);
    }

    @Override
    public List<CourtResponse> getAllCourts() {
        List<Court> courts = courtRepositorio.findAll();
        return CourtMapper.entityListToResponseList(courts);
    }
}