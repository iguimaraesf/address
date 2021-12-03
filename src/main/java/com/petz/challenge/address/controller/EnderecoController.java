package com.petz.challenge.address.controller;

import com.petz.challenge.address.common.EnderecoResponse;
import com.petz.challenge.address.common.EnderecoVO;
import com.petz.challenge.address.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    private EnderecoService service;

    @Operation(summary = "Cadastra um novo endereço")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Adicionou um novo endereço", content = {
                    @Content(schema = @Schema(implementation = EnderecoVO.class))
            }),
            @ApiResponse(responseCode = "400", description = "A validação falhou", content = {
                    @Content(schema = @Schema(implementation = EnderecoResponse.class))
            }),
            @ApiResponse(responseCode = "424", description = "O endereço está incompleto", content = {
                    @Content(schema = @Schema(implementation = EnderecoResponse.class))
            }),
            @ApiResponse(responseCode = "409", description = "O endereço já está armazenado", content = {
                    @Content(schema = @Schema(implementation = EnderecoResponse.class))
            }),
            @ApiResponse(responseCode = "428", description = "Erro de gravação dos dados", content = {
                    @Content(schema = @Schema(implementation = EnderecoResponse.class))
            }),
            @ApiResponse(responseCode = "417", description = "O endereço não existe", content = {
                    @Content(schema = @Schema(implementation = EnderecoResponse.class))
            })
    })
    @PostMapping
    public ResponseEntity<EnderecoResponse> criar(@Valid @RequestBody EnderecoVO endereco) throws Exception {
        EnderecoVO enderecoVO = service.criar(endereco);
        EnderecoResponse response = new EnderecoResponse();
        response.setDados(enderecoVO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Modifica os dados de um endereço pelo ID dele")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alterou o endereço", content = {
                    @Content(schema = @Schema(implementation = EnderecoVO.class))
            }),
            @ApiResponse(responseCode = "400", description = "A validação falhou", content = {
                    @Content(schema = @Schema(implementation = EnderecoResponse.class))
            }),
            @ApiResponse(responseCode = "424", description = "O endereço está incompleto", content = {
                    @Content(schema = @Schema(implementation = EnderecoResponse.class))
            }),
            @ApiResponse(responseCode = "409", description = "O endereço já está armazenado", content = {
                    @Content(schema = @Schema(implementation = EnderecoResponse.class))
            }),
            @ApiResponse(responseCode = "428", description = "Erro de gravação dos dados", content = {
                    @Content(schema = @Schema(implementation = EnderecoResponse.class))
            }),
            @ApiResponse(responseCode = "417", description = "O endereço não existe", content = {
                    @Content(schema = @Schema(implementation = EnderecoResponse.class))
            })
    })
    @PutMapping
    public ResponseEntity<EnderecoResponse> alterar(@Valid @RequestBody EnderecoVO endereco) throws Exception {
        EnderecoVO enderecoVO = service.alterar(endereco);
        EnderecoResponse response = new EnderecoResponse();
        response.setDados(enderecoVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtém um endereço pelo código interno dele")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Encontrou o endereço", content = {
                    @Content(schema = @Schema(implementation = EnderecoVO.class))
            }),
            @ApiResponse(responseCode = "417", description = "O endereço não existe", content = {
                    @Content(schema = @Schema(implementation = EnderecoResponse.class))
            })
    })
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponse> ler(@PathVariable Long id) throws Exception {
        EnderecoVO enderecoVO = service.ler(id);
        EnderecoResponse response = new EnderecoResponse();
        response.setDados(enderecoVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Exclui um endereço pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Excluiu o endereço", content = {
                    @Content(schema = @Schema(implementation = EnderecoVO.class))
            }),
            @ApiResponse(responseCode = "417", description = "O endereço não existe", content = {
                    @Content(schema = @Schema(implementation = EnderecoResponse.class))
            })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<EnderecoResponse> excluir(@PathVariable Long id) throws Exception {
        service.excluir(id);
        EnderecoResponse response = new EnderecoResponse();
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Obtém os endereços paginados")
    @GetMapping
    public ResponseEntity<List<EnderecoVO>> lerTodos(
            @RequestParam(defaultValue = "0") Integer pag,
            @RequestParam(defaultValue = "10") Integer tamanho) throws Exception {
        return new ResponseEntity<>(service.lerTodos(pag, tamanho), HttpStatus.OK);
    }
}
