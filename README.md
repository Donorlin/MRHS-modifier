# MRHS-modifier
MRHS-modifier is tool used for the manipulation with system of Multiple-Right-Hand-Sides (MRHS) equations. It allows manual or heuristic optimalization of the given system. 
Program can be easily modified and used in various experiments. Thanks to input redirection we can automatize chain of execution of given commands. Implementation of new functionality can be easily (hopefully) done thanks to OOP approach.

## Getting Started

Master branch always contains latest source code of the program. These steps will get you a copy of the MRHS-modifier on your local machine.

### Prerequisites

Java installed on your computer is required. Nothing else. 

### Building

You can download all source code contained in src folder of master branch. Then you can import source code into IDE of your preferation and build a jar file. If you own NetBeans IDE simply clone this repository and open it as a project.
Or you can simply download latest release.

## Running the MRHS-modifier

To run the MRHS-modifier simply execute following command in a command line:

```
java -jar MRHS-modifier.jar
```

Program supports one additional option:

```
-f fileName
```
This will start the program with system of equations given by file fileName. This option is only good for experiments ran by automated scripts. 

### Commands

MRHS-modifier supports these commands:

| Command       | Description   |
|---------------|---------------|
| load | Load a system from file |
| save | Save a system to file |
| random | Generate random MRHS system |
| solve | Execute solver's executable file to solve system |
| info | Print info about system |
| show | Print system on system ouput |
| help | Print list of commands |
| echo | Print message on system output |
| exit | Shut down program |
| swaprows | Swap rows | 
| addrow | XOR row with row |
| deleterow | Delete row |
| swapcol | Swap columns | 
| addcol | XOR column with column |
| deletecol | Delete column |
| swapblocks | Swap equations |
| deleteblock | Delete equation |
| glue | Apply gluing algorithm on two equations |
| permuteblocks | Apply permutation on equation order |
| normalizeS/E | Echelonize system/equation |
| guess | Guess value of specific variable |
| build | Apply heuristic algoritm on system |
| expand | Apply expansion algorithm on system |

For more information about specific command and its synopsis type:

```
help commandName
```


## Contributing

I would be very happy for any suggestions, contributions or even request and complains. Please feel free to contact me. 

## Authors

* **Daniel Jahodka** - *Initial work* - [Donorlin](https://github.com/Donorlin)

## Acknowledgments

* Big thanks to Pavol Zajac for guidance and knowledge
