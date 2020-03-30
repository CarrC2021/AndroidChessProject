package edu.up.cs301.androidchessproject.boardandpieces;

public class GameBoard {

    private ChessSquare[] squares;

    public GameBoard(ChessSquare[] board){

        squares = board;
    }

    //creates the default game board with all pieces in their starting positions
    public GameBoard(){

        squares = createEmptySquares();

        for(int i = 0; i < 8; i++){
            squares[7 + i].setPiece(new Pawn(7 + i, 1));
        }
        for(int i = 0; i < 8; i++){
            squares[48 + i].setPiece(new Pawn(48 + i, 0));
        }

        //now the rooks
        squares[0].setPiece(new Rook(0, 1));
        squares[7].setPiece(new Rook(7, 1));
        squares[56].setPiece(new Rook(56, 0));
        squares[63].setPiece(new Rook(63, 0));

        //now the knights
        squares[1].setPiece(new Knight(1, 1));
        squares[6].setPiece(new Knight(6, 1));
        squares[57].setPiece(new Knight(57, 0));
        squares[62].setPiece(new Knight(62, 0));

        //now the bishops
        squares[2].setPiece(new Bishop(2, 1));
        squares[5].setPiece(new Bishop(5, 1));
        squares[58].setPiece(new Bishop(58, 0));
        squares[61].setPiece(new Bishop(61, 0));

        //now the kings and queens
        squares[3].setPiece(new Queen(3, 1));
        squares[4].setPiece(new King(4, 1));
        squares[59].setPiece(new Queen(59, 0));
        squares[60].setPiece(new King(60, 0));
    }

    public ChessSquare[] getSquares() {
        return squares;
    }

    public void setSquares(ChessSquare[]squares) {
        this.squares = squares;
    }

    public ChessSquare[] createEmptySquares(){
        ChessSquare[] squares = new ChessSquare[64];
        for(int i = 0; i<64;i++){
            squares[i] = new ChessSquare();
        }
        return squares;
    }
}
