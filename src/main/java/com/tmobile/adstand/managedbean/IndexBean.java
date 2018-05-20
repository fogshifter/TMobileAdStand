package com.tmobile.adstand.managedbean;


import com.tmobile.adstand.dto.OptionDTO;
import com.tmobile.adstand.dto.TariffDTO;
import com.tmobile.adstand.service.TMobileMessageListener;
import com.tmobile.adstand.service.WebServiceClient;
import com.tmobile.adstand.service.WebSocketEndPoint;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
//import javax.inject.Inject;
import javax.inject.Inject;
import javax.inject.Named;
//import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Singleton(name = "index")
@Named("index")
//@ManagedBean
public class IndexBean {

//    @Inject
//    @EJB
    private WebSocketEndPoint webSocket;

//    @Inject
//    @EJB
    private WebServiceClient wsClient;

//    private TMobileMessageListener messageListener;

    private Map<TariffDTO, List<OptionDTO>> tariffs = new HashMap<>();

    private void downloadTariffs() {
        List<TariffDTO> tariffsList = wsClient.getTariffs();

        for(TariffDTO tariff : tariffsList) {
            tariffs.put(tariff, wsClient.getOptions(tariff.getId()));
        }
    }

//    public IndexBean() {}

    @Inject
    public IndexBean(WebSocketEndPoint webSocket, WebServiceClient wsClient) {
        this.webSocket = webSocket;
        this.wsClient = wsClient;
////        this.messageListener = messageListener;
//
        //downloadTariffs();
    }

    public void update() {
        tariffs.clear();
        downloadTariffs();
        System.out.println("IndexBean::update");
        webSocket.updateView();
    }

    public String getNameTest() {
        return "NAME_TEST";
    }

    public Map<TariffDTO, List<OptionDTO>> getTariffs() {
        return tariffs;
    }
}
