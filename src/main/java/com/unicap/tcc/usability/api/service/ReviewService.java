package com.unicap.tcc.usability.api.service;


import com.google.common.collect.Lists;
import com.unicap.tcc.usability.api.models.User;
import com.unicap.tcc.usability.api.models.assessment.AssessmentUserGroup;
import com.unicap.tcc.usability.api.models.dto.review.*;
import com.unicap.tcc.usability.api.models.enums.AssessmentState;
import com.unicap.tcc.usability.api.models.enums.EReviewState;
import com.unicap.tcc.usability.api.models.enums.SectionEnum;
import com.unicap.tcc.usability.api.models.review.Comment;
import com.unicap.tcc.usability.api.models.review.Review;
import com.unicap.tcc.usability.api.repository.AssessmentRepository;
import com.unicap.tcc.usability.api.repository.AssessmentUserGroupRepository;
import com.unicap.tcc.usability.api.repository.ReviewRepository;
import com.unicap.tcc.usability.api.repository.UserRepository;
import com.unicap.tcc.usability.api.utils.PdfGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AssessmentUserGroupRepository assessmentUserGroupRepository;
    private final AssessmentRepository assessmentRepository;
    private final MailSender mailSender;

    public Optional<Review> submitNewReview(ReviewRequestDTO reviewRequestDTO) {
        var optionalAssessment = assessmentRepository.findByUid(reviewRequestDTO.getAssessmentUid());
        if (optionalAssessment.isPresent()) {
            var newReview = Review.builder()
                    .assessment(optionalAssessment.get())
                    .uid(UUID.randomUUID())
                    .limitReviewDate(reviewRequestDTO.getDateLimit())
                    .comments(SectionEnum.getSectionList()
                            .stream()
                            .map(sectionEnum ->
                                    Comment.builder()
                                            .section(sectionEnum)
                                            .comment("")
                                            .build()).collect(Collectors.toSet()))
                    .state(EReviewState.AVAILABLE)
                    .build();
            reviewRepository.save(newReview);
            optionalAssessment.get().setState(AssessmentState.WAITING_REVIEW);
            assessmentRepository.save(optionalAssessment.get());
            var userGroup =
                    assessmentUserGroupRepository.findAllByAssessmentAndAssessmentRemovedDateIsNull(optionalAssessment.get());
            var collaboratorsList =
                    userGroup.stream().map(AssessmentUserGroup::getSystemUser).collect(Collectors.toList());
            var reviwerList =
                    userRepository.findAllByIsReviewerTrueAndRemovedDateIsNullAndUidNotIn(
                            collaboratorsList.stream().map(User::getUid).collect(Collectors.toList()));
            var reviewerEmailList = reviwerList.stream().map(User::getEmail).collect(Collectors.toList());
            mailSender.sendAvailableReviewEmail(optionalAssessment.get(), newReview, reviewerEmailList);
            return Optional.of(newReview);
        }
        return Optional.empty();
    }

    public Set<ReviewListResponseDTO> getAvailableReviews(UUID userUid) {
        var optionalUser = userRepository.findByUidAndRemovedDateIsNull(userUid);
        if (optionalUser.isPresent()) {
            var reviewerReviews = reviewRepository.findAllByReviewerUidAndRemovedDateIsNull(userUid)
                    .stream()
                    .map(review -> ReviewListResponseDTO.builder()
                            .limitReviewDate(review.getLimitReviewDate())
                            .projectName(review.getAssessment().getProjectName())
                            .reviewStatus(review.getState())
                            .reviewUid(review.getUid())
                            .build())
                    .collect(Collectors.toSet());
            var reviewList = reviewRepository.findAllWhereUserEvaluatorIsNotReviewer(optionalUser.get().getId());
            reviewerReviews.addAll(reviewList.stream().map(review -> ReviewListResponseDTO.builder()
                    .limitReviewDate(review.getLimitReviewDate())
                    .projectName(review.getAssessment().getProjectName())
                    .reviewStatus(review.getState())
                    .reviewUid(review.getUid())
                    .build()).collect(Collectors.toSet()));
            return reviewerReviews;
        }
        return Collections.emptySet();
    }

    public Set<ReviewListResponseDTO> getReviewingPlanList(UUID userUid) {
        var optionalUser = userRepository.findByUidAndRemovedDateIsNull(userUid);
        if (optionalUser.isPresent()) {
            var reviewerReviews = reviewRepository.findAllByReviewerUidAndRemovedDateIsNull(userUid)
                    .stream()
                    .map(review -> ReviewListResponseDTO.builder()
                            .limitReviewDate(review.getLimitReviewDate())
                            .projectName(review.getAssessment().getProjectName())
                            .reviewStatus(review.getState())
                            .build())
                    .collect(Collectors.toSet());
            var reviewList = reviewRepository.findAllWhereUserEvaluatorIsNotReviewer(optionalUser.get().getId());
            reviewerReviews.addAll(reviewList.stream()
                    .map(review ->
                            ReviewListResponseDTO.builder()
                                    .limitReviewDate(review.getLimitReviewDate())
                                    .projectName(review.getAssessment().getProjectName())
                                    .reviewStatus(review.getState())
                                    .build())
                    .collect(Collectors.toSet()));
            return reviewerReviews;
        }
        return Collections.emptySet();
    }


    public Optional<Review> findReviewAssessment(BeginReviewDTO beginReviewDTO) {
        return reviewRepository.findByUidAndRemovedDateIsNull(beginReviewDTO.getReviewUid());
    }

    public Optional<Review> beginReview(BeginReviewDTO beginReviewDTO) {
        var optionalReview = reviewRepository.findByUidAndRemovedDateIsNull(beginReviewDTO.getReviewUid());
        if (optionalReview.isPresent()) {
            var optionalReviewer = userRepository.findByUidAndRemovedDateIsNull(beginReviewDTO.getUserUid());
            if (optionalReviewer.isPresent()) {
                optionalReview.get().setReviewer(optionalReviewer.get());
                optionalReview.get().setState(EReviewState.REVIEWING);
                optionalReview.get().getAssessment().setState(AssessmentState.IN_REVIEW);
                reviewRepository.save(optionalReview.get());
                return optionalReview;
            }
        }
        return Optional.empty();
    }

    public Optional<Review> finishReview(FinishReviewDTO finishReviewDTO) {
        var optionalReview = reviewRepository.findByUidAndRemovedDateIsNull(finishReviewDTO.getReviewUid());
        if (optionalReview.isPresent()) {
            optionalReview.get().setState(EReviewState.COMPLETED);
            optionalReview.get().getAssessment().setState(AssessmentState.REVIEWED);
            optionalReview.get().setReviewedDate(LocalDate.now());
            optionalReview.get().setComments(finishReviewDTO.getComments());
            var savedReview = reviewRepository.save(optionalReview.get());
            new Thread(() -> sendFinishedReviewEmails(savedReview)).start();
            return Optional.of(savedReview);
        }
        return Optional.empty();
    }

    private void sendFinishedReviewEmails(Review review) {
        var userList = userRepository.findReviewUsers(review.getId());
        if (CollectionUtils.isNotEmpty(userList)) {
            var userEmailList = userList.stream().map(User::getEmail).collect(Collectors.toSet());
            var fileSource = PdfGenerator.generatePlanReview(review);
            mailSender.sendFinishedReviewEmail(review, Lists.newArrayList(userEmailList), fileSource);
        }
    }

    public Optional<ByteArrayOutputStream> downloadPlanReview(UUID uid) {
        var optionalReview = reviewRepository.findByUid(uid);
        return optionalReview.map(PdfGenerator::generatePlanReview);
    }

    public List<ReviewedPlanDTO> getReviewedPlanList(UUID uid) {
        var userReviews = reviewRepository.findAllWUserReviews(uid);
        if (CollectionUtils.isNotEmpty(userReviews)) {
            return userReviews.stream().map(review ->
                    ReviewedPlanDTO.builder()
                            .limitReviewDate(review.getLimitReviewDate())
                            .reviewedDate(review.getReviewedDate())
                            .projectName(review.getAssessment().getProjectName())
                            .reviewer(review.getReviewer().getName())
                            .reviewUid(review.getUid())
                            .build())
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public Optional<Review> deleteReview(UUID uid) {
        Optional<Review> reviewOptional = reviewRepository.findByUid(uid);
        if (reviewOptional.isPresent()){
            reviewOptional.get().setRemovedDate(LocalDateTime.now());
            return Optional.of(reviewRepository.save(reviewOptional.get()));
        }
        return Optional.empty();
    }

    public Optional<Review> findReviewPlanByUid(UUID uid) {
        return reviewRepository.findByUid(uid);
    }
}
