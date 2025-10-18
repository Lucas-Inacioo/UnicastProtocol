package com.example.demo.simulation;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.nio.file.Path;
import java.util.*;

@Component
public class SimulationManager {

  public static final class UCSAPConfig {
    public final String UCSAPId;
    public final String hostName;
    public final int port;

    // Constructor for UCSAPConfig
    public UCSAPConfig(String UCSAPId, String hostName, int port) {
      this.UCSAPId = UCSAPId;
      this.hostName = hostName;
      this.port = port;
    }
  }

  // Holds the most recently loaded UCSAP configurations so we can expose them via GET
  private final List<UCSAPConfig> lastLoaded = new ArrayList<>();

  /** Loads <UCSAP_id> <host_name> <port_number> lines from a file and returns them.
   * 
   * @param path the path to the configuration file, a TSV with UCSAP_id, host_name, port_number per line
   *
   * @return an unmodifiable list of UCSAPConfig objects parsed from the file
  */
  public List<UCSAPConfig> startAllFromConfig(Path path) {
    // Create a list to hold the parsed configurations
    List<UCSAPConfig> configs = new ArrayList<>();

    // Read the file line by line
    try (var reader = java.nio.file.Files.newBufferedReader(path, java.nio.charset.StandardCharsets.UTF_8)) {
      String line; int lineNumber = 0;
      while ((line = reader.readLine()) != null) {
        lineNumber++;
        line = line.trim();
        if (line.isEmpty() || line.startsWith("#")) continue;

        // Split the line into parts
        String[] parts = line.split("\\s+");

        // Check if we have exactly 3 parts (UCSAP_id, host_name, port_number)
        if (parts.length != 3) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid config line " + lineNumber + ": " + line);
        }

        // Check that UCSAP_id is a natural number
        try {
          int ucsapId = Integer.parseInt(parts[0]);
          if (ucsapId < 0) throw new NumberFormatException();
        } catch (NumberFormatException exception) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid UCSAP ID at line " + lineNumber + ": " + parts[0]);
        }

        // Check that host_name is either 'localhost' or a valid ipv4
        String host = parts[1];
        if (!host.equals("localhost")) {
          try {
            java.net.InetAddress inetAddress = java.net.InetAddress.getByName(host);
            if (!(inetAddress instanceof java.net.Inet4Address)) {
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Host is not a valid IPv4 address at line " + lineNumber + ": " + host);
            }
          } catch (java.net.UnknownHostException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid host name at line " + lineNumber + ": " + host, exception);
          }
        }

        // Check that port_number is a valid port (>1024)
        int port = Integer.parseInt(parts[2]);
        if (port < 1025) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid port number at line " + lineNumber + ": " + parts[2]);

        // Add the parsed configuration to the list
        configs.add(new UCSAPConfig(parts[0], parts[1], port));
      }
    } catch (org.springframework.web.server.ResponseStatusException exception) {
      // If we already have a ResponseStatusException, rethrow it
      throw exception;
    } catch (Exception exception) {
      // If we have a different kind of exception, wrap it in a ResponseStatusException
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to load UCSAP configurations from file: " + path, exception);
    }

    // Update the lastLoaded list (thread-safe)
    synchronized (lastLoaded) {
      lastLoaded.clear();
      lastLoaded.addAll(configs);
    }
    return List.copyOf(configs);
  }

  /** Exposes the most recently loaded configs
   * 
   * @return an unmodifiable list of UCSAPConfig objects representing the last loaded configurations
  */
  public List<UCSAPConfig> getLastLoaded() {
    synchronized (lastLoaded) { return List.copyOf(lastLoaded); }
  }
}
