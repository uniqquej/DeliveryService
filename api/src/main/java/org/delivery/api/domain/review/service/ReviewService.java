package org.delivery.api.domain.review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.UserErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.review.controller.model.ReviewUpdateRequest;
import org.delivery.api.domain.user.model.User;
import org.delivery.db.review.ReviewEntity;
import org.delivery.db.review.ReviewRepository;
import org.delivery.db.review.enums.ReviewStatus;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.user.UserRepository;
import org.delivery.db.user.enums.UserStatus;
import org.delivery.db.userorder.UserOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final UserOrderRepository userOrderRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public ReviewEntity register(ReviewEntity reviewEntity, User user, Long userOrderId, Long storeId){
        var userEntity = userRepository.findFirstByIdAndStatusOrderByIdDesc(user.getId(), UserStatus.REGISTERED)
                .orElseThrow(()->new ApiException(UserErrorCode.USER_NOT_FOUND));

        var userOrderEntity = userOrderRepository.findById(userOrderId)
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));

        userOrderEntity.addReview(reviewEntity);

        var storeEntity = storeRepository.findById(storeId)
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));

        storeEntity.addReview(reviewEntity);

        return Optional.ofNullable(reviewEntity)
                .map(it->{
                   reviewEntity.setRegisteredAt(LocalDateTime.now());
                   reviewEntity.setUser(userEntity);
                   reviewEntity.setStatus(ReviewStatus.REGISTERED);
                    return reviewRepository.save(reviewEntity);
                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public ReviewEntity getReviewWithThrow(Long id){
        return reviewRepository.findById(id).orElseThrow(
                ()->new ApiException(ErrorCode.NULL_POINT)
        );
    }

    public List<ReviewEntity> searchByStoreId(Long storeId){
        return reviewRepository.findAllByStoreIdAndStatusOrderByIdDesc(storeId, ReviewStatus.REGISTERED);
    }

    public List<ReviewEntity> searchByUserId(Long userId){
        return reviewRepository.findAllByUserIdAndStatusOrderByIdDesc(userId, ReviewStatus.REGISTERED);
    }

    @Transactional
    public ReviewEntity updateReview(Long reviewId, ReviewUpdateRequest request, User user){
        var originReview = reviewRepository.findById(reviewId)
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));

        if(originReview.getUser().getId() != user.getId())
            throw new ApiException(UserErrorCode.AUTHENTICATION_ERROR);

        originReview.setContent(request.getContent());
        originReview.setStar(request.getStar());
        originReview.setUpdatedAt(LocalDateTime.now());
        return originReview;
    }

}
