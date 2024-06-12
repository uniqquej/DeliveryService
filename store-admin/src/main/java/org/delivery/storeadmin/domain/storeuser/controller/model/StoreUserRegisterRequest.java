package org.delivery.storeadmin.domain.storeuser.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.delivery.db.storeuser.enums.StoreUserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StoreUserRegisterRequest {

    @NotBlank(message = "가게 이름은 공백일 수 없습니다.")
    private String storeName;

    @Email
    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    private String password;

//    @NotNull
//    private StoreUserRole role;
}
