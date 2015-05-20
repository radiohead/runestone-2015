## To upload code to robot via bluetooth
nxjc RobotClient.java
nxjlink -o RobotClient.nxj RobotClient
nxjupload -b -r -n ROBOT_NAME RobotClient.nxj

## To start sample server program
nxjpcc RobotTalk.java
nxjpc RobotTalk

**UTU robot name:** *taikaviitta*
