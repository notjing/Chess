import java.util.*;
import java.math.*;
import java.io.*;

class Piece{ //makes a class piece with the type of piece, the colour of its piece and location on the board
	char type;
	char colour;
	int r;
	int c;
	
	public Piece(char T, char C) { //constructor
		type = T;
		colour = C;
	}
}

class Square{ //makes a class square which is just a pair of coordinate
	int r;
	int c;
	
	public Square(int R, int C) { //constructor 
		r = R;
		c = C;
	}
}

public class Chess {
	static Scanner input = new Scanner(System.in);
	static boolean torpedo = false;
	static boolean knightmare = false;
	static int mode;
	public static int[] coords2 = new int[8];
	public static Piece[][] board = new Piece[8][8]; //the board
	public static ArrayList<Piece> white = new ArrayList<Piece>(); //all the white pieces
	public static ArrayList<Piece> black = new ArrayList<Piece>(); //all the black pieces
	//all the colouring stuff
	public static final String reset = "\u001B[0m";
	public static final String backBlack = "\u001B[40m";
	public static final String backWhite = "\u001B[47m";
	public static final String backGreen = "\u001B[42m";
	public static final String red = "\u001B[31m";
	public static final String blue = "\u001B[34m";
	
	public static void type(String a) throws InterruptedException {
		for(int i = 0; i < a.length(); i++) {
			System.out.print(a.charAt(i));
			Thread.sleep(10);
		}
		System.out.println("");
		System.out.println("");
		return;
	}
	
