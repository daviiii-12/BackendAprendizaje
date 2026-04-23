package co.edu.usb.cali.reservasCanchamax.controller;

import co.edu.usb.cali.reservasCanchamax.dto.request.CreateCourtRequest;
import co.edu.usb.cali.reservasCanchamax.dto.response.CourtResponse;
import co.edu.usb.cali.reservasCanchamax.service.CourtService;
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

    @PostMapping("/create")
    public ResponseEntity<CourtResponse> createCourt(@RequestBody CreateCourtRequest request) throws Exception {
        CourtResponse response = courtService.createCourt(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourtResponse>> getAllCourts() {
        List<CourtResponse> response = courtService.getAllCourts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}