package co.edu.usb.cali.reservasCanchamax.dto.request;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
@Builder
@Getter

public class CreateUserRequest {


    @Column(name = "full_name", nullable = false, length = 120)
    private String fullName;

    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;

    @Column(name = "phone", length = 30)
    private String phone;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
}

