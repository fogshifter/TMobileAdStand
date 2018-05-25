package com.tmobile.adstand.managedbean;


import com.tmobile.adstand.dto.OptionDTO;
import com.tmobile.adstand.dto.TariffDTO;
import com.tmobile.adstand.service.WebServiceClient;
import com.tmobile.adstand.service.WebSocketEndPoint;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named("index")
@Singleton
public class IndexBean {

    private WebSocketEndPoint webSocket;
    private WebServiceClient wsClient;

    private Map<TariffDTO, List<OptionDTO>> tariffs = new HashMap<>();

    @Inject
    public IndexBean(WebSocketEndPoint webSocket, WebServiceClient wsClient) {
        this.webSocket = webSocket;
        this.wsClient = wsClient;
        update();
    }

    public void update() {
        tariffs.clear();

        List<TariffDTO> tariffs = wsClient.getTariffs();
        List<OptionDTO> options = wsClient.getOptions();

        for(TariffDTO tariff : tariffs) {

            List<OptionDTO> compatibleOptions = options.stream().filter(o -> tariff.getCompatibleOptions().contains(o.getId())).collect(Collectors.toList());

            this.tariffs.put(tariff, compatibleOptions);
        }

        System.out.println("IndexBean::update, tariffs: " + tariffs.size());
        webSocket.updateView();
    }

    public List<TariffDTO> getTariffs() {
        return new ArrayList<>(tariffs.keySet());
    }
}