	public static void welcome() throws InterruptedException {
		//initilizes variables
		boolean repeatE4 = false, repeatNF3 = false; int r1, r2, c1, c2;
		//welcome
		System.out.println("Welcome to the tutorial!\n");
		System.out.println("Here is the chessboard:");
		printBoard();
		System.out.println("");
		System.out.println(
				//introduces the game
				"\nThere are 6 different pieces in chess, the pawn (P); knight (N); bishop (B); rook (R); queen (Q); king (K)."
						+ "\nSee this document to study how the pieces move and the general rules of chess:\n[removed]"
						+ "\n\nTo win the game, checkmate your opponent, which is defined as the opposing side having no moves to get out of check."
						+ "\n\nTo input a move to the board, type the coordinates of the square the piece is currently on.\nThen type the coordinates of the square it will be moving to. For example: e1 e2 to move the king up one square."
						+ "\n\nEvery time you input a move, a new board will pop up with the latest position."
						+ "\nAnd don't worry, the game will tell you if you make a move that isn't legal.");
		Outer:
		do {
			//start of the tutorial
			System.out.println("");
			type("To start, try, as the red side, to move your pawn to e4");
			String from = input.next();
			String to = input.next();
			
		
			
			if (from.length() == 2 && to.length() == 2 && from.equals("e2") && to.equals("e4")) {
				c1 = (from.charAt(0) - 'a');	
				r1 = 7-(from.charAt(1) - '1');		
				c2 = (to.charAt(0) - 'a');		
				r2 = 7-(to.charAt(1) - '1');
				updateBoard(r1,c1,r2,c2);
				do {

					printBoard();
					System.out.println("");
					System.out.println("\nTry moving the red knight to f3.\n");

					String from1 = input.next();
					String to1 = input.next();
					
					
					if (from1.equals("g1") && to1.equals("f3")) {
						c1 = (from1.charAt(0) - 'a');
						r1 = 7 - (from1.charAt(1) - '1');
						c2 = (to1.charAt(0) - 'a');
						r2 = 7 - (to1.charAt(1) - '1');
						updateBoard(r1,c1,r2,c2);
						printBoard();
						System.out.println("\nGood job, you are ready to play some chess!\n");
						break Outer;
					} else {
						System.out.println(
								"\nThat's not quite right... Here's a hint, the knight is on g1 and is moving to f3.\n");
						repeatNF3 = true;
					}
					
				} while (repeatNF3);

			} else {
				System.out.println("\nThat's not quite right... Here's a hint, the pawn is on e2 and is moving to e4.\n");
				repeatE4 = true;
			}
		} while (repeatE4);

	}
	
	
	public static void printBoard() {
		for (int i = 0; i < 8; i++) { // goes through every row
			System.out.println("  --- --- --- --- --- --- --- --- ");
			if (i % 2 == 0)
				System.out.print(" ");
			for (int j = 0; j < 4; j++) {

				if (i % 2 == 0) { //if the row is even
					
					if (j != 0)
						System.out.print(backGreen + " " + reset); //makes the background square green
					
					System.out.print("|");
					System.out.print(" ");
					
					//prints the piece
					if (board[i][2 * j].colour == 'B')
						System.out.print(board[i][2 * j].type); //colors the pieces
					else
						System.out.print(red + board[i][2 * j].type + reset); //also colours the pieces
					
					//prints the second final bar
					System.out.print(" ");
					System.out.print("|");
					System.out.print(backGreen + " " + reset);

					//prints the next piece
					if (board[i][2 * j + 1].colour == 'B')
						System.out.print(backGreen + board[i][2 * j + 1].type + reset);
					else
						System.out.print(backGreen + red + board[i][2 * j + 1].type + reset);

					if (j == 3)
						System.out.print(backGreen + " " + reset); //adjusts the final square
					
				} else {
					System.out.print(" ");
					System.out.print("|");
					System.out.print(backGreen + " " + reset);

					if (board[i][2 * j].colour == 'W')
						System.out.print(backGreen + red + board[i][2 * j].type + reset);
					else
						System.out.print(backGreen + board[i][2 * j].type + reset);

					System.out.print(backGreen + " " + reset);
					System.out.print("|");
					System.out.print(" ");

					if (board[i][2 * j + 1].colour == 'W')
						System.out.print(red + board[i][2 * j + 1].type + reset);
					else
						System.out.print(board[i][2 * j + 1].type);

				}
				

			}
			//final bar depends on the parity of the row due to the colour discrepancies 
			if (i % 2 == 1)
				System.out.print(" | ");
			else
				System.out.print("| ");
			
			//prints the numerical number
			System.out.println(coords2[7-i]);
		}
		//prints the alphabetical coordinates
		System.out.println("  --- --- --- --- --- --- --- --- ");
		System.out.println("   a   b   c   d   e   f   g   h  ");

	}//end of printBoard method
	public static void initilizeBoard() {
		//puts the pieces on their starting positions
		if(!knightmare) {//checks if user wants to play knightmare mode
			board[0][0] = new Piece('R','B'); board[0][0].r = 0; board[0][0].c = 0; black.add(board[0][0]); 
			board[0][1] = new Piece('N','B'); board[0][1].r = 0; board[0][1].c = 1; black.add(board[0][1]);
			board[0][2] = new Piece('B','B'); board[0][2].r = 0; board[0][2].c = 2; black.add(board[0][2]);
			board[0][3] = new Piece('Q','B'); board[0][3].r = 0; board[0][3].c = 3; black.add(board[0][3]);
			board[0][4] = new Piece('K','B'); board[0][4].r = 0; board[0][4].c = 4; black.add(board[0][4]);
			board[0][5] = new Piece('B','B'); board[0][5].r = 0; board[0][5].c = 5; black.add(board[0][5]);
			board[0][6] = new Piece('N','B'); board[0][6].r = 0; board[0][6].c = 6; black.add(board[0][6]);
			board[0][7] = new Piece('R','B'); board[0][7].r = 0; board[0][7].c = 7; black.add(board[0][7]);
			board[1][0] = new Piece('P','B'); board[1][0].r = 1; board[1][0].c = 0; black.add(board[1][0]);
			board[1][1] = new Piece('P','B'); board[1][1].r = 1; board[1][1].c = 1; black.add(board[1][1]);
			board[1][2] = new Piece('P','B'); board[1][2].r = 1; board[1][2].c = 2; black.add(board[1][2]);
			board[1][3] = new Piece('P','B'); board[1][3].r = 1; board[1][3].c = 3; black.add(board[1][3]);
			board[1][4] = new Piece('P','B'); board[1][4].r = 1; board[1][4].c = 4; black.add(board[1][4]);
			board[1][5] = new Piece('P','B'); board[1][5].r = 1; board[1][5].c = 5; black.add(board[1][5]);
			board[1][6] = new Piece('P','B'); board[1][6].r = 1; board[1][6].c = 6; black.add(board[1][6]);
			board[1][7] = new Piece('P','B'); board[1][7].r = 1; board[1][7].c = 7; black.add(board[1][7]);
			
			for(int i = 2; i <= 6; i++) {
				for(int j = 0; j < 8; j++) {
					board[i][j] = new Piece(' ',' ');
				}
			}
			
			board[7][0] = new Piece('R','W'); board[7][0].r = 7; board[7][0].c = 0; white.add(board[7][0]);
			board[7][1] = new Piece('N','W'); board[7][1].r = 7; board[7][1].c = 1; white.add(board[7][1]);
			board[7][2] = new Piece('B','W'); board[7][2].r = 7; board[7][2].c = 2; white.add(board[7][2]);
			board[7][3] = new Piece('Q','W'); board[7][3].r = 7; board[7][3].c = 3; white.add(board[7][3]);
			board[7][4] = new Piece('K','W'); board[7][4].r = 7; board[7][4].c = 4; white.add(board[7][4]);
			board[7][5] = new Piece('B','W'); board[7][5].r = 7; board[7][5].c = 5; white.add(board[7][5]);
			board[7][6] = new Piece('N','W'); board[7][6].r = 7; board[7][6].c = 6; white.add(board[7][6]);
			board[7][7] = new Piece('R','W'); board[7][7].r = 7; board[7][7].c = 7; white.add(board[7][7]);
			board[6][0] = new Piece('P','W'); board[6][0].r = 6; board[6][0].c = 0; white.add(board[6][0]);
			board[6][1] = new Piece('P','W'); board[6][1].r = 6; board[6][1].c = 1; white.add(board[6][1]);
			board[6][2] = new Piece('P','W'); board[6][2].r = 6; board[6][2].c = 2; white.add(board[6][2]);
			board[6][3] = new Piece('P','W'); board[6][3].r = 6; board[6][3].c = 3; white.add(board[6][3]);
			board[6][4] = new Piece('P','W'); board[6][4].r = 6; board[6][4].c = 4; white.add(board[6][4]);
			board[6][5] = new Piece('P','W'); board[6][5].r = 6; board[6][5].c = 5; white.add(board[6][5]);
			board[6][6] = new Piece('P','W'); board[6][6].r = 6; board[6][6].c = 6; white.add(board[6][6]);
			board[6][7] = new Piece('P','W'); board[6][7].r = 6; board[6][7].c = 7; white.add(board[6][7]);		
		}
		else {
			board[0][0] = new Piece('N','B'); board[0][0].r = 0; board[0][0].c = 0; black.add(board[0][0]); 
			board[0][1] = new Piece('N','B'); board[0][1].r = 0; board[0][1].c = 1; black.add(board[0][1]);
			board[0][2] = new Piece('N','B'); board[0][2].r = 0; board[0][2].c = 2; black.add(board[0][2]);
			board[0][3] = new Piece('N','B'); board[0][3].r = 0; board[0][3].c = 3; black.add(board[0][3]);
			board[0][4] = new Piece('K','B'); board[0][4].r = 0; board[0][4].c = 4; black.add(board[0][4]);
			board[0][5] = new Piece('N','B'); board[0][5].r = 0; board[0][5].c = 5; black.add(board[0][5]);
			board[0][6] = new Piece('N','B'); board[0][6].r = 0; board[0][6].c = 6; black.add(board[0][6]);
			board[0][7] = new Piece('N','B'); board[0][7].r = 0; board[0][7].c = 7; black.add(board[0][7]);
			board[1][0] = new Piece('N','B'); board[1][0].r = 1; board[1][0].c = 0; black.add(board[1][0]);
			board[1][1] = new Piece('N','B'); board[1][1].r = 1; board[1][1].c = 1; black.add(board[1][1]);
			board[1][2] = new Piece('N','B'); board[1][2].r = 1; board[1][2].c = 2; black.add(board[1][2]);
			board[1][3] = new Piece('N','B'); board[1][3].r = 1; board[1][3].c = 3; black.add(board[1][3]);
			board[1][4] = new Piece('N','B'); board[1][4].r = 1; board[1][4].c = 4; black.add(board[1][4]);
			board[1][5] = new Piece('N','B'); board[1][5].r = 1; board[1][5].c = 5; black.add(board[1][5]);
			board[1][6] = new Piece('N','B'); board[1][6].r = 1; board[1][6].c = 6; black.add(board[1][6]);
			board[1][7] = new Piece('N','B'); board[1][7].r = 1; board[1][7].c = 7; black.add(board[1][7]);
			
			for(int i = 2; i <= 6; i++) {
				for(int j = 0; j < 8; j++) {
					board[i][j] = new Piece(' ',' ');
				}
			}
			
			board[7][0] = new Piece('N','W'); board[7][0].r = 7; board[7][0].c = 0; white.add(board[7][0]);
			board[7][1] = new Piece('N','W'); board[7][1].r = 7; board[7][1].c = 1; white.add(board[7][1]);
			board[7][2] = new Piece('N','W'); board[7][2].r = 7; board[7][2].c = 2; white.add(board[7][2]);
			board[7][3] = new Piece('N','W'); board[7][3].r = 7; board[7][3].c = 3; white.add(board[7][3]);
			board[7][4] = new Piece('K','W'); board[7][4].r = 7; board[7][4].c = 4; white.add(board[7][4]);
			board[7][5] = new Piece('N','W'); board[7][5].r = 7; board[7][5].c = 5; white.add(board[7][5]);
			board[7][6] = new Piece('N','W'); board[7][6].r = 7; board[7][6].c = 6; white.add(board[7][6]);
			board[7][7] = new Piece('N','W'); board[7][7].r = 7; board[7][7].c = 7; white.add(board[7][7]);
			board[6][0] = new Piece('N','W'); board[6][0].r = 6; board[6][0].c = 0; white.add(board[6][0]);
			board[6][1] = new Piece('N','W'); board[6][1].r = 6; board[6][1].c = 1; white.add(board[6][1]);
			board[6][2] = new Piece('N','W'); board[6][2].r = 6; board[6][2].c = 2; white.add(board[6][2]);
			board[6][3] = new Piece('N','W'); board[6][3].r = 6; board[6][3].c = 3; white.add(board[6][3]);
			board[6][4] = new Piece('N','W'); board[6][4].r = 6; board[6][4].c = 4; white.add(board[6][4]);
			board[6][5] = new Piece('N','W'); board[6][5].r = 6; board[6][5].c = 5; white.add(board[6][5]);
			board[6][6] = new Piece('N','W'); board[6][6].r = 6; board[6][6].c = 6; white.add(board[6][6]);
			board[6][7] = new Piece('N','W'); board[6][7].r = 6; board[6][7].c = 7; white.add(board[6][7]);		
		}
		
	
		//puts some coords into the coords array
		coords2[0] = 1;
		coords2[1] = 2;
		coords2[2] = 3;
		coords2[3] = 4;
		coords2[4] = 5;
		coords2[5] = 6;
		coords2[6] = 7;
		coords2[7] = 8;
	} // end of initilizeBoard method
	public static void updateBoard(int r1, int c1, int r2, int c2) { // updating the square [r1][c1] to [r2][c2]
		//initilize p1 which represnts the starting piece when updating the board
		Piece p1 = board[r1][c1];
		//splits up based on its color
		if(p1.colour == 'W') {
			for(int i = 0; i < white.size(); i++) { //loops through the pieces to see if there is a piece on r1,c1
				if(white.get(i).type == p1.type && white.get(i).r == r1 && white.get(i).c == c1) {
					//if its found then update the piece and put it into the list of white pieces
					white.remove(i);
					Piece p = new Piece(p1.type,p1.colour); p.r = r2; p.c = c2;
					white.add(p);
				}
			}
			
			if(board[r2][c2].type != ' ') { //if it takes a piece then remove that piece from the black piece list
				for(int i = 0; i < black.size(); i++) {
					if(black.get(i).r == r2 && black.get(i).c == c2) {
						black.remove(i);
						break;
					}
				}
			}
		}
		else if(p1.colour == 'B') { //same as above
			for(int i = 0; i < black.size(); i++) {
				if(black.get(i).type == p1.type && black.get (i).r == r1 && black.get(i).c == c1) {
					black.remove(i);
					Piece p = new Piece(p1.type,p1.colour); p.r = r2; p.c = c2;
					black.add(p);
				}
			}
			
			if(board[r2][c2].type != ' ') {
				for(int i = 0; i < white.size(); i++) {
					if(white.get(i).r == r2 && white.get(i).c == c2) {
						white.remove(i);
						break;
					}
				}
			}
			
		}
		//updates the board and moves the pice
		board[r2][c2] = new Piece(p1.type,p1.colour); board[r2][c2].r = r2; board[r2][c2].c = c2;
		board[r1][c1] = new Piece(' ',' ');
	}//end of updateBoard method

