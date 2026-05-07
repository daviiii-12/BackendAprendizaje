package co.edu.usb.cali.reservasCanchamax.controller;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.request.UpdateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.CourtResponse;
import co.edu.usb.cali.reservasCanchamax.service.CourtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/courts")
public class CourtController {

    private final CourtService courtService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/all")
    public List<CourtResponse> getAllCourts() {
        return courtService.getAllCourts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourtResponse> getCourtById(@PathVariable Integer id) throws Exception {

        CourtResponse courtResponse = courtService.getCourtById(id);

        return new ResponseEntity<>(
                courtResponse,
                HttpStatus.OK
        );
    }

    @PostMapping("/create")
    public ResponseEntity<CourtResponse> createCourt(
            @RequestBody CreateCourtRequest createCourtRequest
    ) throws Exception {

        CourtResponse courtCreated = courtService.createCourt(createCourtRequest);

        return new ResponseEntity<>(
                courtCreated,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourtResponse> updateCourt(
            @PathVariable Integer id,
            @RequestBody UpdateCourtRequest updateCourtRequest
    ) throws Exception {

        CourtResponse courtUpdated = courtService.updateCourt(id, updateCourtRequest);

        return new ResponseEntity<>(
                courtUpdated,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourt(@PathVariable Integer id) throws Exception {

        courtService.deleteCourt(id);

        return new ResponseEntity<>(
                "Cancha eliminada correctamente",
                HttpStatus.OK
        );
    }
}