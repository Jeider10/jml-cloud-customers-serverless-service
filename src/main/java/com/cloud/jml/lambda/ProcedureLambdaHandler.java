package com.cloud.jml.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cloud.jml.dto.ProcedureRequest;
import com.cloud.jml.dto.ProcedureResponse;
import com.cloud.jml.service.ProcedureService;
import org.springframework.stereotype.Component;

@Component
public class ProcedureLambdaHandler implements RequestHandler<ProcedureRequest, ProcedureResponse> {

    private final ProcedureService procedureService;

    public ProcedureLambdaHandler(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    @Override
    public ProcedureResponse handleRequest(ProcedureRequest request, Context context) {
        return procedureService.executeProcedure(request);
    }
}
