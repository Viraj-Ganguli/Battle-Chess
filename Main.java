import java.math.MathContext;
import java.util.ArrayList;
import java.util.Scanner;

import javax.management.Query;

import java.util.Arrays;

class Main {

  public static void main(String[] args) {

    cls();

    Scanner in = new Scanner(System.in);
    // Black Pieces
    Rook LBRook = new Rook(1, 0, 0);
    Knight DBKnight = new Knight(1, 0, 1);
    Bishop LBBishop = new Bishop(1, 0, 2);
    Queen BQueen = new Queen(1, 0, 3);
    King BKing = new King(1, 0, 4);
    Bishop DBBishop = new Bishop(1, 0, 5);
    Knight LBKnight = new Knight(1, 0, 6);
    Rook DBRook = new Rook(1, 0, 7);
    // Black Pawns
    Pawn aBPawn = new Pawn(1, 1, 0);
    Pawn bBPawn = new Pawn(1, 1, 1);
    Pawn cBPawn = new Pawn(1, 1, 2);
    Pawn dBPawn = new Pawn(1, 1, 3);
    Pawn eBPawn = new Pawn(1, 1, 4);
    Pawn fBPawn = new Pawn(1, 1, 5);
    Pawn gBPawn = new Pawn(1, 1, 6);
    Pawn hBPawn = new Pawn(1, 1, 7);
    // White pieces
    Rook LWRook = new Rook(0, 7, 7);
    Knight DWKnight = new Knight(0, 7, 6);
    Bishop LWBishop = new Bishop(0, 7, 5);
    Queen WQueen = new Queen(0, 7, 3);
    King WKing = new King(0, 7, 4);
    Bishop DWBishop = new Bishop(0, 7, 2);
    Knight LWKnight = new Knight(0, 7, 1);
    Rook DWRook = new Rook(0, 7, 0);
    // White Pawns
    Pawn aWPawn = new Pawn(0, 6, 0);
    Pawn bWPawn = new Pawn(0, 6, 1);
    Pawn cWPawn = new Pawn(0, 6, 2);
    Pawn dWPawn = new Pawn(0, 6, 3);
    Pawn eWPawn = new Pawn(0, 6, 4);
    Pawn fWPawn = new Pawn(0, 6, 5);
    Pawn gWPawn = new Pawn(0, 6, 6);
    Pawn hWPawn = new Pawn(0, 6, 7);

    // list of all successful moves algebraic notation
    ArrayList<String> pgnMoves = new ArrayList<String>();

    // array representation of the chess board
    Piece[][] board = {
        { LBRook, DBKnight, LBBishop, BQueen, BKing, DBBishop, LBKnight, DBRook },
        { aBPawn, bBPawn, cBPawn, dBPawn, eBPawn, fBPawn, gBPawn, hBPawn },
        { null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null },
        { aWPawn, bWPawn, cWPawn, dWPawn, eWPawn, fWPawn, gWPawn, hWPawn },
        { LWRook, DWKnight, LWBishop, WQueen, WKing, DWBishop, LWKnight, DWRook }
    };

    // initialize arraylist of pieces
    ArrayList<Piece> pieces = new ArrayList<Piece>(Arrays.asList(
        aBPawn, bBPawn, cBPawn, dBPawn, eBPawn, fBPawn, gBPawn, hBPawn,
        aWPawn, bWPawn, cWPawn, dWPawn, eWPawn, fWPawn, gWPawn, hWPawn,
        LBRook, DBKnight, LBBishop, BQueen, BKing, DBBishop, LBKnight, DBRook,
        LWRook, DWKnight, LWBishop, WQueen, WKing, DWBishop, LWKnight, DWRook));

    String input;
    int turn = 0;
    double numTurns = 1;

    boolean permWhite = false;
    boolean boardless = false;

    // printing instructions
    System.out.println(instructions());
    in.nextLine();
    cls();
    // debug stuff

    // printing board
    System.out.println("Turn: 1\t\t\t\t\t\tWhite");
    printBoard(board);

    while (true) {
      // get move
      System.out.println("Enter Move: ");
      input = in.nextLine();

      // debug stuff/special commands
      if (input.equals("pieces")) {
        cls();
        printBoard(board);
        System.out.println("pieces: " + pieces.size());
        for (Piece p : pieces) {
          System.out.println(p);
        }
        in.nextLine();// hit enter to stop seeing debug stuff
        cls();
        printBoard(board);
        continue;
      }
      if (input.equals("white")) {
        permWhite = !permWhite;
        turn = 0;
        continue;
      }
      if (input.equals("boardless")) {
        boardless = !boardless;
        continue;
      }
      if (boardless && input.equals("print")) {
        printBoard(board);
        continue;
      }
      if (input.equals("instructions")) {
        System.out.println(instructions());
        in.nextLine();
        cls();
        printBoard(board);
        continue;
      }
      if (input.equals("clear")) {
        cls();
        if (!boardless) {
          printBoard(board);
        }
        continue;
      }
      if (input.equals("quit") || input.equals("exit")) {
        System.exit(0);
      }
      if (input.equals("reset") || input.equals("restart")) {
        main(args);// will crash if used too many times (aproxx 1012 times(this took 80 secs per
                   // test))
      }

      // do the move
      if (!move(input, board, pieces, turn, pgnMoves))
        continue;

      // change turns
      turn = permWhite ? 0 : (turn == 0 ? 1 : 0);
      // increment numTurns
      numTurns += .5;

      if (!boardless) {
        // clear the screen
        cls();
        // draw the now changed board
        System.out.println("Turn: " + ((int) numTurns) + "\t\t\t\t\t\t" + (turn == 0 ? "White" : "Black"));
        printBoard(board);
      }
      // check for winner
      if (WKing.getHP() <= 0) {
        System.out.println("\u001B[38;5;2mBlack wins!\u001B[0m");
        System.out.println("PGN: " + pgnGenerator(pgnMoves));
        System.exit(0);
      }
      if (BKing.getHP() <= 0) {
        System.out.println("\u001B[38;5;2mWhite wins!\u001B[0m");
        System.out.println("PGN: " + pgnGenerator(pgnMoves));
        System.exit(0);
      }
    }
  }

