import java.util.ArrayList;

public class Piece {
    protected Position position;
    protected int color;

    public void init(Position position, int color) {//设定位置和颜色
        this.position = position;
        this.color = color;
    }

    public int getColor() {//得到颜色

        return this.color;
    }

    public void setPosition(Position newPosition){//设定新的位置

        this.position = newPosition;
    }

    public ArrayList<Position> tryCell(ArrayList<Position> possibleMoves, Cell[][] board, int indexRow, int indexColumn) {
        try {
            if (indexRow<8 && indexRow>=0 && indexColumn<8 && indexColumn>=0){//在棋盘范围内
                boolean isEmpty = board[indexRow][indexColumn].isEmpty();

                if (isEmpty) {
                    possibleMoves.add(board[indexRow][indexColumn].getPosition());
                }

                //对手的棋子
                else if (board[indexRow][indexColumn].getPiece().getColor() != color){
                    possibleMoves.add(board[indexRow][indexColumn].getPosition());
                }
            }
        } catch (Exception ignored) {
        }
        return possibleMoves;
    }

    public boolean isValidMove(Position newPosition, Cell[][] board){
        return false;
    }
}
