package org.delivery.storeadmin.domain.user.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.storeuser.enums.StoreUserRole;
import org.delivery.db.storeuser.enums.StoreUserStatus;

import java.time.LocalDateTime;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreUserResponse {

    private UserResponse user;
    private StoreResponse store;


    @Data @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponse{

        private Long id;

        private String email;

        private StoreUserStatus status;

        private StoreUserRole role;

        private LocalDateTime registeredAt;

        private LocalDateTime unregisteredAt;

        private LocalDateTime lastLoginAt;

    }

    @Data @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoreResponse{

        private Long id;

        private String name;

    }
}
