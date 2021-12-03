package com.petz.challenge.address.service;

import com.petz.challenge.address.common.EnderecoVO;
import com.petz.challenge.address.common.GoogleGeoCoding;
import com.petz.challenge.address.exceptions.BaseException;
import com.petz.challenge.address.exceptions.EnderecoNaoEncontradoException;
import com.petz.challenge.address.exceptions.EnderecoIncompletoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class GoogleGeoService {
    public static final String API_KEY = "apiKey";
    private final Environment env;

    @Autowired
    public GoogleGeoService(Environment env) {
        this.env = env;
    }

    public GoogleGeoCoding findGeoCoordinates(EnderecoVO address) throws BaseException {
        String components = "country:" + address.getPais()
                + "|street_number:" + address.getNumero()
                + "|route:" + address.getRua()
                + "|administrative_area_level_1:" + address.getEstado()
                + "|administrative_area_level_2:" + address.getCidade()
                //+ "|postal_code:" + address.getCep()
                + "|locality:" + address.getBairro();

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://maps.googleapis.com/maps/api/geocode/json?components=" + components + "&key=" + env.getProperty(API_KEY);
        GoogleGeoCoding geoCoding = restTemplate.getForObject(url, GoogleGeoCoding.class);
        if (noResults(geoCoding)) {
            throw new EnderecoNaoEncontradoException();
        }
        if (incompleteAddress(geoCoding)) {
            throw new EnderecoIncompletoException();
        }
        return geoCoding;
    }

    private boolean incompleteAddress(GoogleGeoCoding geoCoding) {
        return geoCoding.getResults()[0].isPartialMatch();
    }

    private boolean noResults(GoogleGeoCoding geoCoding) {
        return geoCoding.getResults() == null || geoCoding.getResults().length == 0;
    }

}
