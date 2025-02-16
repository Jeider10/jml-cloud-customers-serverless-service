package com.cloud.jml.controller;

import com.cloud.jml.dto.ProcedureRequest;
import com.cloud.jml.dto.ProcedureResponse;
import com.cloud.jml.service.ProcedureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/infra")
@RequiredArgsConstructor
public class ProcedureController {

    private final ProcedureService procedureService;

    @PostMapping("/execute-procedure")
    public ResponseEntity<ProcedureResponse> executeProcedure(@RequestBody ProcedureRequest request) {
        ProcedureResponse response = procedureService.executeProcedure(request);
        return ResponseEntity.ok(response);
    }
}
