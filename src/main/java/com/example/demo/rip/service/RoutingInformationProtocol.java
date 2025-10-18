package com.example.demo.unicast.service;

import org.springframework.stereotype.Service;

/**
 * Implements the Unicast service user interface: receives PDUs via UdpChannel,
 * extracts origin and data, and notifies the UI.
 */
@Service
public class RoutingInformationProtocol implements UnicastServiceUserInterface {

    /**
     * Receives a Unicast Data Indication from the UdpChannel, extracts the origin UCSAP ID
     * and the application payload, and notifies the UI.
     */
    @Override
    public void UPDataInd(short originUCSAPId, String data) {
        // recebe PDU via UdpChannel, extrai origem e dados, notifica UI
    }
}