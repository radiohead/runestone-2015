# Runestone 2015 Project

## Goal

The goal of the project is to create a prototype simulating an autonomous warehouse robot and necessary support systems. The purpose is to create a proof of concept of a system that solves a real-world problem using many small systems and aggregated data form many sources. The real-world problem consists of a robot navigating a warehouse floor and storing goods. Some of the goods have requirements for their storage environment, so a suitable storage place has to be retrieved or calculated.

The project consists of four different systems working together in parallel; a robot navigating the warehouse floor, a server supplying the robot with instructions, a set of sensors supplying the server with climate data as well as a client GUI that the warehouse manager can use to assume manual control of the robot.

The main reason for this split is to reduce complexity in the robot.

Since the project is a proof-of-concept and the goal is only to create a prototype, some real world requirements and scenarios will be stripped away in favor of the parts that are crucial for the solution. Some notable limitations are as follows.

A real world implementation such a system would naturally allow multiple robots to navigate the warehouse floor simultaneously. This project scopes that down to one single robot, but the solution will be one such that multiple robot support may be added later on.
The robot will not actually carry any physical object representing the goods. The picking up and dropping off of goods will be simulated, and thus the robot will not be able to do this physically.
An unlimited stock will be assumed at a given loading stock, where the robot can go to pick up goods.
The live view of the robot location in the warehouse in the client GUI will not be updated in actual real time, but close enough.

## Team

- danieleliassen
- akelagercrantz
- Lelli
- helena-bond
- radiohead

