package fr.istic.gm.weplan.server.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private String object;
}