package com.petz.challenge.address.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.petz.challenge.address.model.Address;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class EnderecoVO {
    private Long id;
    @NotEmpty
    private String rua;
    @NotNull
    private Integer numero;
    @NotNull
    private String complemento;
    @NotEmpty
    private String bairro;
    @NotEmpty
    private String cidade;
    @NotEmpty
    private String estado;
    @NotEmpty
    private String pais;
    @NotEmpty
    private String cep;
    private BigDecimal latitude;
    private BigDecimal longitude;
    @JsonIgnore
    private boolean novo;

    public static EnderecoVO fromNewEntity(Address address) {
        EnderecoVO vo = fromEntity(address);
        vo.setNovo(true);
        return vo;
    }

    public static EnderecoVO fromStoredEntity(Address address) {
        EnderecoVO vo = fromEntity(address);
        vo.setNovo(false);
        return vo;
    }

    private static EnderecoVO fromEntity(Address address) {
        EnderecoVO vo = new EnderecoVO();
        vo.setId(address.getId());
        vo.setBairro(address.getNeighbourhood());
        vo.setCep(address.getZipCode());
        vo.setCidade(address.getCity());
        vo.setComplemento(address.getComplement());
        vo.setLatitude(address.getLatitude());
        vo.setLongitude(address.getLongitude());
        vo.setEstado(address.getState());
        vo.setNumero(address.getNumber());
        vo.setPais(address.getCountry());
        vo.setRua(address.getStreetName());
        return vo;
    }

    public Address toEntity() {
        Address entity = new Address();
        copyToEntity(entity);
        return entity;
    }

    public void copyToEntity(Address entity) {
        entity.setId(id);
        entity.setStreetName(rua);
        entity.setNumber(numero);
        entity.setComplement(complemento);
        entity.setNeighbourhood(bairro);
        entity.setCity(cidade);
        entity.setState(estado);
        entity.setCountry(pais);
        entity.setZipCode(cep);
        entity.setLatitude(latitude);
        entity.setLongitude(longitude);
    }

}
