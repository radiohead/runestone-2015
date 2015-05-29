package se.uu.it.runestone.teamone.robotcontrol;

import se.uu.it.runestone.teamone.robotcontrol.command.Command;
import se.uu.it.runestone.teamone.robotcontrol.Communicator;

public class TestCommunicator extends Communicator {
	public TestCommunicator(String brickName, String brickAddress) {
		super(brickName, brickAddress);
	}

	@Override
	public void connect() {
		return;
	}

	@Override
	public Boolean sendCommand(Command command) {
		return true;
	}
}
