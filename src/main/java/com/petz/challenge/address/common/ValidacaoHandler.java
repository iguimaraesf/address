package com.petz.challenge.address.common;

import com.petz.challenge.address.exceptions.EnderecoExistenteException;
import com.petz.challenge.address.exceptions.EnderecoIncompletoException;
import com.petz.challenge.address.exceptions.EnderecoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ValidacaoHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<EnderecoResponse> handleValidacao(MethodArgumentNotValidException ex) {
        EnderecoResponse response = new EnderecoResponse();
        response.setErros(camposComMensagem(ex.getFieldErrors()));
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EnderecoIncompletoException.class)
    public ResponseEntity<EnderecoResponse> handleEnderecoIncompleto(EnderecoIncompletoException ex) {
        EnderecoResponse response = new EnderecoResponse();
        response.setErros(umErro("O endereço está incompleto"));
        return new ResponseEntity(response, HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(EnderecoNaoEncontradoException.class)
    public ResponseEntity<EnderecoResponse> handleEnderecoNaoEncontrado(EnderecoNaoEncontradoException ex) {
        EnderecoResponse response = new EnderecoResponse();
        response.setErros(umErro("O endereço não existe"));
        return new ResponseEntity(response, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(EnderecoExistenteException.class)
    public ResponseEntity<EnderecoResponse> handleEnderecoExistente(EnderecoExistenteException ex) {
        EnderecoResponse response = new EnderecoResponse();
        response.setErros(umErro("Este endereço já está cadastrado"));
        return new ResponseEntity(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<EnderecoResponse> handleSQLException(SQLException ex) {
        EnderecoResponse response = new EnderecoResponse();
        response.setErros(umErro("Erro gravando os dados: " + ex.getMessage()));
        return new ResponseEntity(response, HttpStatus.PRECONDITION_REQUIRED);
    }

    private Map<String, String> umErro(String mensagem) {
        Map<String, String> msg = new HashMap<>();
        msg.put("endereco", mensagem);
        return msg;
    }

    private Map<String, String> camposComMensagem(List<FieldError> lista) {
        Map<String, String> map = new HashMap<>();
        lista.forEach(fieldError -> map.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return map;
    }
}
