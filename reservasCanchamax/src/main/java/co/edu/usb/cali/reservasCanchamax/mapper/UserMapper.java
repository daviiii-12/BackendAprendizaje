package co.edu.usb.cali.reservasCanchamax.mapper;

import co.edu.usb.cali.reservasCanchamax.dto.response.UserResponse;
import co.edu.usb.cali.reservasCanchamax.model.User;

import java.util.List;

public class UserMapper {


    public static UserResponse entityToUserResponse(User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }


    public static List<UserResponse> entityToListUserResponse(List<User> users) {
        return users.stream()
                .map(UserMapper::entityToUserResponse)
                .toList();
    }
}