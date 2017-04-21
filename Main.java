
public class Main {

	public static void main(String[] args) {
	
		EZ.initialize(500,500);
	
		EZImage backdrop = EZ.addImage("space.jpg", 256, 256);	//Background image
		Player me = new Player("zeroRun.png", 320,320,54,80,3);	//Player image, control player
		
		while (true)	//loop for game
		{
			me.processStates(); //Function for movement/animation of character
								//run left/right, jump
			EZ.refreshScreen();	//Refresh screen
		}
	}

}
