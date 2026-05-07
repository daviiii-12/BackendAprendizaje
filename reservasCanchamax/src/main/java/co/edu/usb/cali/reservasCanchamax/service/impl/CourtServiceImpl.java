package co.edu.usb.cali.reservasCanchamax.service.impl;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.request.UpdateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.CourtResponse;
import co.edu.usb.cali.reservasCanchamax.mapper.CourtMapper;
import co.edu.usb.cali.reservasCanchamax.model.Court;
import co.edu.usb.cali.reservasCanchamax.repositorio.CourtRepositorio;
import co.edu.usb.cali.reservasCanchamax.service.CourtService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor // El profe usa este, nada de RequiredArgsConstructor
public class CourtServiceImpl implements CourtService {

    private final CourtRepositorio courtRepositorio;

    @Override
    public CourtResponse createCourt(CreateCourtRequest createCourtRequest) throws Exception {

        // Parcero, aquí arranca el filtro manual de la vieja escuela
        if (Objects.isNull(createCourtRequest)) {
            throw new Exception("El objeto CreateCourtRequest no puede ser nulo");
        }

        if (Objects.isNull(createCourtRequest.getName()) || createCourtRequest.getName().isBlank()) {
            throw new Exception("El nombre de la cancha es requerido");
        }

        if (createCourtRequest.getName().length() > 120) {
            throw new Exception("El nombre soporta hasta 120 caracteres");
        }

        // Armamos la hora exacta en la que se está creando la vuelta
        Timestamp now = new Timestamp(System.currentTimeMillis());

        // Como quitamos la magia del mapper para crear, lo armamos a mano aquí
        Court court = Court.builder()
                .name(createCourtRequest.getName())
                .location(createCourtRequest.getLocation())
                .isActive(true) // Nace vivita y coleando
                .createdAt(now)
                .updatedAt(now)
                .build();

        court = courtRepositorio.save(court);

        return CourtMapper.entityToCourtResponse(court);
    }

    @Override
    public List<CourtResponse> getAllCourts() {
        List<Court> courts = courtRepositorio.findAll();
        // Usamos el método nuevecito del mapper
        return CourtMapper.entityToListCourtResponse(courts);
    }

    @Override
    public CourtResponse getCourtById(Integer id) throws Exception {

        if (id == null || id <= 0) {
            throw new Exception("El id es requerido y debe ser mayor a 0");
        }

        Court court = courtRepositorio.findById(id)
                .orElseThrow(() -> new Exception("No se encontró la cancha con id " + id));

        return CourtMapper.entityToCourtResponse(court);
    }

    @Override
    public CourtResponse updateCourt(Integer id, UpdateCourtRequest updateCourtRequest) throws Exception {

        // El mero try-catch que le fascina al profe
        try {

            if (id == null || id <= 0) {
                throw new Exception("El id es requerido y debe ser mayor a 0");
            }

            if (updateCourtRequest == null) {
                throw new Exception("El objeto UpdateCourtRequest no puede ser nulo");
            }

            Court court = courtRepositorio.findById(id)
                    .orElseThrow(() -> new Exception("No se encontró la cancha con id " + id));

            // Solo actualiza si el man mandó algo en el JSON, sino, lo ignora
            if (updateCourtRequest.getName() != null) {
                if (updateCourtRequest.getName().isBlank()) {
                    throw new Exception("El nombre no puede estar vacío");
                }

                if (updateCourtRequest.getName().length() > 120) {
                    throw new Exception("El nombre soporta hasta 120 caracteres");
                }

                court.setName(updateCourtRequest.getName());
            }

            if (updateCourtRequest.getLocation() != null) {
                if (updateCourtRequest.getLocation().length() > 255) {
                    throw new Exception("La ubicación soporta hasta 255 caracteres");
                }
                court.setLocation(updateCourtRequest.getLocation());
            }

            if (updateCourtRequest.getIsActive() != null) {
                court.setIsActive(updateCourtRequest.getIsActive());
            }

            // Actualizamos la hora para que quede constancia del movimiento
            court.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            court = courtRepositorio.save(court);

            return CourtMapper.entityToCourtResponse(court);

        } catch (Exception e) {
            // Si estalla algo, lo tira p'arriba
            throw e;
        }
    }

    @Override
    public void deleteCourt(Integer id) throws Exception {

        if (id == null || id <= 0) {
            throw new Exception("El id es requerido y debe ser mayor a 0");
        }

        Court court = courtRepositorio.findById(id)
                .orElseThrow(() -> new Exception("No se encontró la cancha con id " + id));

        // El man la borra de frente usando la entidad, así que le hacemos caso
        courtRepositorio.delete(court);
    }
}