	public static boolean onBoard(int r, int c) {
		//checks if the coords is on the board
		return r < 8 && r >= 0 && c < 8 && c >= 0;
	}//end of onBoard method
	
	public static ArrayList<Square> attack(Square s, Piece[][]board) {
		//returns an arraylist of squares of which the piece attacks
		
		//gets the row, column, type, and column, also initilizes the arraylist
		int r = s.r; int c = s.c;
		char t = board[r][c].type;
		char colour = board[r][c].colour;
		ArrayList<Square> attacking = new ArrayList<Square>();
		if(board[r][c].type == ' ') return attacking;
		
		
		//splits it by type of piece
		if(t == 'P') {
			if(colour == 'B') { //splits by colour
				if(r == 1 || torpedo) { //can move two squares
					if(onBoard(r+2,c) && board[r+2][c].colour == ' ' && board[r+1][c].colour == ' ') attacking.add(new Square(r+2,c));
				}
				if(onBoard(r+1,c) && board[r+1][c].colour == ' ') attacking.add(new Square(r+1,c));
				//captures go diagonal
				if(onBoard(r+1,c-1) && board[r+1][c-1].colour == 'W') attacking.add(new Square(r+1,c-1));
				if(onBoard(r+1,c+1) && board[r+1][c+1].colour == 'W') attacking.add(new Square(r+1,c+1));
			}
			else { //same as above
				if(r == 6 || torpedo) {
					if(onBoard(r-2,c) && board[r-2][c].colour == ' ') attacking.add(new Square(r-2,c));
				}
				if(onBoard(r-1,c) && board[r-1][c].colour == ' ') attacking.add(new Square(r-1,c));
				if(onBoard(r-1,c-1) && board[r-1][c-1].colour == 'B') attacking.add(new Square(r-1,c-1));
				if(onBoard(r-1,c+1) && board[r-1][c+1].colour == 'B') attacking.add(new Square(r-1,c+1));
			}
		}
		else if(t == 'N') {
			//knights move by going 2 in one direction and 1 in the other
			if(onBoard(r+2,c-1) && board[r+2][c-1].colour != colour) attacking.add(new Square(r+2,c-1));
			if(onBoard(r+2,c+1) && board[r+2][c+1].colour != colour) attacking.add(new Square(r+2,c+1));
			if(onBoard(r-2,c-1) && board[r-2][c-1].colour != colour) attacking.add(new Square(r-2,c-1));
			if(onBoard(r-2,c+1) && board[r-2][c+1].colour != colour) attacking.add(new Square(r-2,c+1));
			if(onBoard(r+1,c+2) && board[r+1][c+2].colour != colour) attacking.add(new Square(r+1,c+2));
			if(onBoard(r-1,c-2) && board[r-1][c-2].colour != colour) attacking.add(new Square(r-1,c-2));
			if(onBoard(r+1,c-2) && board[r+1][c-2].colour != colour) attacking.add(new Square(r+1,c-2));
			if(onBoard(r-1,c+2) && board[r-1][c+2].colour != colour) attacking.add(new Square(r-1,c+2));
		}
		else if(t == 'B') {
			//loops through all 4 diagonals to see if they are attacked
			for(int i = 1; i < 8; i++) {
				if(onBoard(r+i,c+i) && board[r+i][c+i].colour != colour) attacking.add(new Square(r+i,c+i));
				else break;
				if(board[r+i][c+i].type != ' ')  break; //stops if it hits apiece
				
			}
			for(int i = 1; i < 8; i++) {
				if(onBoard(r+i,c-i) && board[r+i][c-i].colour != colour) attacking.add(new Square(r+i,c-i));
				else break;
				if(board[r+i][c-i].type != ' ')  break;
			}
			for(int i = 1; i < 8; i++) {
				if(onBoard(r-i,c+i) && board[r-i][c+i].colour != colour) attacking.add(new Square(r-i,c+i));
				else break;
				if(board[r-i][c+i].type != ' ')  break;
			}
			for(int i = 1; i < 8; i++) {
				if(onBoard(r-i,c-i) && board[r-i][c-i].colour != colour) attacking.add(new Square(r-i,c-i));
				else break;
				if(board[r-i][c-i].type != ' ')  break;
			}
		}
		else if(t == 'R') {
			//checks each row/column, same logic as the bishop
			for(int i = 1; i < 8; i++) {
				if(onBoard(r+i,c) && board[r+i][c].colour != colour) attacking.add(new Square(r+i,c));
				else break;
				if(board[r+i][c].type != ' ')  break;
			}
			for(int i = 1; i < 8; i++) {
				if(onBoard(r-i,c) && board[r-i][c].colour != colour) attacking.add(new Square(r-i,c));
				else break;
				if(board[r-i][c].type != ' ')  break;
			}
			for(int i = 1; i < 8; i++) {
				if(onBoard(r,c+i) && board[r][c+i].colour != colour) attacking.add(new Square(r,c+i));
				else break;
				if(board[r][c+i].type != ' ')  break;
			}
			for(int i = 1; i < 8; i++) {
				if(onBoard(r,c-i) && board[r][c-i].colour != colour) attacking.add(new Square(r,c-i));
				else break;
				if(board[r][c-i].type != ' ')  break;
			}
		}
		else if(t == 'Q') {
			//I just copy pasted both the rook and bishop code here because its both.
			for(int i = 1; i < 8; i++) {
				if(onBoard(r+i,c+i) && board[r+i][c+i].colour != colour) attacking.add(new Square(r+i,c+i));
				else break;
				if(board[r+i][c+i].type != ' ')  break;
				
			}
			for(int i = 1; i < 8; i++) {
				if(onBoard(r+i,c-i) && board[r+i][c-i].colour != colour) attacking.add(new Square(r+i,c-i));
				else break;
				if(board[r+i][c-i].type != ' ')  break;
			}
			for(int i = 1; i < 8; i++) {
				if(onBoard(r-i,c+i) && board[r-i][c+i].colour != colour) attacking.add(new Square(r-i,c+i));
				else break;
				if(board[r-i][c+i].type != ' ')  break;
			}
			for(int i = 1; i < 8; i++) {
				if(onBoard(r-i,c-i) && board[r-i][c-i].colour != colour) attacking.add(new Square(r-i,c-i));
				else break;
				if(board[r-i][c-i].type != ' ')  break;
			}
			for(int i = 1; i < 8; i++) {
				if(onBoard(r+i,c) && board[r+i][c].colour != colour) {
					attacking.add(new Square(r+i,c));
				}
				else break;
				if(board[r+i][c].type != ' ')  break;
			}
			for(int i = 1; i < 8; i++) {
				if(onBoard(r-i,c) && board[r-i][c].colour != colour) {
					attacking.add(new Square(r-i,c));
				}
				else break;
				if(board[r-i][c].type != ' ')  break;
			}
			for(int i = 1; i < 8; i++) {
				if(onBoard(r,c+i) && board[r][c+i].colour != colour) {
					attacking.add(new Square(r,c+i));
				}
				else break;
				if(board[r][c+i].type != ' ')  break;
			}
			for(int i = 1; i < 8; i++) {
				if(onBoard(r,c-i) && board[r][c-i].colour != colour) {
					attacking.add(new Square(r,c-i));
				}
				else break;
				if(board[r][c-i].type != ' ')  break;
			}
		}
		else if(t == 'K') {
			//check every adjacent square
			if(onBoard(r+1,c+1) && board[r+1][c+1].colour != colour) attacking.add(new Square(r+1,c+1));
			if(onBoard(r+1,c) && board[r+1][c].colour != colour) attacking.add(new Square(r+1,c));
			if(onBoard(r+1,c-1) && board[r+1][c-1].colour != colour) attacking.add(new Square(r+1,c-1));
			if(onBoard(r,c+1) && board[r][c+1].colour != colour) attacking.add(new Square(r,c+1));
			if(onBoard(r,c-1) && board[r][c-1].colour != colour) attacking.add(new Square(r,c-1));
			if(onBoard(r-1,c+1) && board[r-1][c+1].colour != colour) attacking.add(new Square(r-1,c+1));
			if(onBoard(r-1,c) && board[r-1][c].colour != colour) attacking.add(new Square(r-1,c));
			if(onBoard(r-1,c-1) && board[r-1][c-1].colour != colour) attacking.add(new Square(r-1,c-1));
		}
		
		
		return attacking;		
	}//end of attack method
		
