# Devlog

## 18.01.16 [21h-23h]
Starting the project by doing a bit of research about voxel engines
- found the "greedy meshing" algorithm, i decided i will implement it

## 19.01.16 [17h-23h]
Started to code a bit, just enought for a window to show up and added few classes

## 20.01.16 [17h-21h]
Read about the greedy meshing algorithm during all my spare time, i eventually understood it, i shall try to implement it tomorrow

## 21.01.16 [17h-23h]
Successfully implemented greedy meshing, but i'm having a not so good framerate and loading time, it sure needs optimization

## 22.01.16  [13h-22h]
Continuing to optimize the rendering process, i'm trying to merge all vertices / normals / colors / indices on 3 array for each chunk to reduce overhead.
=> merged data successfully ! The loading time and rendering delay is reduced. => {~1600 fps on desktop, ~250 fps on mac book air}\
Implemented a procedural terrain generation

## 23.01.16 [11h-16]
Fixed an error in the mesher limiting it to a squared chunk.
Now that the "backend" is all done, i have trouble knowing what features i should implement

## 24.01.16 [20h-22h]
Added a console for user interaction, started to add commands

## 25.01.16 [21h-22h]
Added the "give" command which will allow me to implement the adding/removing of blocks later on

## 27.01.16 [20h-23h]
Fixed the mouse picker and started to set up for block adding/removing (actually implemented removing block)

## 28.01.16 [20h-21h]
In the train, started to implement file saving/loading

## 29.01.16 [15h-23h]
Continuing to implement saving/loading, and done!

## 30.01.16 [18h-20h30]
Added UI, changed controls a bit