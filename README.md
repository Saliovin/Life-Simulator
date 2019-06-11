# Life-Simulator

A program that simulates life in a defined area. It is written in Java and uses JavaFX for its GUI. You can download and run the "Life Simulator.jar" file if you want to try it out.

## Simulator Rules

- Simulation starts with a number of creatures and food spawned in.
- Food spawns every second and does not expire till eaten.
- Creatures lose energy when moving. Amount depends on the size, sight, and speed of the creature.
- Creatures die when their energy reaches 0.
- Creatures cannot move outside the defined area.
- Creatures obtain energy from eating food/prey.
- Creatures 1.2 times larger than another will consider it as food/prey.
- Creatures move randomly until food/prey enters their sight radius which they will move towards it.
- Creatures will move away from predators within their sight radius. Takes priority over food.
- Creatures whose energy reaches the replication threshold will spawn another creature and lose half their energy.
- Newly spawned creatures have the same properties as their parent but has a chance of one property being modified.
- Creatures spawn with a set amount of energy. Amount is half the replication threshold.

## Simulation Settings
### Entities
- **Food energy**: Amount of energy provided when eaten.
- **Prey energy**: Amount of energy provided when eaten.
- **Replication threshold**: Amount of energy needed to spawn a creature.
### Environment
- **Area width**: Width of the simulation area.
- **Area height**: Height of the simulation area.
- **Food spawn rate**: Amount of food spawned per second.
- **Mutation chance**: Percent chance of a mutation occuring when spawning a creature.
### Initial Values
- **Creature count**: Number of creatures spawned at the start of a simulation.
- **Food count**: Number of food spawned at the start of a simulation.
- **Size**: Starting size of creatures.
- **Speed**: Starting speed of creatures.
- **Sight**: Starting sight of creatures.
### Mutations
- **Speed**: When enabled, creatures now have a chance of spawing with speed modified.
- **Size**: When enabled, creatures now have a chance of spawing with size modified.
- **Sight**: When enabled, creatures now have a chance of spawing with sight modified.

Note: This program has no scientific backing. It was made for fun and learning/practicing programming.
