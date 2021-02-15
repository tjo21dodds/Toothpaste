# Toothpaste
## Design Guide
The point of Toothpaste is to be a general purpose simulation. I have a lot of project ideas that would rely on a fluid dynamics engine, so I thought I ought to make one.

## Layers
Layers can be used to apply forces to particles in a certain region. For example gravity or a radiator.

## Particles
Particles can be given 2 vectors; position and velocity (Mass has no current effect). Acceleration is calculated by the resultant of the force from the layers.

## GUI 
Velocity is indicated by color. Shows a 2D projection.
### Future
I want to add a GUI for adding layers and better interaction with a 3d space. Currently in the Event-B stage.

![Point source](img/Screenshot 2021-02-15 160707.png)
