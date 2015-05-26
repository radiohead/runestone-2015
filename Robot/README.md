## Available commands
- **forward** - moves 1 grid size forward (grid size is defined in the code, check the constant)
- **backwards** - moves 1 grid size backwards
- **left** - rotates to left
- **right** - rotates to right
- **shutdown** - stops the program

The robot will reply to each command with either `success` or `fail`

If it fails to reply (which probably means connection died or something), it will stop, close current connection and wait for another connection (check out `Robot#restart`)

## To upload code to robot via bluetooth
```
nxjc RobotClient.java
nxjlink -o RobotClient.nxj RobotClient
nxjupload -b -r -n ROBOT_NAME RobotClient.nxj
```

## To start sample server program
```
nxjpcc RobotTalk.java
nxjpc RobotTalk
```

**UTU robot name:** *taikaviitta*
