package com.safalifter.jobservice.controller;

import com.safalifter.jobservice.dto.JobDto;
import com.safalifter.jobservice.request.job.JobCreateRequest;
import com.safalifter.jobservice.request.job.JobUpdateRequest;
import com.safalifter.jobservice.service.JobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/job-service/job")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    private final ModelMapper modelMapper;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JobDto> createJob(@RequestBody JobCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(jobService.createJob(request), JobDto.class));
    }

    @PostMapping("/getJobsThatFitYourNeeds")
    ResponseEntity<List<JobDto>> getJobsThatFitYourNeeds(@RequestBody String needs) {
        return ResponseEntity.ok(jobService.getJobsThatFitYourNeeds(needs).stream()
                .map(job -> modelMapper.map(job, JobDto.class)).toList());
    }

    @GetMapping("/getAll")
    ResponseEntity<List<JobDto>> getAll() {
        return ResponseEntity.ok(jobService.getAll().stream()
                .map(job -> modelMapper.map(job, JobDto.class)).toList());
    }

    @GetMapping("/getJobById/{id}")
    ResponseEntity<JobDto> getJobById(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(jobService.getJobById(id), JobDto.class));
    }

    @GetMapping("/getJobsByCategoryId/{id}")
    ResponseEntity<List<JobDto>> getJobsByCategoryId(@PathVariable String id) {
        return ResponseEntity.ok(jobService.getJobsByCategoryId(id).stream()
                .map(job -> modelMapper.map(job, JobDto.class)).toList());
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JobDto> updateJob(@RequestBody JobUpdateRequest request) {
        return ResponseEntity.ok(modelMapper.map(jobService.updateJob(request), JobDto.class));
    }

    @DeleteMapping("/deleteJobById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> deleteJobById(@PathVariable String id) {
        jobService.deleteJobById(id);
        return ResponseEntity.ok().build();
    }
}
