package com.drubalsky.chess;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.drubalsky.chess.pieces.Piece;

public class Chess {

	public static void main(String[] args) {
		boolean isGameOver;
		boolean isWhitesTurn;
		Board b = null;
		List<Piece> myPieces;
		Scanner in = new Scanner(System.in);
		boolean funnyStalemate = false;
		
		while (!funnyStalemate) {
			isGameOver = false;
			isWhitesTurn = true;
			b = new Board();
			
			//System.out.println(b);
		
			while (!isGameOver) {
	//			in.nextLine();
				
				myPieces = b.getMyPieces(isWhitesTurn);
				Collections.shuffle(myPieces);
				int i;
				for (i = 0; i < myPieces.size(); i++) {
					if (myPieces.get(i).moveRandomly()) {
						//System.out.println(b);
						break;
					}
				}
				
				if (i == myPieces.size()) isGameOver = true;
				if (b.getMyPieces(true).size() == 1 && b.getMyPieces(false).size() == 1) isGameOver = true;
				
				isWhitesTurn = !isWhitesTurn;
			}
	
			if (isWhitesTurn && b.amIInCheck(false)) {
				//System.out.println("White won");
			} else if (!isWhitesTurn && b.amIInCheck(true)) {
				//System.out.println("Black won");
			} else {
				//System.out.println("Stalemate");
				if (b.getMyPieces(true).size() > 8 || b.getMyPieces(false).size() > 8)
					funnyStalemate = true;
			}
		}
		System.out.println(b);
		in.close();
	}
}
