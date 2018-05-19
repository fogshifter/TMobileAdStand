package com.tmobile.adstand.service;


import com.tmobile.adstand.dto.OptionDTO;
import com.tmobile.adstand.dto.TariffDTO;

//import javax.inject.Singleton;
//import javax.ejb.Stateless;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

//@Stateless
@Named
public class WebServiceClient {

    private final String TARRIFFS_URL = "http://localhost:8080/TMobile/tariffs";
    private final String OPTIONS_URL = "http://localhost:8080/TMobile/options/{0}";

    public List<TariffDTO> getTariffs() {
        List<TariffDTO> tariffs = new ArrayList<>();

        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target(TARRIFFS_URL);
//        tariffs = resource.request(MediaType.APPLICATION_JSON).get(new GenericType<List<TariffDTO>>(){});
        tariffs = resource.request(MediaType.APPLICATION_JSON).get(tariffs.getClass());

//       /* tariffs =*/ resource.request(MediaType.APPLICATION_JSON).get();
        return tariffs;
    }

    public List<OptionDTO> getOptions(int tariffId) {
        List<OptionDTO> options = new ArrayList<>();

        Client client = ClientBuilder.newClient();
        String url = MessageFormat.format(OPTIONS_URL, String.valueOf(tariffId));
        WebTarget resource = client.target(url);
        options = resource.request(MediaType.APPLICATION_JSON).get(options.getClass());

        return options;
    }
}
