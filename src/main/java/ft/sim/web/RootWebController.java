package ft.sim.web;

/**
 * Created by Sina on 20/02/2017.
 */

import ft.sim.simulation.SimulationController;
import ft.sim.world.map.MapBuilderHelper;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RootWebController {

  private static final transient Logger logger = LoggerFactory.getLogger(RootWebController.class);

  @RequestMapping("/")
  public String index(Model model) {
    return "home";
  }

  @RequestMapping("/hi")
  public String hi(
      @RequestParam(value = "name", required = false, defaultValue = "World") String name,
      Model model) {
    model.addAttribute("name", name);
    return "hi";
  }

  @RequestMapping("/simulation")
  public String simulation(Model model) {
    Map<String, String> maps = MapBuilderHelper.getMaps().stream().collect(Collectors
        .toMap(s -> WordUtils.capitalizeFully(s.replace("-", " ").replace("_", " ")), s -> s,
            (u, v) -> {
              throw new IllegalStateException(String.format("Duplicate key %s", u));
            },
            LinkedHashMap::new));
    model.addAttribute("maps", maps);
    return "simulation";
  }

  @RequestMapping("/trains/start")
  public String trainsStart(Model model) {

    SimulationController.getInstance().startTrains();
    model.addAttribute("name", "Trains started");

    return "hi";
  }


}