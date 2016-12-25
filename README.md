# GA-AntWorld
Playing around with Genetic Algorithms, Neural Networks, and a synthetic Ant World in Processing3
Set up as an Eclipse project (Eclipse MARS - version 4.5)
Using Processing3 for graphics
and Watchmaker Framework for Genetic Algorithms.

Ants input are: local sensing by heading of: OUTBOUND, INBOUND, FOOD, and HOME (e.g. FOOD at (-1,0))
      (local sensing has max distance of surrnouding 5 x 5 grid; grid points are lableled 
      in a local [x,y] grid system, x = -2, -1, 0 , 1, 2, and the same for y).
  Sensing of internal state: HEALTH (0-10). HEALTH of 0 is dead (will be removed next update).
      HEAHTH is returned 2*(CARRIED-FOOD + 1) when in home. HEALTH decreases over time.
      If HEALTH drops below 4 and Ant is carring food, the food carried is decreased by one
      and HEALTH is increased to 8.
Home is a 5 X 5 grid size box and there are no INBOUND, OUTBOUND, or FOOD objects in it.
Ants outputs are: heading of motion, deposit nothing/INBOUND/OUTBOUND.
  Movement is towards a target. Targets are set by location on local grid 
When FOOD is encountered (within one grid size square) and CARRIED-FOOD is not above max of 4, then +1 to
  CARRIED-FOOD, and FOOD is removed from grid.
  
Ant motion cycles throught two phases: Stand, Move towards target. State is changed from Outbound to Inbound 
  
All ants in the world are controlled by the same "Brain", a four layer nerual network, input, output, and two internal layers.
Fitness Score of antWorld determines evolution of the brain of the world.
Score combines: ant live turns ( the total number of turns that an ant is alive for all the turns that the world is run ).
    food at home (the total amount of food deposited at home)
