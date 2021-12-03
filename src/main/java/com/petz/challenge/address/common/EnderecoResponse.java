package com.petz.challenge.address.common;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class EnderecoResponse {
    private Object dados;
    private Map<String, String> erros;

}