  // method that clears the screen
  public static void cls() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  // prints a readable and formatted version of the chess board
  public static void printBoard(Piece[][] board) {
    System.out.println("\u001B[38;5;00m\u001B[48;5;255m┌───────────────────────────────┐\u001B[0m");
    System.out.print("\u001B[38;5;00m\u001B[48;5;255m│\u001B[0m");
    for (int number = 0; number < board.length; number++) {
      for (int letter = 0; letter < board.length; letter++) {
        if (board[number][letter] == null) {
          System.out.print("\u001B[38;5;00m\u001B[48;5;255m   │\u001B[0m");
        } else {
          System.out.print("\u001B[38;5;00m\u001B[48;5;255m " + board[number][letter].getSymbol() + " │\u001B[0m");
        }
      }
      System.out.println("\u001B[0m " + (8 - number));

      if (number != 7) {
        System.out.print("\u001B[38;5;00m\u001B[48;5;255m│───┼───┼───┼───┼───┼───┼───┼───│\u001B[0m");
        System.out.println();
        System.out.print("\u001B[38;5;00m\u001B[48;5;255m│\u001B[0m");
      }
    }
    System.out.println("\u001B[38;5;00m\u001B[48;5;255m└───────────────────────────────┘\u001B[0m");
    System.out.println("\u001B[0m  A   B   C   D   E   F   G   H\u001B[0m");
  }

  // creates a string of the PGN of the chess match
  public static String pgnGenerator(ArrayList<String> pgnMoves) {
    int count = 1;
    String readableStr = "";
    for (int i = 0; i < pgnMoves.size() - 1; i++) {
      if (i % 2 == 0) {
        readableStr += count + ". ";
        count++;
      }
      readableStr += pgnMoves.get(i) + " ";
    }
    return readableStr;
  }

