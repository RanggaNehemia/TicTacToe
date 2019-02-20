package id.co.tictactoecommandpromt;
import java.util.Scanner;
import java.util.Random;

public class TicTacToeCMD {
   // Name-constants to represent the seeds and cell contents
   private final int EMPTY = 0;
   private final int CROSS = 1;
   private final int NOUGHT = 2;
 
   // Name-constants to represent the various states of the game
   private final int PLAYING = 0;
   private final int DRAW = 1;
   private final int CROSS_WON = 2;
   private final int NOUGHT_WON = 3;
   private final int OPTION = 4;
   
   // The game board and the game status
   private int ROWS = 3, COLS = 3; // number of rows and columns
   private int[][] board = new int[ROWS][COLS]; // game board in 2D array
                                                      //  containing (EMPTY, CROSS, NOUGHT)
   private int currentState;  // the current state of the game
                                    // (PLAYING, DRAW, CROSS_WON, NOUGHT_WON)
   private int currentPlayer = CROSS; // the current player (CROSS or NOUGHT)
   private int currntRow, currentCol; // current seed's row and column
 
   private static Scanner in = new Scanner(System.in); // the input Scanner
   
   public TicTacToeCMD() {
   }
   
   public TicTacToeCMD(int rows, int cols) {
	   ROWS = rows;
	   COLS = cols;
	   board = new int[ROWS][COLS];
   }
 
   /** The entry main method (the program starts here) */
   public void play() {
      // Initialize the game-board and current status
      initGame();
      // Check for Option state
      if(currentState == OPTION) {
    	  setOption();
      }
      // Play the game once
      do {
         playerMove(currentPlayer); // update currentRow and currentCol
         updateGame(currentPlayer, currntRow, currentCol); // update currentState
         printBoard();
         // Print message if game-over
         if (currentState == CROSS_WON) {
            System.out.println("'X' won!");
         } else if (currentState == NOUGHT_WON) {
            System.out.println("'O' won!");
         } else if (currentState == DRAW) {
            System.out.println("It's a Draw!");
         }
         // Switch player
         currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
      } while (currentState == PLAYING); // repeat if not game-over
   }
 
   /** Initialize the game-board contents and the current states */
   private void initGame() {
	  System.out.print("Welcome to TicTacToe. Type Option to Change Options, or Play to start playing");
	  String input = in.next();
	  if(input.toLowerCase().equals("option")||input.toLowerCase().equals("options")) {
		  currentState = OPTION; // option mode
	  }else if(input.toLowerCase().equals("play")) {
		  for (int row = 0; row < ROWS; ++row) {
		         for (int col = 0; col < COLS; ++col) {
		            board[row][col] = EMPTY;  // all cells empty
		         }
		      }
		      currentState = PLAYING; // ready to play
	  }else {
		  System.out.println("Sorry, I dont know that command, please try again");
		  initGame();
	  }
   }
   
   /** Set the option before playing **/
   private void setOption() {
	   System.out.print("What size is the board? Input only 1 number (3 = 3x3, 5 = 5x5)");
	   ROWS = in.nextInt();
	   COLS = ROWS;
	   board = new int[ROWS][COLS];
	   System.out.println("Board size set to "+ROWS+"X"+COLS);
	   System.out.println("");
	   System.out.print("Who start first? (Cross or Nought or Random)");
	   String first = in.next();
	   if(first.toLowerCase().equals("cross")){
		   currentPlayer = CROSS;
		   System.out.println("Cross start first");
	   }else if(first.toLowerCase().equals("nought")){
		   currentPlayer = NOUGHT;
		   System.out.println("Nought start first");
	   }else if(first.toLowerCase().equals("random")){
		   Random rand = new Random();
		   currentPlayer = rand.nextInt(2)+1;
		   System.out.println("First player picked randomly");
	   }else {
		   Random rand = new Random();
		   currentPlayer = rand.nextInt(2)+1;
		   System.out.println("I cannot determine that, current player will be set to random.");
	   }
	   System.out.println("");
	   System.out.println("Option setup complete. Game will now commence.");
	   System.out.println("");
	   
	   currentState = PLAYING;
   }
 
