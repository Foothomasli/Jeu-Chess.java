public class Position {//位置
    //设立2个属性,获取行和列
    private char column;
    private int row;

    //方法
    public void init(char column, int row) {//设置行和列
        this.column = column;
        this.row = row;
    }

    public char getColumn(){//得到列

        return this.column;
    }

    public int getRow(){//得到行

        return this.row;
    }

    public String toString(char column, int row) {//获取位置

        return column + String.valueOf(row);
    }

    public String getString() {
        return String.valueOf(column) + row;
    }
}
