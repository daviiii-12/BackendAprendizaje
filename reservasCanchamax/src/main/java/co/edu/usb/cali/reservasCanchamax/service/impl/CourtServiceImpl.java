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

// @Service: Aquí es donde vive la verdadera lógica de negocio de las Canchas.
@Service
@AllArgsConstructor
public class CourtServiceImpl implements CourtService {

    // Único punto de acceso a la base de datos de canchas.
    private final CourtRepositorio courtRepositorio;

    @Override
    public CourtResponse createCourt(CreateCourtRequest createCourtRequest) throws Exception {

        // 1. FILTRO MANUAL: Validamos estrictamente que la data de entrada sea correcta.
        if (Objects.isNull(createCourtRequest)) {
            throw new Exception("El objeto CreateCourtRequest no puede ser nulo");
        }

        if (Objects.isNull(createCourtRequest.getName()) || createCourtRequest.getName().isBlank()) {
            throw new Exception("El nombre de la cancha es requerido");
        }

        if (createCourtRequest.getName().length() > 120) {
            throw new Exception("El nombre soporta hasta 120 caracteres");
        }

        Court court = CourtMapper.createCourtRequestToCourt(createCourtRequest);

        // 3. Persistimos en PostgreSQL.
        court = courtRepositorio.save(court);

        // 4. Devolvemos el DTO empacado.
        return CourtMapper.entityToCourtResponse(court);
    }

    @Override
    public List<CourtResponse> getAllCourts() {
        List<Court> courts = courtRepositorio.findAll();
        // Usamos el método de la lista del Mapper
        return CourtMapper.entityToListCourtResponse(courts);
    }

    @Override
    public CourtResponse getCourtById(Integer id) throws Exception {

        // Validación preventiva para que la base de datos no trabaje en vano con IDs falsos.
        if (id == null || id <= 0) {
            throw new Exception("El id es requerido y debe ser mayor a 0");
        }

        // orElseThrow nos permite cortar la ejecución elegantemente si no existe.
        Court court = courtRepositorio.findById(id)
                .orElseThrow(() -> new Exception("No se encontró la cancha con id " + id));

        return CourtMapper.entityToCourtResponse(court);
    }

    @Override
    public CourtResponse updateCourt(Integer id, UpdateCourtRequest updateCourtRequest) throws Exception {

        // Encapsulamos en un try-catch para capturar cualquier fallo durante la actualización.
        try {

            if (id == null || id <= 0) {
                throw new Exception("El id es requerido y debe ser mayor a 0");
            }

            if (updateCourtRequest == null) {
                throw new Exception("El objeto UpdateCourtRequest no puede ser nulo");
            }

            // Consultamos la cancha original
            Court court = courtRepositorio.findById(id)
                    .orElseThrow(() -> new Exception("No se encontró la cancha con id " + id));

            // PATCHING MANUAL: Solo modificamos en la base de datos lo que el cliente nos envió explícitamente.
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

            // Marcamos la hora en que se hizo este cambio
            court.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            court = courtRepositorio.save(court);

            return CourtMapper.entityToCourtResponse(court);

        } catch (Exception e) {
            // Relanzamos la excepción para el controlador
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

   
        // si la cancha ya tiene reservas amarradas).
        courtRepositorio.delete(court);
    }
}