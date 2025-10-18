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

  /** POST /api/simulation/start?path=/absolute/path/to/config.txt */
  @PostMapping("/start")
  public List<UCSAPConfig> start(@RequestParam("path") String path) {
    return simulationManager.startAllFromConfig(Path.of(path));
  }

  /** GET /api/simulation/configs
   * 
   * @return the most recently loaded UCSAP configurations
  */
  @GetMapping("/configs")
  public List<UCSAPConfig> configs() {
    return simulationManager.getLastLoaded();
  }
}
