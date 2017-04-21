public class Player {
	
	EZImage spriteSheet;	//Declares EZImage called spriteSheet
	
	int x = 10;				// x position of Sprite
	int y = 0;				// y position of Sprite
	int spriteWidth;		// Width of each sprite
	int spriteHeight;		// Height of each sprite
	int direction = 0;		// Direction character is walking in
	int walkSequence = 0;	// Walk sequence counter
	int cycleSteps;			// Number of steps before cycling to next animation step
	int counter = 0;		// Cycle counter
	int jumpCounter = 0;
	
	int playerState = STAND;// Set player state to STAND
	EZImage zeroStand;		// Declares EZImage called zeroStand
	EZImage zeroJump;		// Declares EZImage called zeroJump
	EZImage zeroLand;		// Declares EZImage called zeroLand
	
	static final int STAND = 1; 			// Create static final int called STAND, set to 1
	static final int JUMP = 2;				// Create static final int called JUMP, set to 2
	static final int LAND = 3;				// Create static final int called LAND, set to 3
	static final int HOVER = 4;				// Create static final int called HOVER, set to 4
	static final int JUMPHEIGHT = 120; 		// Create static final int called JUMPHEIGHT, set to 120
	
	Player(String imgFile, int startX, int startY, int width, int height, int steps) {
		x = startX; //set int x to startX
		y = startY;	//set int y to startY
		spriteWidth = width; //set spriteWidth to width
		spriteHeight = height; //set spriteHeight to height
		cycleSteps = steps; //sets cycleSteps
		spriteSheet = EZ.addImage(imgFile, startX, y); //creates spriteSheet image for animations
		zeroJump = EZ.addImage("zeroJump.png", startX, startY);	//Jump picture
		zeroJump.hide();	//hides (loaded) jump picture
		zeroLand = EZ.addImage("zeroLand.png", startX, startY);	//Land picture
		zeroLand.hide();	//hides (loaded) land picture
		setImagePosition();	//movement and animation function
	}
	
	void processStates() { //function for states of player
		switch(playerState) {	//switch statement
		
		case STAND:									//when in STAND, can:
			movePlayer();							//move player left and right
			if (EZInteraction.isKeyDown('w')) {		//jump, land
											
				playerState = JUMP;	//sets playerState to JUMP
				jumpCounter = 0;	//sets jumpCounter to 0
			}
			break;	
		case JUMP: 									//when in JUMP:
			jumpCounter += 7;						//increments jumpCounter by 7 until it hits/passes JUMPHEIGHT(120)
			movePlayer();							//move player left and right
			zeroJump.show();						//show zeroJump image
			spriteSheet.hide();						//hide spriteSheet image(s)
			if (jumpCounter > JUMPHEIGHT) {			//condition for "limiting" player's jump
				playerState = LAND;	//set playerState to LAND
				setImagePosition();	//movement and animation function
			} else { 								//continue to "jump" higher
			y -= 7;									//by 7
			}
			
			break;
		case LAND:									//when in LAND:
			jumpCounter -= 7;						//decrements jumpCounter by 7 until it's <= 0
			movePlayer();							//move player left/right
			zeroJump.hide();						//hide zeroJump image
			zeroLand.show();						//show zeroLand image
			if (jumpCounter <= 0) {					//when jumpCounter <= 0,
				playerState = STAND;					//playerState is set to STAND
				zeroLand.hide();						//hide zeroLand image				
				spriteSheet.show();						//show spriteSheet image
			} else {								//otherwise 
				y += 7;									//increment(land) by increments of 7
			}
		}
	}

	private void setImagePosition() {	//movement/animation function
		spriteSheet.translateTo(x, y);	//move spriteSheet,
		zeroJump.translateTo(x, y);		//zeroJump,
		zeroLand.translateTo(x, y);		//zeroLand to wherever x and y are.
		spriteSheet.setFocus(walkSequence * spriteWidth, direction,	//focuses on parts of spriteSheet
				walkSequence * spriteWidth + spriteWidth, direction	//to simulate animation of running
						+ spriteHeight);
	}
	
	public void moveLeft(int stepSize) {	//function for running left
		x = x - stepSize;					//move player left by stepSize (4)
		direction = spriteHeight;			//which part of spriteSheet image(second row)

		if ((counter % cycleSteps) == 0) {
			walkSequence--;					//read spriteSheet from right to left focus
			if (walkSequence < 0)			//if reach leftmost image,
				walkSequence = 14;			//go back to rightmost image
		}
		counter++;							//increment counter by 1
		setImagePosition();					//move image(s) to x, y position
	}

	public void moveRight(int stepSize) {	//function for running right
		x = x + stepSize;					//move player right by stepSize (4)
		direction = 0;						//which part of spriteSheet image(first row)
		
		if ((counter % cycleSteps) == 0) {	
			walkSequence++;					//read spriteSheet from left to right
			if (walkSequence > 14)			//if reach rightmost image,
				walkSequence = 0;			//go back to first image
		}
		counter--;							//decrement counter by 1
		setImagePosition();					//move image(s) to x, y position
	}



	// Keyboard controls for moving the character.
	public void movePlayer() {
		if (EZInteraction.isKeyDown('a')) {	//press/hold a: move left
			moveLeft(4);
		}
		if (EZInteraction.isKeyDown('d')) { //press/hold d: move right
			moveRight(4);
		}
		
	}

}
