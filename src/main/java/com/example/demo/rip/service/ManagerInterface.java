package com.example.demo.rip.service;

public interface ManagerInterface extends UnicastServiceUserInterface {
    void UPDataInd(short originUCSAPId, String data);
}