package com.unicap.tcc.usability.api.resources;

import com.unicap.tcc.usability.api.models.assessment.Assessment;
import com.unicap.tcc.usability.api.models.assessment.question.PlanForm;
import com.unicap.tcc.usability.api.models.dto.review.*;
import com.unicap.tcc.usability.api.models.review.Review;
import com.unicap.tcc.usability.api.service.ReviewService;
import com.unicap.tcc.usability.api.utils.UUIDUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/review")
@Api("Review API")
@RequiredArgsConstructor
public class ReviewResource {

    private final ReviewService reviewService;

    @GetMapping(path = "/by-category/{category}")
    public ResponseEntity<PlanForm> getScaleByUid(@PathVariable("category") String category) {

        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/submit")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Submit a assessment plan to be reviewed.")
    @ApiResponse(code = 200, message = "Review submited.")
    public ResponseEntity<Review> submitReview(@RequestBody @Valid ReviewRequestDTO reviewRequestDTO) {
        Optional<Review> optionalReview = reviewService.submitNewReview(reviewRequestDTO);
        if (optionalReview.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(optionalReview.get());
    }

    @GetMapping("/list/{uid}")
    @ApiOperation("Get list of scales.")
    public ResponseEntity<Set<ReviewListResponseDTO>> getScaleList(
            @PathVariable @Pattern(regexp = UUIDUtils.UUID_REGEXP, message = "Invalid uid") String uid) {
        Set<ReviewListResponseDTO> reviewList = reviewService.getAvailableReviews(UUID.fromString(uid));
        if (reviewList.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(reviewList);
    }

    @PostMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Find a assessment in review phase.")
    @ApiResponse(code = 200, message = "Assessment finded.")
    public ResponseEntity<Review> findReviewAssessment(@RequestBody @Valid BeginReviewDTO beginReviewDTO) {
        Optional<Review>  optionalReview =  reviewService.findReviewAssessment(beginReviewDTO);
        if (optionalReview.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(optionalReview.get());
    }

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Begin plan review.")
    @ApiResponse(code = 200, message = "Review started.")
    public ResponseEntity<Review> beginReview(@RequestBody @Valid BeginReviewDTO beginReviewDTO) {
        Optional<Review>  optionalReview = reviewService.beginReview(beginReviewDTO);
        if (optionalReview.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(optionalReview.get());
    }

    @PostMapping("/finish")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Finish plan review.")
    @ApiResponse(code = 200, message = "Review finished.")
    public ResponseEntity<Review> finishReview(@RequestBody @Valid FinishReviewDTO finishReviewDTO) {
        Optional<Review>  optionalReview = reviewService.finishReview(finishReviewDTO);
        if (optionalReview.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(optionalReview.get());
    }

    @GetMapping("/{uid}/file")
    @ApiOperation("Download plan.")
    public ResponseEntity<byte[]> download(@PathVariable @Pattern(regexp = UUIDUtils.UUID_REGEXP, message = "Invalid uid") String uid) {

        Optional<ByteArrayOutputStream> byteArrayOutputStream = reviewService.downloadPlanReview(UUID.fromString(uid));

        if (byteArrayOutputStream.isEmpty())
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok()
                .contentType(APPLICATION_OCTET_STREAM)
                .body(byteArrayOutputStream.get().toByteArray());
    }

    @GetMapping("/completed/list/{uid}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Finish plan review.")
    @ApiResponse(code = 200, message = "Review finished.")
    public ResponseEntity<List<ReviewedPlanDTO>> getReviewedPlanList(
            @PathVariable @Pattern(regexp = UUIDUtils.UUID_REGEXP, message = "Invalid uid") String uid) {
        List<ReviewedPlanDTO>  reviewedPlanList = reviewService.getReviewedPlanList(UUID.fromString(uid));
        if (reviewedPlanList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(reviewedPlanList);
    }

    @PutMapping("/detete/{uid}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Finish plan review.")
    @ApiResponse(code = 200, message = "Review finished.")
    public ResponseEntity<Review> deleteReview(
            @PathVariable @Pattern(regexp = UUIDUtils.UUID_REGEXP, message = "Invalid uid") String uid) {
        Optional<Review>  review = reviewService.deleteReview(UUID.fromString(uid));
        if (review.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(review.get());
    }

    @GetMapping("/by-uid/{uid}")
    @ApiOperation("Search list of assessment plans.")
    public ResponseEntity<Review> findReviewPlanByUid(@PathVariable(value = "uid") @ApiParam("Assessment plan uid") String uid) {
        Optional<Review> reviewOptional = reviewService.findReviewPlanByUid(UUID.fromString(uid));
        if (reviewOptional.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(reviewOptional.get());
    }
}