	public static boolean isValid(int r1, int c1, int r2, int c2) { //checks if the move is a valid move
		if(!onBoard(r1,c1) || !onBoard(r2,c2)) return false;
		if(isCheck(board[r1][c1].colour,board) && !doesThisMoveStopCheck(r1,c1,r2,c2)) return false;
		ArrayList<Square> attacking = attack(new Square(r1,c1),board);
		boolean flag = false;
		for(int i = 0; i < attacking.size(); i++) {
			if(attacking.get(i).r == r2 && attacking.get(i).c == c2) {
				flag = true;
				break;
			}
		}
		if(!flag) return false;
		
		Piece p1 = board[r1][c1];
		
		Piece[][] newBoard = new Piece[8][8];
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				newBoard[i][j] = new Piece(board[i][j].type, board[i][j].colour);
				newBoard[i][j].r = i; newBoard[i][j].c = j;
			}
		}
		
		newBoard[r2][c2] = new Piece(p1.type,p1.colour); newBoard[r2][c2].r = r2; newBoard[r2][c2].c = c2;
		newBoard[r1][c1] = new Piece(' ',' ');
		
		if(newBoard[r2][c2].colour == 'B') { //splits it based on the colour
			for(int i = 0; i < black.size(); i++) { //goes through the pieces
				Piece p = black.get(i);
				if(black.get(i).r == r1 && black.get(i).c == c1) { //checks if that piece is the one that was being moved
					//change the corresponding piece in the list of pieces and move it
					black.add(new Piece(black.get(i).type,'B'));
					black.get(black.size()-1).r = r2;
					black.get(black.size()-1).c = c2;
					black.remove(i);
					
					//if you are still in check then the move does not stop check
					if(isCheck(newBoard[r2][c2].colour,newBoard)) {
						//change it back to normal
						black.remove(black.size()-1);
						black.add(new Piece(p.type,'B'));
						black.get(black.size()-1).r = r1;
						black.get(black.size()-1).c = c1;
						return false;
					}
					//change the piece back to normal 
					black.remove(black.size()-1);
					black.add(new Piece(p.type,'B'));
					black.get(black.size()-1).r = r1;
					black.get(black.size()-1).c = c1;
					break;
				}
			}
		}
		else { //same thing as above
			for(int i = 0; i < white.size(); i++) {
				Piece p = white.get(i);
				if(white.get(i).r == r1 && white.get(i).c == c1) {
					white.add(new Piece(white.get(i).type,'W'));
					white.get(white.size()-1).r = r2;
					white.get(white.size()-1).c = c2;
					white.remove(i);
					
					if(isCheck(newBoard[r2][c2].colour,newBoard)) {
						white.remove(white.size()-1);
						white.add(new Piece(p.type,'W'));
						white.get(white.size()-1).r = r1;
						white.get(white.size()-1).c = c1;
						return false;
					}
					white.remove(white.size()-1);
					white.add(new Piece(p.type,'W'));
					white.get(white.size()-1).r = r1;
					white.get(white.size()-1).c = c1;
					break;
				}
			}
			
		}
				
		return true;
	}// end of isValid method

	public static boolean isCheck(char colour, Piece board[][]) { //checks if the king of colour "colour" is in check
		int r = -1; int c = -1;
		
		if(colour == 'W') {
			for(int i = 0; i < white.size(); i++) { //finds the king and sets the coords to r and c
				if(white.get(i).type == 'K') {
					r = white.get(i).r; c = white.get(i).c;
					break;
				}
			}
			
			for(int i = 0; i < black.size(); i++) {  //goes through every black piece
				ArrayList<Square> attacking = attack(new Square(black.get(i).r,black.get(i).c),board);
				for(int j = 0; j < attacking.size(); j++) { //see if the king is being attacked
					if(attacking.get(j).r == r && attacking.get(j).c == c) {
						return true;
					}
				}
			}
		}
		else {//same as above
			for(int i = 0; i < black.size(); i++) {
				if(black.get(i).type == 'K') {
					r = black.get(i).r; c = black.get(i).c;
					break;
				}
			}
			
			for(int i = 0; i < white.size(); i++) {
				ArrayList<Square> attacking = attack(new Square(white.get(i).r,white.get(i).c),board);
				for(int j = 0; j < attacking.size(); j++) {
					if(attacking.get(j).r == r && attacking.get(j).c == c) {
						return true;
					}
				}
			}
		}
		
		
		return false;
	}//end of isCheck method
	
	public static boolean doesThisMoveStopCheck(int r1, int c1, int r2, int c2) {
		//checks if moving the piece on r1,c1 to r2,c2 stops check
		//Prerequisite: king must be in check, both r1,c1 and r2,c2 is on the borad and is a legal move
		//makes a new temporary board to test with
	
		Piece[][] newBoard = new Piece[8][8];
	
		//copys the board to newBoard
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				newBoard[i][j] = new Piece(board[i][j].type, board[i][j].colour);
				newBoard[i][j].r = i; newBoard[i][j].c = j;
			}
		}
		
		
		Piece p1 = newBoard[r1][c1];
		
		//moves the piece on the newBoard
		newBoard[r2][c2] = new Piece(p1.type,p1.colour); newBoard[r2][c2].r = r2; newBoard[r2][c2].c = c2;
		newBoard[r1][c1] = new Piece(' ',' ');
		if(newBoard[r2][c2].colour == 'B') { //splits it based on the colour
			for(int i = 0; i < black.size(); i++) { //goes through the pieces
				Piece p = black.get(i);
				if(black.get(i).r == r1 && black.get(i).c == c1) { //checks if that piece is the one that was being moved
					//change the corresponding piece in the list of pieces and move it
					black.add(new Piece(p.type,'B'));
					black.get(black.size()-1).r = r2;
					black.get(black.size()-1).c = c2;
					black.remove(i);
					
					//if you are still in check then the move does not stop check
					if(isCheck(newBoard[r2][c2].colour,newBoard)) {
						//change it back to normal
						black.remove(black.size()-1);
						black.add(new Piece(p.type,'B'));
						black.get(black.size()-1).r = r1;
						black.get(black.size()-1).c = c1;
						return false;
					}
					//change the piece back to normal 
					black.remove(black.size()-1);
					black.add(new Piece(p.type,'B'));
					black.get(black.size()-1).r = r1;
					black.get(black.size()-1).c = c1;
					return true;
				}
			}
		}
		else { //same thing as above
			for(int i = 0; i < white.size(); i++) {
				Piece p = white.get(i);
				if(white.get(i).r == r1 && white.get(i).c == c1) {
					white.add(new Piece(white.get(i).type,'W'));
					white.get(white.size()-1).r = r2;
					white.get(white.size()-1).c = c2;
					white.remove(i);
					
					if(isCheck(newBoard[r2][c2].colour,newBoard)) {
						white.remove(white.size()-1);
						white.add(new Piece(p.type,'W'));
						white.get(white.size()-1).r = r1;
						white.get(white.size()-1).c = c1;
						return false;
					}
					white.remove(white.size()-1);
					white.add(new Piece(p.type,'W'));
					white.get(white.size()-1).r = r1;
					white.get(white.size()-1).c = c1;
					return true;
				}
			}
			
		}
		/*newBoard[r1][c1] = new Piece(p2.type,p2.colour); newBoard[r1][c1].r = r1; newBoard[r1][c1].c = c1;
		newBoard[r2][c2] = new Piece(' ',' ');*/
		return true;
			
	}//end of doesThisMoveStopCheck
	
	//checks if the current position has the king with colour "colour" in checkmate
	public static boolean isCheckmate(char colour) {
		//if the king isn't even in check it definitely isn't checkmated
		if(!isCheck(colour,board)) return false;
		
		//splits the code depending on the colour
		if(colour == 'W') {
			//loops through every piece
			for(int i = 0; i < white.size(); i++) {
				Piece p = white.get(i);
				ArrayList<Square> attacking = attack(new Square(p.r,p.c),board);
				//loops through every move each piece could have
				for(int j = 0; j < attacking.size(); j++) {
					//checks if the move stops the check
					if(doesThisMoveStopCheck(p.r,p.c,attacking.get(j).r,attacking.get(j).c)) {
						return false;
					}
				}
			}
		}
		else { //same thing as above
			for(int i = 0; i < black.size(); i++) {
				Piece p = black.get(i);
				ArrayList<Square> attacking = attack(new Square(p.r,p.c),board);
				for(int j = 0; j < attacking.size(); j++) {
					if(doesThisMoveStopCheck(p.r,p.c,attacking.get(j).r,attacking.get(j).c)) {
						return false;
					}
				}
			}
		}
		//if no move stops the checkmate then the king is checkmated
		return true;
		
	}//end of isCheckmate method
	
	public static void main(String[]args) throws InterruptedException {
		Scanner input = new Scanner(System.in);
		String wantTorpedo;
		boolean tutorial = false, repeat = false;
		int r1, r2, c1, c2;
		int move = 1;
        String doYouWantToTutorial;
       type("Welcome to Chess: a digital redition of the classic boardgame!");
       Thread.sleep(2000);
        do {
           type("Would you like to play the tutorial? Enter Y to play it and N to continue to the game.");
           type("First time users are highly recommended to use the tutorial.");

            doYouWantToTutorial = input.next().toUpperCase();

            switch (doYouWantToTutorial) {
            case "Y":
                tutorial = true;
                repeat = false;
                break;
            case "N":
                tutorial = false;
                repeat = false;
                break;
            default:
                System.out.println("Please enter a valid response.\n");
                repeat = true;
                break;
            }
        } while (repeat);

        if (tutorial == true) { 
            initilizeBoard();
            welcome();
        }
        
        System.out.println("What mode of chess would you like to play? Type the number the corresponds to the game mode.");
        System.out.println("1. Normal Chess\n2. Knightmare Chess\n3. Torpedo Chess\n");
        System.out.println("For the rules on how to play these modes, see the bottom of this document:\n[removed] (Knightmare replaces your pieces with knights, and torpedo always allows your pawns to move two spaces)\n");
        
        mode = input.nextInt();
        
        if(mode == 3) {
        	torpedo = true;
        }
        else if(mode == 2) {
        	knightmare = true;
        }
        initilizeBoard();
        System.out.println("This is the Starting Position\n");
		printBoard();
		char curColour = 'B';
		while(true) {
			System.out.print("Input your move: ");
			String from = input.next();
			String to = input.next();
			
			if(from.length() == 2 || to.length() == 2) {
				//reads the user input and parses it to useable numbers
				c1 = (from.charAt(0) - 'a');	
				r1 = 7-(from.charAt(1) - '1');		
				c2 = (to.charAt(0) - 'a');		
				r2 = 7-(to.charAt(1) - '1');
				
				if(isValid(r1,c1,r2,c2) && board[r1][c1].colour != curColour) {
					updateBoard(r1,c1,r2,c2);
					System.out.println("\nThis is the position after move " + move + '\n');
					printBoard();
					System.out.println("");
					if(isCheckmate(curColour)) break;
					curColour = curColour == 'W'? 'B':'W';
					move++;
				}
				else if(!onBoard(r1,c1) || !onBoard(r2,c2)) {
					System.out.println("Your coordinates are incorrect.\n");
				}
				else if(isValid(r1,c1,r2,c2) && board[r1][c1].colour == curColour) {
					//if the colour is wrong lets the user know who's  turn it is
					System.out.println("It is " + (curColour == 'B'? "Red's":"Black's") + " turn to move.");
					System.out.println("Try Again.\n");
				}
				else {
					//the move was not legal and it lets the user know
					System.out.println("This is not a legal move.\n");
				}
				
			}
			else {
				System.out.println("Your coordinates are incorrect.\n");
			}
					
			
			//checks if the move is valid and the right player is moving
			
		

		}
		//game ends and it lets the player know who won
		System.out.print("Checkmate! ");
		if(curColour == 'B') System.out.println("Red Wins!\n");
		else System.out.println("Black Wins!\n");
		
		input.close();
	}//end of main method
}
