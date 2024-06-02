package org.delivery.api.domain.store.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;

import java.util.List;
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDetailResponse {
    private StoreResponse store;
    private List<StoreMenuResponse> menuList;
}
