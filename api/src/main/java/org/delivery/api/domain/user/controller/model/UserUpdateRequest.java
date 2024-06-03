package org.delivery.api.domain.user.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    @NotBlank(message = "이름은 필수값입니다.")
    private String name;

    @NotBlank(message = "주소는 필수값입니다.")
    private String address;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    private String password;
}
