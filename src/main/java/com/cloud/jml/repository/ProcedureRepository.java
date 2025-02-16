package com.cloud.jml.repository;

import com.cloud.jml.dto.ProcedureRequest;
import com.cloud.jml.dto.ProcedureResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class ProcedureRepository {

    private static final Logger log = LoggerFactory.getLogger(ProcedureRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public ProcedureRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ProcedureResponse executeProcedure(ProcedureRequest request) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName(request.getProcedureName());

            Map<String, Object> out = jdbcCall.execute(request.getParams().toArray());

            return new ProcedureResponse(true, "Procedimiento ejecutado correctamente", List.of(out));
        } catch (Exception e) {
            log.error("Error ejecutando procedimiento {}: {}", request.getProcedureName(), e.getMessage());
            return new ProcedureResponse(false, "Error ejecutando procedimiento", null);
        }
    }
}
