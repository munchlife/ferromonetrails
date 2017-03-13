package ft.sim.world;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import ft.sim.world.Connectable;
import ft.sim.world.Track;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sina on 21/02/2017.
 */
public class JourneyPath {

  protected Logger logger = LoggerFactory.getLogger(JourneyPath.class);

  private List<Connectable> path = new ArrayList<>();
  private Map<Connectable, Double> connectablePositions = new HashMap<>();
  private BiMap<Connectable, Integer> connectableIndexes = HashBiMap.create();

  private double length = 0;

  public JourneyPath(List<Connectable> path) {
    this.path.addAll(path);
    init();
  }

  public double getConnectableStartingPosition(Connectable connectable) {
    if (connectable == null) {
      throw new NullPointerException("cannot get position of null connectable!!");
    }
    return connectablePositions.get(connectable);
  }

  public double getConnectableEndingPosition(Connectable connectable) {
    if (connectable == null) {
      throw new NullPointerException("cannot get position of null connectable!!");
    }
    Connectable nextConnectable = getNext(connectable);
    if (nextConnectable == null) {
      return length;
    }
    return connectablePositions.get(nextConnectable);
  }

  private void init() {
    // already initialised
    if (length != 0) {
      return;
    }

    for (Connectable c : path) {
      connectablePositions.put(c, length);
      connectableIndexes.put(c, connectableIndexes.size());

      if (c instanceof Track) {
        List<Balise> balises = ((Track) c).getBalises();
        for (Balise b : balises) {
          int relativePosition = ((Track) c).getPlaceablePosition(b);
          b.setPosition(length + relativePosition);
        }
      }
      length += c.getLength();
      //logger.debug("Connectable length: {}", c.getLength());
    }
  }

  public double getPlaceablePosition(Placeable placeable) {
    double position = -1;
    for (Connectable c : path) {
      if (!(c instanceof Track)) {
        continue;
      }
      Track t = (Track) c;
      if (!t.hasPlaceable(placeable)) {
        continue;
      }
      return getConnectableStartingPosition(c) + t.getPlaceablePosition(placeable);
    }

    throw new IllegalArgumentException("The placeable did not exist on the given journey path");
  }

  public double getLength() {
    return length;
  }

  public Connectable getNext(Connectable c) {
    int index = connectableIndexes.get(c);
    int nextIndex = index + 1;
    if (nextIndex >= connectableIndexes.size()) {
      return null;
    }
    return path.get(nextIndex);
  }

  public Connectable getPrevious(Connectable c) {
    int index = connectableIndexes.get(c);
    int previousIndex = index - 1;
    if (previousIndex < 0) {
      return null;
    }
    return path.get(previousIndex);
  }

  public List<Connectable> getPath() {
    return path;
  }

  public List<Connectable> getConnectablesBetween(double from, double to) {

    List<Connectable> connectables = new ArrayList<>();

    if (from < 0 || to > getLength()) {
      throw new IllegalArgumentException("Invalid From/To arguments");
    }

    if (from > to) {
      double tmp = from;
      from = to;
      to = tmp;
    }

    double calculated = 0;
    for (Connectable c : path) {
      double connectableLength = c.getLength();

      if (connectables.isEmpty()) {
        if (from >= calculated && from < calculated + connectableLength) {
          connectables.add(c);
        }
      } else {
        if (to >= calculated) {
          connectables.add(c);
        } else {
          break;
        }
      }

      calculated += c.getLength();
    }

    return connectables;
  }

  public Connectable getFirst() {
    if (path.size() == 0) {
      return null;
    }
    return path.get(0);
  }

  public Connectable getLast() {
    if (path.size() == 0) {
      return null;
    }
    return path.get(path.size() - 1);
  }

}