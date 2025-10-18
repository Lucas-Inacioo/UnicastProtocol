package com.example.demo.rip.service;

public class Node extends UCSAP implements NodeInterface {
  public Node(int UCSAPId, String hostName, int port) {
    super(UCSAPId, hostName, port);
  }

  public void UPDataInd(short originUCSAPId, String data) {
    System.out.println("Node " + this.UCSAPId + " received data from UCSAP " + originUCSAPId + ": " + data);
  }

  @Override
  public void run() {
    System.out.println("Running Node " + this.UCSAPId + " on " + this.hostName + ":" + this.port);
    try {
      // validate
      if (!isValidId(this.UCSAPId))   throw new IllegalArgumentException("Invalid Node ID: " + this.UCSAPId);
      if (!isValidIP(this.hostName))  throw new IllegalArgumentException("Invalid host: " + this.hostName);
      if (!isValidPort(this.port))    throw new IllegalArgumentException("Invalid port: " + this.port);

      // main loop:
      while (!Thread.currentThread().isInterrupted()) {
        // work / receive / send
      }
    } catch (IllegalArgumentException exception) {
      // Validation failed
      System.err.println("[NODE " + this.UCSAPId + "] start failed: " + exception.getMessage());
      return;
    } catch (Exception exception) {
      System.err.println("[NODE " + this.UCSAPId + "] crashed: " + exception.getMessage());
    } finally {
      // cleanup resources (close socket, etc.)
      System.out.println("UCSAP Node " + this.UCSAPId + " has stopped.");
    }
  }
}