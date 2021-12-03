package com.petz.challenge.address.service;

import com.petz.challenge.address.common.GoogleGeoCoding;
import com.petz.challenge.address.exceptions.EnderecoNaoEncontradoException;
import com.petz.challenge.address.exceptions.EnderecoIncompletoException;
import com.petz.challenge.address.fixture.EnderecoFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class GoogleGeoServiceTest {
    @Mock
    private Environment env;
    private GoogleGeoService service;

    @BeforeEach
    void setUp() {
        this.service = new GoogleGeoService(env);
        Mockito.when(env.getProperty("apiKey")).thenReturn("AIzaSyCj0cY2yEvVfYhAaTz3-P2MW-YRKmhz5Uw");
    }
    @Test
    void coordenadasEstadosUnidos() throws Exception {
        GoogleGeoCoding geo = service.findGeoCoordinates(EnderecoFixture.enderecoEstadosUnidos());
        Assertions.assertAll(() -> Assertions.assertEquals(new BigDecimal("37.4230749"), geo.getResults()[0].getGeometry().getLocation().getLat()),
                () -> Assertions.assertEquals(new BigDecimal("-122.0841176"), geo.getResults()[0].getGeometry().getLocation().getLng()));
    }

    @Test
    void coordenadasBrasil() throws Exception {
        GoogleGeoCoding geo = service.findGeoCoordinates(EnderecoFixture.enderecoBrasil());
        Assertions.assertAll(() -> Assertions.assertEquals(new BigDecimal("-23.5504934"), geo.getResults()[0].getGeometry().getLocation().getLat()),
                () -> Assertions.assertEquals(new BigDecimal("-46.6154951"), geo.getResults()[0].getGeometry().getLocation().getLng()));
    }

    @Test
    void enderecoIncompleto() {
        Assertions.assertThrows(EnderecoIncompletoException.class, () -> service.findGeoCoordinates(EnderecoFixture.enderecoIncompleto()),
                "Retornou um endereço incompleto");
    }

    @Test
    void enderecoInvalido() {
        Assertions.assertThrows(EnderecoNaoEncontradoException.class, () -> service.findGeoCoordinates(EnderecoFixture.enderecoInvalido()),
                "Retornou um endereço inválido");
    }
}