  // moves a piece; returns whether it succeeded
  public static boolean move(String input, Piece[][] board, ArrayList<Piece> pieces, int turn,
      ArrayList<String> pgnMoves) {

    String pawnRegex = "[a-h][1-8]";
    String pieceRegex = "[KQBRN]x?[a-h][1-8]";
    String pawnTakeRegex = "[a-h]x[a-h][1-8]";
    String multiPieceRegex = "[KQBRN][a-h1-8]x?[a-h][1-8]";

    if (input.matches(pawnRegex)) {
      for (Piece piece : pieces) {
        if (!(piece instanceof Pawn)) {
          continue;
        }
        Pawn pawn = (Pawn) piece;
        if (pawn.getColor() == turn && pawn.calcLegalMove(input, board) == 1) {
          board[pawn.getY()][pawn.getX()] = null;
          pawn.setY(8 - Integer.parseInt(input.substring(1)));
          board[pawn.getY()][pawn.getX()] = pawn;
          if (pawn.getY() == (pawn.getColor() == 0 ? 0 : 7)) {
            pawn.promote(pieces, board);
          }
          pgnMoves.add(input);
          return true;
        }
      }
    } else if (input.matches(pieceRegex)) {
      boolean hadx = false;
      if (input.contains("x")) {
        input = input.substring(0, 1) + input.substring(2);
        hadx = true;
      }
      String p = input.substring(0, 1);
      String move = input.substring(1);
      int x = Piece.calcColumn(move.substring(0, 1));
      int y = 8 - Integer.parseInt(move.substring(1));

      Piece piece = null;
      boolean take = false;

      for (Piece temp : pieces) {
        if (temp.getColor() == turn
            && ((p.equals("K") && temp instanceof King) || (p.equals("Q") && temp instanceof Queen)
                || (p.equals("B") && temp instanceof Bishop)
                || (p.equals("R") && temp instanceof Rook) || (p.equals("N") && temp instanceof Knight))) {
          if (!hadx && temp.calcLegalMove(move, board) == 1) {
            if (piece != null) {
              System.out.println("\u001B[38;5;1mSpecify Piece\u001B[0m");
              return false;
            }
            take = false;
            piece = temp;
          } else if (hadx && temp.calcLegalMove(move, board) == 2) {
            if (piece != null) {
              System.out.println("\u001B[38;5;1mSpecify Piece\u001B[0m");
              return false;
            }
            take = true;
            piece = temp;
          }
        }
      }
      if (piece != null) {
        if (!take) {
          board[piece.getY()][piece.getX()] = null;
          piece.setY(y);
          piece.setX(x);
          board[y][x] = piece;
          pgnMoves.add(input);
          return true;
        } else {
          if (piece.fight(board[y][x])) {
            board[piece.getY()][piece.getX()] = null;
            pieces.remove(board[y][x]);
            piece.setY(y);
            piece.setX(x);
            board[y][x] = piece;
          } else {
            pieces.remove(piece);
            board[y][x] = null;
          }
          pgnMoves.add(input);
          return true;
        }
      }
    } else if (input.matches(pawnTakeRegex)) {
      for (Piece piece : pieces) {
        if (!(piece instanceof Pawn)) {
          continue;
        }
        Pawn pawn = (Pawn) piece;
        if (pawn.getColor() == turn && pawn.getX() == Piece.calcColumn(input.substring(0, 1))
            && pawn.calcLegalMove(input.substring(2), board) == 2) {
          int x = Piece.calcColumn(input.substring(2, 3));
          int y = 8 - Integer.parseInt(input.substring(3));
          if (pawn.fight(board[y][x])) {
            board[pawn.getY()][pawn.getX()] = null;
            pieces.remove(board[y][x]);
            pawn.setY(y);
            pawn.setX(x);
            board[y][x] = pawn;
            if (pawn.getY() == (pawn.getColor() == 0 ? 0 : 7)) {
              pawn.promote(pieces, board);
            }
          } else {
            pieces.remove(pawn);
            board[pawn.getY()][pawn.getX()] = null;
          }
          pgnMoves.add(input);
          return true;
        }
      }
    }

    else if (input.matches(multiPieceRegex)) {
      boolean hadx = false;
      if (input.contains("x")) {
        input = input.substring(0, 2) + input.substring(3);
        hadx = true;
      }
      String p = input.substring(0, 1);
      String loc = input.substring(1, 2);
      String move = input.substring(2);
      int x = Piece.calcColumn(move.substring(0, 1));
      int y = 8 - Integer.parseInt(move.substring(1));

      Piece piece = null;
      boolean take = false;

      for (Piece temp : pieces) {
        if (temp.getColor() == turn
            && (loc.matches("[a-h]") ? temp.getX() == Piece.calcColumn(loc)
                : (loc.matches("[1-8]") ? temp.getY() == 8 - Integer.parseInt(loc) : false))
            && ((p.equals("K") && temp instanceof King) || (p.equals("Q") && temp instanceof Queen)
                || (p.equals("B") && temp instanceof Bishop)
                || (p.equals("R") && temp instanceof Rook) || (p.equals("N") && temp instanceof Knight))) {
          if (!hadx && temp.calcLegalMove(move, board) == 1) {
            if (piece != null) {
              System.out.println("\u001B[38;5;1mSpecify Piece\u001B[0m");
              return false;
            }
            take = false;
            piece = temp;
          } else if (hadx && temp.calcLegalMove(move, board) == 2) {
            if (piece != null) {
              System.out.println("\u001B[38;5;1mSpecify Piece\u001B[0m");
              return false;
            }
            take = true;
            piece = temp;
          }
        }
      }
      if (piece != null) {
        if (!take) {
          board[piece.getY()][piece.getX()] = null;
          piece.setY(y);
          piece.setX(x);
          board[y][x] = piece;
          pgnMoves.add(input);
          return true;
        } else {
          if (piece.fight(board[y][x])) {
            board[piece.getY()][piece.getX()] = null;
            pieces.remove(board[y][x]);
            piece.setY(y);
            piece.setX(x);
            board[y][x] = piece;
          } else {
            pieces.remove(piece);
            board[y][x] = null;
          }
          pgnMoves.add(input);
          return true;
        }
      }
    }
    System.out.println("\u001B[38;5;1mINVALID MOVE\u001B[0m");
    return false;

  }

