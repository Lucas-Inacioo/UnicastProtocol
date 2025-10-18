package com.example.demo.rip;

public interface ManagerInterface extends UnicastServiceUserInterface {
    void UPDataInd(short originUCSAPId, String data);
}