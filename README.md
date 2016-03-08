# SWLineOfSight

Play with the concept of Line of Sight as defined in the boardgame "Imperial Assault".

By translating Walls to their "inside", most problems are resolved.
except from corner (0,-1).

Some tests in src/test

- src/test/TestJPlane : use left button to change the origin point, right button to change the target edge in a BASIC test that does not check the source of "intersection".

## Todo
* DONE Detect problematic Corners in Cells
* DONE Compute from one Cell to a Segment2D
* DONE Compute from one Cell to another.