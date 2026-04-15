package co.edu.usb.cali.reservasCanchamax.mapper;

import co.edu.usb.cali.reservasCanchamax.dto.response.GetUserResponse;
import co.edu.usb.cali.reservasCanchamax.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    // MÉTODO 1: Convierte un solo User a GetUserResponse
    public static GetUserResponse entityToGetUserResponse(User user) {
        // instanciar nuevo objeto
        GetUserResponse getUserResponse = GetUserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .build();

        return getUserResponse;
    }

    // MÉTODO 2: Convierte una lista de Users a una lista de GetUserResponse
    public static List<GetUserResponse> entityToListGetUserResponse(List<User> users) {

        /* // VERSIÓN ANTIGUA CON FOR (Comentada)
        List<GetUserResponse> getUserResponseList = new ArrayList<>();
        // iterar sobre lista de objetos y agregarlos a la lista objetos userResponse
        for(int i = 0; i < users.size(); i++){
            // por cada iteraccion obtener el objeto actual
            User user = users.get(i);
            GetUserResponse getUserResponse = entityToGetUserResponse(user);
            // agregar el objeto dto get user
            getUserResponseList.add(getUserResponse);
        }
        // retornar la lista Dto GetuserResponse
        return getUserResponseList;
        */

        // VERSIÓN MODERNA Y CORTA CON STREAMS (Funcional)
        return users.stream()
                .map(UserMapper::entityToGetUserResponse)
                .toList();
    }
}