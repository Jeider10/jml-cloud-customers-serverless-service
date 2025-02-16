package com.cloud.jml.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcedureRequest {

    private String procedureName;
    private List<Object> params;
}
