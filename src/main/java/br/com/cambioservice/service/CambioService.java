package br.com.cambioservice.service;

import br.com.cambioservice.model.Cambio;
import br.com.cambioservice.repository.CambioRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CambioService {

    private CambioRepository cambioRepository;

    private Environment environment;

    public CambioService(CambioRepository cambioRepository, Environment environment) {
        this.cambioRepository = cambioRepository;
        this.environment = environment;
    }

    public Cambio listCambio(BigDecimal amount, String from, String to) {

        Cambio cambioPersistido = cambioRepository.findByFromAndTo(from, to);

        if (cambioPersistido == null) throw new RuntimeException("Currency Unsupported");

        var convertedValue = cambioPersistido.getConversionFactor().multiply(amount);

        cambioPersistido.setEvironment(environment.getProperty("local.server.port"));
        cambioPersistido.setConvertedValue(convertedValue);

        return cambioPersistido;
    }

}