   /** Player with the "theSeed" makes one move, with input validation.
       Update global variables "currentRow" and "currentCol". */
   private void playerMove(int theSeed) {
      boolean validInput = false;  // for input validation
      do {
         if (theSeed == CROSS) {
            System.out.print("Player 'X', enter your move (row[1-"+ROWS+"] column[1-"+COLS+"]): ");
         } else {
            System.out.print("Player 'O', enter your move (row[1-"+ROWS+"] column[1-"+COLS+"]): ");
         }
         int row = in.nextInt() - 1;  // array index starts at 0 instead of 1
         int col = in.nextInt() - 1;
         if (row >= 0 && row < ROWS && col >= 0 && col < COLS && board[row][col] == EMPTY) {
            currntRow = row;
            currentCol = col;
            board[currntRow][currentCol] = theSeed;  // update game-board content
            validInput = true;  // input okay, exit loop
         } else {
            System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                  + ") is not valid. Try again...");
         }
      } while (!validInput);  // repeat until input is valid
   }
   
   private boolean playerMove(int theSeed, int col, int row) {
	    if (row >= 0 && row < ROWS && col >= 0 && col < COLS && board[row][col] == EMPTY) {
	            currntRow = row;
	            currentCol = col;
	            board[currntRow][currentCol] = theSeed;  // update game-board content
	            return true;  // input okay, exit loop
	    } else {
	        return false;
	    }
   }
 
   /** Update the "currentState" after the player with "theSeed" has placed on
       (currentRow, currentCol). */
   private void updateGame(int theSeed, int currentRow, int currentCol) {
      if (hasWon(theSeed, currentRow, currentCol)) {  // check if winning move
         currentState = (theSeed == CROSS) ? CROSS_WON : NOUGHT_WON;
      } else if (isDraw()) {  // check for draw
         currentState = DRAW;
      }
      // Otherwise, no change to currentState (still PLAYING).
   }
 
   /** Return true if it is a draw (no more empty cell) */
   // TODO: Shall declare draw if no player can "possibly" win
   private boolean isDraw() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (board[row][col] == EMPTY) {
               return false;  // an empty cell found, not draw, exit
            }
         }
      }
      return true;  // no empty cell, it's a draw
   }
 
   /** Return true if the player with "theSeed" has won after placing at
       (currentRow, currentCol) */
   private boolean hasWon(int theSeed, int currentRow, int currentCol) {
	  boolean vertical = true;
	  boolean horizontal = true;
	  boolean diagonalleft = true;
	  boolean diagonalright = true;
	  
	  for(int x = 0;x<COLS;x++) {
		  if(board[currentRow][x]!= theSeed) { //Check for vertical win
			  vertical=false;
		  }
		  
		  if(board[x][currentCol]!= theSeed) { //Check for horizontal win
			  horizontal=false;
		  }
		  
		  if(board[x][x]!= theSeed) { //Check for diagonal win
			  diagonalleft=false;
		  }
		  
		  if(board[x][(COLS-1)-x]!= theSeed) { //Check for another diagonal win
			  diagonalright=false;
		  }
	  }
	  
      return (vertical||horizontal||diagonalleft||diagonalright);
   }
 
   /** Print the game board */
   private void printBoard() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            printCell(board[row][col]); // print each of the cells
            if (col != COLS - 1) {
               System.out.print("|");   // print vertical partition
            }
         }
         System.out.println();
         if (row != ROWS - 1) {
        	for(int x=0;x<COLS-1;x++) {
        		System.out.print("----"); // print horizontal partition
        	}
        	System.out.println("---"); // print last horizontal partition
         }
      }
      System.out.println();
   }
 
   /** Print a cell with the specified "content" */
   private void printCell(int content) {
      switch (content) {
         case EMPTY:  System.out.print("   "); break;
         case NOUGHT: System.out.print(" O "); break;
         case CROSS:  System.out.print(" X "); break;
      }
   }
   
   protected boolean servletMove(int theSeed, int row, int col) {
	   return playerMove(theSeed, col, row);
   }
   
//   public String getStatus() {
//	   
//   }
}