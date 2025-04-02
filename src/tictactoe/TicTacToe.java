package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {
	static void drawBoard(char[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print("|");
				System.out.print(board[i][j]);
			}
			System.out.print("|");
			System.out.println();
			System.out.print("-------");
			System.out.println();
		}
	}
	
	static boolean checkForSpace(char[][] board) {
		boolean hasSpace = false;
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == ' ') {
					hasSpace = true;
					break;
				}
			}
		}
		
		return hasSpace;
	}
	
	static void print(String s) {
		System.out.println(s);
	}
	
	static void print(char c) {
		System.out.println(c);
	}
	
	static void print(int i) {
		System.out.println(i);
	}
	
	static char lookForWinner(char[][] board) {
		char winner = ' ';
		
		List<List<String>> combinations = new ArrayList<>();
		// Horizontal combinations
		combinations.add(Arrays.asList("A1", "A2", "A3"));
		combinations.add(Arrays.asList("B1", "B2", "B3"));
		combinations.add(Arrays.asList("C1", "C2", "C3"));
		// Vertical combinations
		combinations.add(Arrays.asList("A1", "B1", "C1"));
		combinations.add(Arrays.asList("A2", "B2", "C2"));
		combinations.add(Arrays.asList("A3", "B3", "C3"));
		// Diagonal combinations
		combinations.add(Arrays.asList("A1", "B2", "C3"));
		combinations.add(Arrays.asList("A3", "B2", "C1"));

		for (List<String> l : combinations) {
			ArrayList<Character> chars = new ArrayList<>();
			for (String s : l) {
				chars.add(board[charToInt(s.charAt(0))][s.charAt(1) - '1']);
			}
			
			if (chars.get(0) != ' ' && chars.get(0) == chars.get(1) && chars.get(1) == chars.get(2)) {
				winner = chars.get(0);
				break;
			}
			
		}
		
		return winner;
	}
	
	static boolean isValidInput(String input) {
		boolean isValid = true;
		
		if (input.length() > 2) {
			isValid = false;
		}
		
        	ArrayList<Character> validLetters = new ArrayList<>(Arrays.asList('A', 'B', 'C'));
        	ArrayList<Integer> validNumbers = new ArrayList<>(Arrays.asList(1, 2, 3));
		
		if (!validLetters.contains(Character.toUpperCase(input.charAt(0)))) {
			isValid = false;
		}
		
		if (!validNumbers.contains(Character.getNumericValue(input.charAt(1)))) {
			isValid = false;
		}
		
		return isValid;
	}
	
	static int charToInt(char input) {
		input = Character.toUpperCase(input);
		int conversion = 0;
		
		if (input == 'B') {
			conversion = 1;
		} else if (input == 'C') {
			conversion = 2;
		}
		
		return conversion;
	}
	
	static boolean checkFreeSpace(char[][] board, String input) {
		int letter, number;
		letter = charToInt(input.charAt(0));
		number = input.charAt(1) - '0';
		
		if (board[letter][number - 1] == ' ') {
			return true;
		}
		
		return false;
	}
	
	static void restartGame(char[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = ' ';
			}
		}
	}
	
	static boolean promptForRestart(Scanner input) {
		System.out.println(Localization.PLAY_AGAIN.getLine());
		return input.next().equalsIgnoreCase("y");
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		char[][] board = {{' ', ' ', ' '},
				          {' ', ' ', ' '},
				          {' ', ' ', ' '},
				         };
		
		boolean ended = false;
		char last = ' ';
		char next = ' ';
		
		while (!ended) {
			if (last == ' ' || last == 'X') {
				next = 'O';
				last = 'O';
			} else {
				next = 'X';
				last = 'X';
			}
			
			if (!checkForSpace(board)) {
				ended = true;
				System.out.println(Localization.TIE.getLine());
				if (promptForRestart(input)) {
					restartGame(board);
				}
			}
			
			System.out.println(Localization.NEXT_TURN.getLine(next));
			String nextInput = input.next();
			
			//System.out.println(nextInput);
			
			while (!isValidInput(nextInput)) {
				System.out.println(Localization.INVALID_INPUT.getLine());
				nextInput = input.next();
			}
			
			while (!checkFreeSpace(board, nextInput)) {
				System.out.println(Localization.SPOT_OCCUPIED.getLine());
				System.out.println(Localization.NEXT_TURN.getLine(next));
				nextInput = input.next();
			}
			
			board[charToInt(nextInput.charAt(0))][nextInput.charAt(1) - '0' - 1] = next;
			drawBoard(board);
			
			char winner = lookForWinner(board);
			if (winner != ' ') {
				ended = true;
				
				System.out.println(Localization.GAME_ENDED.getLine(winner));
				if (promptForRestart(input)) {
					restartGame(board);
				}
			}
		}
		input.close();
	}
}
