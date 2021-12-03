package com.petz.challenge.address.service;

import com.petz.challenge.address.common.EnderecoVO;
import com.petz.challenge.address.common.GoogleGeoCoding;
import com.petz.challenge.address.common.GoogleLocation;
import com.petz.challenge.address.exceptions.BaseException;
import com.petz.challenge.address.exceptions.EnderecoExistenteException;
import com.petz.challenge.address.exceptions.EnderecoNaoEncontradoException;
import com.petz.challenge.address.model.Address;
import com.petz.challenge.address.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EnderecoService {
    private final AddressRepository repository;
    private final GoogleGeoService googleGeoService;

    @Autowired
    public EnderecoService(AddressRepository repository, GoogleGeoService googleGeoService) {
        this.repository = repository;
        this.googleGeoService = googleGeoService;
    }

    public EnderecoVO criar(EnderecoVO endereco) throws BaseException {
        if (faltaLatitudeOuLongitude(endereco)) {
            GoogleGeoCoding geo = googleGeoService.findGeoCoordinates(endereco);
            GoogleLocation location = geo.getResults()[0].getGeometry().getLocation();
            endereco.setLatitude(location.getLat());
            endereco.setLongitude(location.getLng());
        }
        Address entity = endereco.toEntity();
        return novoEndereco(entity);
    }

    public EnderecoVO alterar(EnderecoVO endereco) {
        Address entity = repository.findById(endereco.getId()).orElseThrow(() -> new EnderecoNaoEncontradoException());
        if (faltaLatitudeOuLongitude(endereco)) {
            GoogleGeoCoding geo = googleGeoService.findGeoCoordinates(endereco);
            GoogleLocation location = geo.getResults()[0].getGeometry().getLocation();
            endereco.setLatitude(location.getLat());
            endereco.setLongitude(location.getLng());
        }
        boolean existe = repository.findFirstByLatitudeAndLongitude(endereco.getLatitude(), endereco.getLongitude()).isPresent();
        if (existe) {
            throw new EnderecoExistenteException();
        }
        endereco.copyToEntity(entity);
        return EnderecoVO.fromStoredEntity(repository.save(entity));
    }

    public EnderecoVO ler(Long id) {
        Address entity = repository.findById(id).orElseThrow(() -> new EnderecoNaoEncontradoException());
        return EnderecoVO.fromStoredEntity(entity);
    }

    public void excluir(Long id) {
        Address entity = repository.findById(id).orElseThrow(() -> new EnderecoNaoEncontradoException());
        repository.deleteById(id);
    }

    private EnderecoVO novoEndereco(Address entity) throws BaseException {
        EnderecoVO vo = repository.findFirstByLatitudeAndLongitude(entity.getLatitude(), entity.getLongitude())
                .map(EnderecoVO::fromStoredEntity)
                .orElse(EnderecoVO.fromNewEntity(repository.save(entity)));
        if (!vo.isNovo()) {
            throw new EnderecoExistenteException();
        }
        return vo;
    }

    private boolean faltaLatitudeOuLongitude(EnderecoVO endereco) {
        return endereco.getLatitude() == null || endereco.getLongitude() == null;
    }

    public List<EnderecoVO> lerTodos(Integer pag, Integer tamanho) {
        Pageable page = PageRequest.of(pag, tamanho);
        Page<Address> entities = repository.findAll(page);
        return entities.stream().map(EnderecoVO::fromStoredEntity).collect(Collectors.toList());
    }
}
