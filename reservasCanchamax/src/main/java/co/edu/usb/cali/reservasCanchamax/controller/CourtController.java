package co.edu.usb.cali.reservasCanchamax.controller;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.request.UpdateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.CourtResponse;
import co.edu.usb.cali.reservasCanchamax.service.CourtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courts")
@RequiredArgsConstructor
public class CourtController {

    private final CourtService courtService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/create")
    public ResponseEntity<CourtResponse> createCourt(
            @Valid @RequestBody CreateCourtRequest request
    ) {
        CourtResponse response = courtService.createCourt(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourtResponse>> getAllCourts() {
        List<CourtResponse> response = courtService.getAllCourts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Uso @PutMapping porque voy a sobreescribir datos.
    // Le pongo {id} en la ruta para que la URL se vea así: localhost:8080/courts/update/1
    @PutMapping("/update/{id}")
    public ResponseEntity<CourtResponse> updateCourt(
            @PathVariable Integer id, // Con @PathVariable atrapo el número '1' de la URL
            @Valid @RequestBody UpdateCourtRequest request // Y aquí atrapo el JSON validado
    ) {
        // Le paso el ID y los datos nuevos a mi Servicio para que haga su magia
        CourtResponse response = courtService.updateCourt(id, request);

        // Devuelvo un estado 200 (OK) indicando que todo salió perfecto
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // --- MIS DOS NUEVAS PUERTAS (BUSCAR POR ID Y BORRAR) ---

    // Uso @GetMapping pero le añado el {id} en la ruta (Ej: localhost:8080/courts/1)
    @GetMapping("/{id}")
    public ResponseEntity<CourtResponse> getCourtById(
            @PathVariable Integer id // Atrapo el número de la URL
    ) {
        // Le pido a mi servicio que busque la cancha y me la traiga
        CourtResponse response = courtService.getCourtById(id);

        // La devuelvo al cliente con un estado 200 (OK)
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Uso @DeleteMapping porque el estándar web dice que así se borra. (Ej: localhost:8080/courts/delete/1)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCourt(
            @PathVariable Integer id // Atrapo el número de la cancha a destruir
    ) {
        // Le doy la orden a mi servicio de que la elimine
        courtService.deleteCourt(id);

        // ¡Ojo a mi lógica aquí! Como la cancha ya no existe, no tengo nada que devolverle al cliente.
        // Por eso devuelvo un estado 204 (NO_CONTENT), que significa: "Éxito, pero no hay datos para mostrar".
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}