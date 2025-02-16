package com.cloud.jml.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcedureResponse {

    private boolean success;
    private String message;
    private List<Object> data;
}
