package com.cloud.jml.service;

import com.cloud.jml.dto.ProcedureRequest;
import com.cloud.jml.dto.ProcedureResponse;
import com.cloud.jml.repository.ProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcedureService {

    private final ProcedureRepository procedureRepository;

    public ProcedureResponse executeProcedure(ProcedureRequest request) {
        return procedureRepository.executeProcedure(request);
    }
}
