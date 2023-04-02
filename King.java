import java.util.ArrayList;

public class King extends Piece{

    public String toString() {//将它返回成对应的字母
        return "K";
    }

    @Override//编译器可以给你验证@Override下面的方法名是否是你父类中所有的，如果没有则报错。
    public boolean isValidMove(Position newPosition, Cell[][] board) {//判断是否符合规则走下一步棋
        ArrayList<Position> possibleMoves = new ArrayList();//建立一个数组包含可以走的下一步

        // 从当前位置生成所有可能的移动
        int currentColumn = this.position.getColumn() - 97;//得到列
        int currentRow = 8 - this.position.getRow();//的行

        // 直线移动
        possibleMoves = tryCell(possibleMoves, board, currentRow + 1, currentColumn);
        possibleMoves = tryCell(possibleMoves, board, currentRow, currentColumn + 1);
        possibleMoves = tryCell(possibleMoves, board, currentRow - 1, currentColumn);
        possibleMoves = tryCell(possibleMoves, board, currentRow, currentColumn - 1);

        // 斜线移动
        possibleMoves = tryCell(possibleMoves, board, currentRow + 1, currentColumn + 1);
        possibleMoves = tryCell(possibleMoves, board, currentRow - 1, currentColumn - 1);
        possibleMoves = tryCell(possibleMoves, board, currentRow + 1, currentColumn - 1);
        possibleMoves = tryCell(possibleMoves, board, currentRow - 1, currentColumn + 1);


        // 比较新的位置是否是所有可能移动的一部分
        boolean isInArray = false;
        for (Position p : possibleMoves) {
            if (p.getString().equals(newPosition.getString())) {
                isInArray = true;

            }
        }
        return isInArray;
    }

}
