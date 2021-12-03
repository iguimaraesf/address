package com.petz.challenge.address.fixture;

import com.petz.challenge.address.common.EnderecoVO;

import java.math.BigDecimal;

public class EnderecoFixture {
    private EnderecoFixture() {

    }

    public static EnderecoVO enderecoEstadosUnidos() {
        EnderecoVO vo = new EnderecoVO();
        vo.setNumero(1600);
        vo.setRua("Amphitheatre Parkway");
        vo.setBairro("Mountain View");
        vo.setEstado("CA");
        vo.setPais("USA");
        return vo;
    }

    public static EnderecoVO enderecoBrasil() {
        EnderecoVO vo = new EnderecoVO();
        vo.setNumero(1316);
        vo.setRua("R. Visc. de Parnaíba");
        vo.setBairro("Mooca");
        vo.setEstado("SP");
        vo.setCidade("São Paulo");
        vo.setPais("Brasil");
        vo.setComplemento("");
        vo.setCep("03164-300");
        return vo;
    }

    public static EnderecoVO enderecoBrasilComCoordenadas() {
        EnderecoVO vo = enderecoBrasil();
        vo.setLatitude(new BigDecimal("-23.5504934"));
        vo.setLongitude(new BigDecimal("-46.6154951"));
        return vo;
    }

    public static EnderecoVO enderecoIncompleto() {
        EnderecoVO vo = new EnderecoVO();
        vo.setNumero(1);
        vo.setRua("R. Que não existe");
        vo.setBairro("Brooklin");
        vo.setEstado("SP");
        vo.setPais("Brasil");
        return vo;
    }

    public static EnderecoVO enderecoInvalido() {
        EnderecoVO vo = new EnderecoVO();
        vo.setNumero(11092);
        vo.setRua("R. Que não existe");
        vo.setBairro("Bairro que não existe");
        vo.setCidade("Não tem");
        vo.setComplemento("");
        vo.setCep("99999-999");
        vo.setPais("Pais que Não existe");
        vo.setEstado("Estado que não existe");
        return vo;
    }
}
