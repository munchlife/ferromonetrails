# Ferromone Trails - Adaptive Subway Vehicle Signaling
[![Build Status](https://travis-ci.org/sinaa/train-simulator.svg?branch=master)](https://travis-ci.org/sinaa/train-simulator)
### Ferromone Trails

Ferromone Trails mimics swarm intelligence strategies. As vehicles leave their yards, or their “colonies”, they seek pheromone Signals dropped in the subway by other vehicles. These pheromones direct vehicles to lines and stations according to demand.

Ferromone Trails currently includes strategies used by ant colonies. Future explorations include signaling strategies used by bacterial colonies.

![Train Simulator Screenshot](gh/screenshot.png)

# Getting Started
## Download

[Download Latest Release: 1.0.0](https://github.com/sinaa/train-simulator/releases/download/1.0.0/train-simulator-1.0.0-SNAPSHOT.jar)

## Run it!

Start by executing the program (Notice: Java 1.8 is required):

    java -jar train-simulator.jar

Navigate to http://localhost:8080 and follow the intstructions on the page to run your desired simulation.

![Simulator Menu](gh/menu.png)

## Runtime Arguments

### Custom Maps

Maps can be created in YAML format. You can pass custom maps to the program using the argument: 

    java -jar train-simulator.jar --maps=path/to/my_map.yaml,path/to/map/folder,...
    
All maps used to evaluate this study are [available here](https://github.com/sinaa/train-simulator/releases/download/1.0.0/experiment-maps.zip).

### Output folder

By default, the results will be exported to the `./results` directory in the current working directory. This can be changed by passing the runtime argument `--output=my/custom/path`.

# Development Status

The simulator application was built under the Department for Transport T-TRIG grant, and since the objectives of the study have already been investigated, the project is no longer actively maintained. However, contributions are more than welcome!

# License
This project is released under GPL License. Please review [License file](LICENSE) for more details.
