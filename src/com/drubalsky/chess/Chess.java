package com.drubalsky.chess;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.drubalsky.chess.pieces.Piece;
import com.drubalsky.chess.pieces.Queen;
import com.drubalsky.chess.pieces.Rook;
import com.drubalsky.chess.util.ChessPoint;

public class Chess {

	public static final boolean HUMAN_PLAY = false;
	public static Random rnd = new Random();
	
	public static void main(String[] args) {
		long seed = Long.MIN_VALUE;
		
		boolean isGameOver;
		boolean isWhitesTurn;
		Board b = null;
		List<Piece> myPieces;
		Scanner in = new Scanner(System.in);
		boolean funnyStalemate = false;
		
		while (true) {
			//seed = rnd.nextLong();
			rnd.setSeed(seed);
			seed++;
			isGameOver = false;
			isWhitesTurn = true;
			b = new Board();
			
			if (HUMAN_PLAY) System.out.println(b);
		
			while (!isGameOver) {
	//			in.nextLine();
				
				if (HUMAN_PLAY && isWhitesTurn) {
					letPlayerMove(in, b, isWhitesTurn);
				} else {
					myPieces = b.getMyPieces(isWhitesTurn);
					Collections.shuffle(myPieces, rnd);
					int i;
					for (i = 0; i < myPieces.size(); i++) {
						if (myPieces.get(i).moveRandomly()) {
							if (HUMAN_PLAY) System.out.println(b);
							break;
						}
					}
					
					if (i == myPieces.size()) isGameOver = true;
					if (b.getMyPieces(true).size() == 1 && b.getMyPieces(false).size() == 1) isGameOver = true;
				}
				
				isWhitesTurn = !isWhitesTurn;
			}
			//System.out.println(b);
			if (isWhitesTurn && b.amIInCheck(false)) {
				//System.out.println("White won");
				if (b.getMyPieces(true).size() < 4) {
					funnyStalemate = true;
					for (Piece p: b.getMyPieces(true)) {
						if (p instanceof Queen || p instanceof Rook) {
							funnyStalemate = false;
							break;
						}
					}
					if (funnyStalemate) {
						System.out.println("SEED: " + seed + "\n" + b);
						in.nextLine();
					}
				}
			} else if (!isWhitesTurn && b.amIInCheck(true)) {
				//System.out.println("Black won");
				if (b.getMyPieces(false).size() < 4) {
					funnyStalemate = true;
					for (Piece p: b.getMyPieces(false)) {
						if (p instanceof Queen || p instanceof Rook) {
							funnyStalemate = false;
							break;
						}
					}
					if (funnyStalemate) {
						System.out.println("SEED: " + seed + "\n" + b);
						in.nextLine();	
					}
				}
			} else {
				if (b.getMyPieces(true).size() > 8 || b.getMyPieces(false).size() > 8) funnyStalemate = true;
				if (funnyStalemate) {
					System.out.println("Stalemate");
					System.out.println("SEED: " + seed + "\n" + b);
					in.nextLine();
				}
			}
		}
	//System.out.println(b);
		//in.close();
	}
	
	public static ChessPoint getChessPointFromNotation(String tile) {
		if (tile.length() != 2) return null;
		
		int x, y;
		
		//eh
		//x = ((int) tile.charAt(0)) - 65;
		//y = 8 - Character.digit(tile.charAt(1), 10);
		
		switch (tile.charAt(0)) {
		case 'A':
			x = 0;
			break;
		case 'B':
			x = 1;
			break;
		case 'C':
			x = 2;
			break;
		case 'D':
			x = 3;
			break;
		case 'E':
			x = 4;
			break;
		case 'F':
			x = 5;
			break;
		case 'G':
			x = 6;
			break;
		case 'H':
			x = 7;
			break;
		default:
			return null;
		}
		
		switch (tile.charAt(1)) {
		case '1':
			y = 7;
			break;
		case '2':
			y = 6;
			break;
		case '3':
			y = 5;
			break;
		case '4':
			y = 4;
			break;
		case '5':
			y = 3;
			break;
		case '6':
			y = 2;
			break;
		case '7':
			y = 1;
			break;
		case '8':
			y = 0;
			break;
		default:
			return null;
		}
		
		return new ChessPoint(x, y);
	}
	
	private static void letPlayerMove(Scanner in, Board b, boolean isWhitesTurn) {
		String tile;
		ChessPoint cpFrom = null, cpTo = null;
		Piece piece = null;
		while (piece == null) {
			while (cpFrom == null) {
				System.out.println("Which tile would you like to move a piece from? ");
				tile = in.next();
				cpFrom = getChessPointFromNotation(tile);
			}
			piece = b.getPiece(cpFrom);
			if (piece != null) {
				if (piece.isWhite() != isWhitesTurn) {
					System.out.println("Not your piece!");
				} else if (piece.getPossibleMoves().isEmpty()) {
					System.out.println("Your " + piece + " cannot move!");
					piece = null;
				}
			} else {
				System.out.println("That's an empty tile!");
			}
			cpFrom = null;
		}
		List<ChessPoint> possibleMoves;
		while (cpTo == null) {
			System.out.println("Which tile would you like to move your " + piece + " to?");
			System.out.println("Possible Moves: " + (possibleMoves = piece.getPossibleMoves()) + " ");
			tile = in.next();
			cpTo = getChessPointFromNotation(tile);
			if (!possibleMoves.contains(cpTo)) {
				System.out.println("Not a valid move!");
				cpTo = null;
			}
		}
		piece.move(cpTo);
	}
}
