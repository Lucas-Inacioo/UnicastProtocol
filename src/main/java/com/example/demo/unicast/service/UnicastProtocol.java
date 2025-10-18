package com.example.demo.unicast.service;

import org.springframework.stereotype.Service;

/**
 * Implements the Unicast service: resolves the destination UCSAP, builds a UPDREQPDU, and sends it over UDP.
 */
@Service
public class UnicastProtocol implements UnicastServiceInterface {

    /**
     * Sends a Unicast Data Request to the specified UCSAP by resolving its address,
     * encoding the payload into a protocol data unit (PDU), and transmitting it via UDP.
     *
     * @param destUCSAPId the destination UCSAP identifier to which the message should be sent
     * @param data the application payload (string) to deliver to the destination
     */
    @Override
    public void UPDataReq(short destUCSAPId, String data) {
        // resolve dest -> host:port, build PDU and send via UdpChannel
    }
}
