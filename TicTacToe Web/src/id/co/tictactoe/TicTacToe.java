package id.co.tictactoe;

import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacToe {
	// Name-constants to represent the seeds and cell contents
	public final int EMPTY = 0;
	public final int CROSS = 1;
	public final int NOUGHT = 2;

	// Name-constants to represent the various states of the game
	public final int PLAYING = 0;
	public final int DRAW = 1;
	public final int CROSS_WON = 2;
	public final int NOUGHT_WON = 3;
	public final int OPTION = 4;

	// Check if playing with bots, which then bot will play as Cross
	private boolean SINGLEPLAYER = true;

	// The game board and the game status
	private int ROWS, COLS; // number of rows and columns
	private int[][] board; // game board in 2D array
							// containing (EMPTY, CROSS, NOUGHT)
	private int currentState; // the current state of the game
								// (PLAYING, DRAW, CROSS_WON, NOUGHT_WON)
	private int currentPlayer = CROSS; // the current player (CROSS or NOUGHT)
	private int currentRow, currentCol; // current seed's row and column


	public TicTacToe() {
	}

	public TicTacToe(int rows, int cols) {
		ROWS = rows;
		COLS = cols;
		board = new int[ROWS][COLS];
	}

	/** Initialize the game-board contents and the current states */
	public void initGame(int size, boolean single) {
		ROWS = size;
		COLS = size;
		board = new int[ROWS][COLS];

		SINGLEPLAYER = single;

		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				board[row][col] = EMPTY; // all cells empty
			}
		}
		currentState = PLAYING; // ready to play

		if (SINGLEPLAYER) {
			Random rand = new Random();
			currentPlayer = rand.nextInt(2) + 1;
			if (currentPlayer == CROSS && SINGLEPLAYER && currentState == PLAYING) {
				compMove();
			}
		}
	}

	private boolean playerMove(int theSeed, int col, int row) {
		if (row >= 0 && row < ROWS && col >= 0 && col < COLS && board[row][col] == EMPTY) {
			currentRow = row;
			currentCol = col;
			board[currentRow][currentCol] = theSeed; // update game-board
														// content

			if (hasWon(theSeed, currentRow, currentCol)) { // check if winning
															// move
				currentState = (theSeed == CROSS) ? CROSS_WON : NOUGHT_WON;
			} else if (isDraw()) { // check for draw
				currentState = DRAW;
			} else {
				// Switch player
				currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
			}
			return true; // input okay, exit loop
		} else {
			return false;
		}
	}

	/** Return true if it is a draw (no more empty cell) */
	private boolean isDraw() {
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				if (board[row][col] == EMPTY) {
					return false; // an empty cell found, not draw, exit
				}
			}
		}
		return true; // no empty cell, it's a draw
	}

	/**
	 * Return true if the player with "theSeed" has won after placing at
	 * (currentRow, currentCol)
	 */
	private boolean hasWon(int theSeed, int currentRow, int currentCol) {
		boolean vertical = true;
		boolean horizontal = true;
		boolean diagonalleft = true;
		boolean diagonalright = true;

		for (int x = 0; x < COLS; x++) {
			if (board[currentRow][x] != theSeed) { // Check for vertical win
				vertical = false;
			}

			if (board[x][currentCol] != theSeed) { // Check for horizontal win
				horizontal = false;
			}

			if (board[x][x] != theSeed) { // Check for diagonal win
				diagonalleft = false;
			}

			if (board[x][(COLS - 1) - x] != theSeed) { // Check for another
														// diagonal win
				diagonalright = false;
			}
		}

		return (vertical || horizontal || diagonalleft || diagonalright);
	}

	private void compMove() {
		List<Integer> indexes = new ArrayList<Integer>();
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (board[row][col] == EMPTY) {
					indexes.add((row * COLS) + col);
				}
			}
		}
		Random rand = new Random();
		int index = indexes.get(rand.nextInt(indexes.size()));
		currentRow = (int) Math.floor(index / COLS);
		currentCol = index % ROWS;

		board[currentRow][currentCol] = CROSS;

		if (hasWon(currentPlayer, currentRow, currentCol)) { // check if winning
																// move
			currentState = (currentPlayer == CROSS) ? CROSS_WON : NOUGHT_WON;
		} else if (isDraw()) { // check for draw
			currentState = DRAW;
		} else {
			// Switch player
			currentPlayer = NOUGHT;
		}
	}

	public void servletMove(int theSeed, int row, int col) {
		playerMove(theSeed, col, row);
		if (currentPlayer == CROSS && SINGLEPLAYER && currentState == PLAYING) {
			compMove();
		}
	}

	public int[][] getBoard() {
		return board;
	}

	public int getSize() {
		return ROWS;
	}

	public int getCurrentState() {
		return currentState;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}
}