  // returns string of the instructions for the game
  public static String instructions() {
    cls();
    return "instructions: \n\n" +
        "To replay the game type \"reset\" or \"restart\" when prompted for a move\n\n" +
        "To exit the game type \"quit\" or \"exit\"\n\n" +
        "To clear the board type \"clear\"\n\n" +
        "To play without the chess board type \"boardless\"\n\n" +
        "To print the board type \"print\"\n\n" +
        "To see instructions again type \"instructions\"\n\n" +
        "This game uses chess notation:\n\n" +
        "Each piece is represented by a letter:\n" +
        "King: K\n" +
        "Queen: Q\n" +
        "Rook: R\n" +
        "Bishop: B\n" +
        "Knight: N (to distinguish from King, which uses K)\n" +
        "Pawn: No symbol, only the destination square is recorded.\n" +
        "To record a move, write the piece symbol (if applicable) followed by the destination square.\n" +
        "Example: Nf3 (Knight to f3), e5 (Pawn to e5), Bb5 (Bishop to b5).\n\n" +
        "Pawn Moves:\n\n" +
        "When a pawn moves, only the destination square is recorded. For example, e4 (Pawn to e4) or d5 (Pawn to d5).\n"
        +
        "If a pawn captures a piece, the file of the pawn's starting square is added before the \"x\" symbol. " +
        "For example, exd5 (Pawn on e captures on d5).\n\n" +
        "Piece Moves:\n\n" +
        "The symbol for the piece is written followed by the destination square.\n" +
        "If two or more identical pieces can move to the same square, the piece's starting square is added before the destination square.\n"
        +
        "Example: Nbd2 (Knight from b to d2).\n\n" +
        "Capturing:\n\n" +
        "When a piece captures an opponent's piece, the \"x\" symbol is added between the piece symbol (if applicable) and the destination square.\n"
        +
        "Example: Bxe5 (Bishop captures on e5), Nxc3 (Knight captures on c3). \n \n" + "Press Enter to continue";
  }
}
