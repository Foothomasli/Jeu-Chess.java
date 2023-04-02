public class Cell {
    private Position position;
    private boolean isEmpty;//如果单元格为空，则返回 true，否则返回 false；
    private Piece piece;

    public void init(Position position, boolean isEmpty, Piece piece) {
        this.position = position;
        this.isEmpty = isEmpty;
        this.piece = piece;
    }

    public void setPiece(Piece piece) {//设定为某个棋子,且不为空
        this.piece = piece;
        this.isEmpty = false;
    }

    public void setEmpty() {//设定为空,没有棋子
        this.piece = null;
        this.isEmpty = true;
    }

    public boolean isEmpty(){//判断是否为空

        return this.isEmpty;
    }

    public Piece getPiece() {//判断是什么棋子
        if (!isEmpty) {
            return this.piece;
        }
        return null;
    }

    public Position getPosition() {//得到位置的数据

        return this.position;
    }

    public void movePiece(Cell destinationCell) {//移动棋子
        destinationCell.setPiece(this.piece);
        this.piece.setPosition(destinationCell.getPosition());
        this.setEmpty();
    }
}
