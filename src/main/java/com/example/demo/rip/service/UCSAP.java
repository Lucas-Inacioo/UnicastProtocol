package com.example.demo.rip.service;

public abstract class UCSAP implements Runnable {
  protected final int UCSAPId;
  protected final String hostName;
  protected final int port;

  protected UCSAP(int UCSAPId, String hostName, int port) {
    this.UCSAPId = UCSAPId;
    this.hostName = hostName;
    this.port = port;
  }

  protected boolean isValidId(int id) {
    return id >= 0;
  }

  protected boolean isValidIP(String ip) {
    if (ip.equals("localhost")) return true;

    String[] parts = ip.split("\\.");
    if (parts.length != 4) return false;
    for (String part : parts) {
      try {
        int num = Integer.parseInt(part);
        if (num < 0 || num > 255) return false;
      } catch (NumberFormatException e) {
        return false;
      }
    }
    return true;
  }

  protected boolean isValidPort(int port) {
    return port > 1024;
  }

  @Override
  public void run() {
    System.out.println("Running UCSAP Node " + UCSAPId + " on " + hostName + ":" + port);
    try {
      // validate
      if (!isValidId(UCSAPId))   throw new IllegalArgumentException("Invalid UCSAP ID: " + UCSAPId);
      if (!isValidIP(hostName))  throw new IllegalArgumentException("Invalid host: " + hostName);
      if (!isValidPort(port))    throw new IllegalArgumentException("Invalid port: " + port);

      // main loop:
      while (!Thread.currentThread().isInterrupted()) {
        // work / receive / send
      }
    } catch (IllegalArgumentException exception) {
      // Validation failed
      System.err.println("[NODE " + UCSAPId + "] start failed: " + exception.getMessage());
      return;
    } catch (Exception exception) {
      System.err.println("[NODE " + UCSAPId + "] crashed: " + exception.getMessage());
    } finally {
      // cleanup resources (close socket, etc.)
      System.out.println("UCSAP Node " + UCSAPId + " has stopped.");
    }
  }
}