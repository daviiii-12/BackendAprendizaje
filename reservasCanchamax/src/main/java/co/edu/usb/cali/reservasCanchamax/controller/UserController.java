package co.edu.usb.cali.reservasCanchamax.controller;


import co.edu.usb.cali.reservasCanchamax.model.User;
import co.edu.usb.cali.reservasCanchamax.repositorio.UserRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepositorio userRepositorio;

   @GetMapping("/ping")
    public String  ping(){
        return "pong";
    }
    @GetMapping("/all")
    public List<User>  getAllUsers(){
       return userRepositorio.findAll();
    }

    @GetMapping("/{id}")  
    public ResponseEntity<User> getUserById(@PathVariable int id){
       return new ResponseEntity<>(
               userRepositorio.getReferenceById(id),
               HttpStatus.OK);
    }
}
