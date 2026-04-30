package com.cloud.jml.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
public class ClienteRequestDTO {

    // FIX: Se agregaron validaciones Jakarta Bean Validation para evitar datos invalidos
    @NotNull(message = "La identificacion es obligatoria")
    private Long identificacion;

    @NotBlank(message = "El campo 'nombres' es obligatorio")
    @Size(max = 100, message = "El campo 'nombres' no puede exceder 100 caracteres")
    private String nombres;

    @NotBlank(message = "El campo 'apellidos' es obligatorio")
    @Size(max = 100, message = "El campo 'apellidos' no puede exceder 100 caracteres")
    private String apellidos;

    @Size(max = 20, message = "El campo 'telefono' no puede exceder 20 caracteres")
    private String telefono;

    @Size(max = 200, message = "El campo 'direccion' no puede exceder 200 caracteres")
    private String direccion;
}
