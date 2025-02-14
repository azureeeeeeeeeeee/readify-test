package com.test.backend.services;

import com.test.backend.DTO.AuthResult;
import com.test.backend.DTO.JsonResponse;
import com.test.backend.DTO.ReportDTO;
import com.test.backend.models.Report;
import com.test.backend.models.Review;
import com.test.backend.permissions.ReportPermissions;
import com.test.backend.repositories.ReportRepository;
import com.test.backend.repositories.ReviewRepository;
import com.test.backend.utilities.UserUtils;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportServices {
    private final ReportRepository reportRepository;
    private final UserUtils userUtils;
    private final ReportPermissions reportPermissions;
    private final ReviewRepository reviewRepository;

    public ReportServices(ReportRepository reportRepository, UserUtils userUtils, ReportPermissions reportPermissions, ReviewRepository reviewRepository) {
        this.reportRepository = reportRepository;
        this.userUtils = userUtils;
        this.reportPermissions = reportPermissions;
        this.reviewRepository = reviewRepository;
    }

    public ResponseEntity<JsonResponse<Object>> createReport(Integer id, Report report) {
        JsonResponse<Object> response = new JsonResponse<>();
        Optional<Review> checkReview = reviewRepository.findById(id);

        if (checkReview.isEmpty()) {
            response.setMessage("Review with id " + id + " is not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // authz ? as long as the user is logged in (already done by jwt auth filter)
        report.setUser(userUtils.getUser());
        report.setReview(checkReview.get());
        report.setResolved(false);

        Report savedReport = reportRepository.save(report);

        response.setMessage("Report successfully made");
        response.setData(new ReportDTO(savedReport));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<JsonResponse<Object>> getAllReport() {
        JsonResponse<Object> response = new JsonResponse<>();

        // authz
        AuthResult authResult = reportPermissions.checkFindAll();
        if (!authResult.isAllowed()) {
            response.setMessage(authResult.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        List<ReportDTO> data = reportRepository.findAll().stream().map(ReportDTO::new).toList();

        response.setMessage("Success");
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<JsonResponse<Object>> resolvedReport(Integer id) {
        JsonResponse<Object> response = new JsonResponse<>();

        Optional<Report> checkReport = reportRepository.findById(id);

        if (checkReport.isEmpty()) {
            response.setMessage("Report not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Report report = checkReport.get();

        // authz
        AuthResult authResult = reportPermissions.checkResolve();
        if (!authResult.isAllowed()) {
            response.setMessage(authResult.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        report.setResolved(true);

        Report savedReport = reportRepository.save(report);

        response.setMessage("successfuily resolved a report");
        response.setData(new ReportDTO(savedReport));

        return ResponseEntity.ok(response);
    }
}
