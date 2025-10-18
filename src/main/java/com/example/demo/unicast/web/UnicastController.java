package com.example.demo.unicast.web;

import com.example.demo.simulation.SimulationManager;
import com.example.demo.simulation.SimulationManager.UCSAPConfig;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/simulation")
public class UnicastController {

  private final SimulationManager simulationManager;

  public UnicastController(SimulationManager simulationManager) {
    this.simulationManager = simulationManager;
  }

  /** POST /api/simulation/configs?path=/absolute/path/to/config.txt
   * Load UCSAP configurations from a file and prepare the nodes.
  */
  @PostMapping("/configs")
  public List<UCSAPConfig> start(@RequestParam("path") String path) {
    return simulationManager.startAllFromConfig(Path.of(path));
  }

  /** GET /api/simulation/configs
   * Get the most recently loaded UCSAP configurations.
   * 
   * @return the most recently loaded UCSAP configurations
  */
  @GetMapping("/configs")
  public List<UCSAPConfig> configs() {
    return simulationManager.getLastLoaded();
  }

  /** GET /api/simulation/start
   * Start all UCSAP nodes from the most recently loaded configuration.
  */
  @GetMapping("/start")
  public void start() {
    simulationManager.startNodes();
  }
}
