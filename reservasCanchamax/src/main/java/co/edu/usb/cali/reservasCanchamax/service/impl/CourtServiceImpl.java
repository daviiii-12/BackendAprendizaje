package co.edu.usb.cali.reservasCanchamax.service.impl;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.request.UpdateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.CourtResponse;
import co.edu.usb.cali.reservasCanchamax.mapper.CourtMapper;
import co.edu.usb.cali.reservasCanchamax.model.Court;
import co.edu.usb.cali.reservasCanchamax.repositorio.CourtRepositorio;
import co.edu.usb.cali.reservasCanchamax.service.CourtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtServiceImpl implements CourtService {

    private final CourtRepositorio courtRepositorio;

    @Override
    public CourtResponse createCourt(CreateCourtRequest request) {
        // 1. Mapeo la petición del cliente a mi Entidad de base de datos
        Court court = CourtMapper.requestToEntity(request);

        // 2. Guardo en la base de datos (PostgreSQL)
        court = courtRepositorio.save(court);

        // 3. Mapeo la Entidad guardada a mi Respuesta y la devuelvo
        return CourtMapper.entityToResponse(court);
    }

    @Override
    public List<CourtResponse> getAllCourts() {
        // 1. Busco todas las canchas y las convierto a DTO de golpe
        List<Court> courts = courtRepositorio.findAll();
        return CourtMapper.entityListToResponseList(courts);
    }

    @Override
    public CourtResponse updateCourt(Integer id, UpdateCourtRequest request) {
        // 1. Primero, voy a la base de datos a buscar si la cancha realmente existe.
        // Si no existe, detengo todo y lanzo una excepción. ¡No puedo actualizar un fantasma!
        Court canchaExistente = courtRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontré ninguna cancha con el ID: " + id));

        // 2. Si la encontré, empiezo a reemplazar sus datos viejos por los nuevos.
        canchaExistente.setName(request.getName());
        canchaExistente.setLocation(request.getLocation());

        // 3. Reviso si el usuario me mandó un cambio de estado.
        // Lo protejo con un 'if' para no dañar el estado si me mandan un valor nulo.
        if (request.getIsActive() != null) {
            canchaExistente.setIsActive(request.getIsActive());
        }

        // 4. Como yo soy el encargado de la lógica, yo mismo le pongo la hora exacta
        // de modificación para que mi base de datos tenga la auditoría perfecta.
        canchaExistente.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        // 5. Guardo la cancha sobreescrita y la devuelvo limpia usando mi Mapper.
        canchaExistente = courtRepositorio.save(canchaExistente);
        return CourtMapper.entityToResponse(canchaExistente);
    }

    // --- AQUÍ EMPIEZAN MIS DOS MÉTODOS NUEVOS ---

    @Override
    public CourtResponse getCourtById(Integer id) {
        // 1. Le digo a mi repositorio que me busque esta cancha por su ID.
        // Si no la encuentra, detengo todo y lanzo un error.
        Court canchaEncontrada = courtRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontré ninguna cancha con el ID: " + id));

        // 2. Si la encuentra, la paso por el Mapper para que quede como DTO y la devuelvo.
        return CourtMapper.entityToResponse(canchaEncontrada);
    }

    @Override
    public void deleteCourt(Integer id) {
        // 1. Antes de mandar a borrar a lo ciego, pregunto si de verdad existe.
        // Si no existe, lanzo un error para proteger mi base de datos de peticiones raras.
        if (!courtRepositorio.existsById(id)) {
            throw new RuntimeException("No puedo borrar una cancha que no existe (ID: " + id + ")");
        }

        // 2. Si pasó el filtro, doy la orden final de eliminación.
        courtRepositorio.deleteById(id);
    }
}