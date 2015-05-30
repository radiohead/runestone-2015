package se.uu.it.runestone.teamone.robotcontrol;

import se.uu.it.runestone.teamone.robotcontrol.command.Command;
import java.util.concurrent.TimeUnit;

public class TestCommunicator extends Communicator {
	public TestCommunicator(String brickName, String brickAddress) {
		super(brickName, brickAddress);
	}

	@Override
	public void connect() {
	}

	@Override
	public Boolean sendCommand(Command command) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
	}
}
