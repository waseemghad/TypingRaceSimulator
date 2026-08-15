# TypingRaceSimulator

Object Oriented Programming Project — ECS414U

## Project Structure

```
TypingRaceSimulator/
├── Part1/                 # Textual simulation (Java, command-line)
│   ├── TypingRace.java        # Public - main race logic
│   └── Typist.java            # Package-private - typist data/behavior
└── Part2/                 # GUI simulation (Java Swing User Interface)
│   ├── TypingRaceGUI.java        # Public - GUI implementation and runs some game logic
│   └── GameSpecs.java            # Public - class to support with the game modifications
```

# Part 1 — Textual Simulation

### How to compile

Note: now compiles differently, old method won't work

Open command prompt/terminal and type:
```bash
javac Part1/*.java
```

### How to run

The race is started by calling `startRace()` on a `TypingRace` object.
A simple way to change the icons, names or accuracy is to locate and edit the `main` method to `TypingRace`, for example:

```java
    public static void main(String[] args)                              // values below can be changed
    {
        TypingRace race = new TypingRace(40);                           // '40' is the race length, and can be changed.
        race.addTypist(new Typist('①', "TURBOFINGERS", 0.85), 1);      // '①' is the character, can be changed into any desired SINGLE
        race.addTypist(new Typist('②', "QWERTY_QUEEN",  0.60), 2);     // character (same with ② & ③); "TURBOFINGERS" is player name
        race.addTypist(new Typist('③', "HUNT_N_PECK",   0.30), 3);     // number (e.g. 0.85) is the player accuracy between 0 and 1.
        race.startRace();
    }
```

Then run:

```bash
java part1.TypingRace.java
```

## Dependencies

- Java Development Kit (JDK) 11 or higher

## Notes

- Compiling and running is now different - must be done from TypingRaceSimulator directory
- If running in terminal, the game only supports three Typists

# Part 2 — Graphics User Interface Simulation

### How to compile

Open command prompt/terminal and type:
```bash
javac Part1/*.java Part2/*.java
```

### How to run

Run:
```bash
java part2.TypingRaceGUI.java
```

## Dependencies

- Java Development Kit (JDK) 11 or higher