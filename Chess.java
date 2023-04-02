import java.util.regex.*;
import java.util.Scanner;

public class Chess {//一个完整的棋局
    private Cell[][] board;//定义棋盘--以Cell为类型的数组
    private Player[] players = new Player[2];//创建包含2个玩家的数组,但此时为null
    private Player currentPlayer;//创造出"现行玩家"的对象
    private static Pattern pattern;
    private static Matcher matcher;

    public void play() {
        while (true) {
            createPlayers();
            initialiseBoard();
            while (!isCheckMate()) {//只要不是被将军,游戏继续进行下去
                printBoard();
                String move;
                do {
                    move = askMove();//先问想要执行的步骤
                }
                while (!isValidMove(move) && move.length() == 7);//假如收到的步骤不符合规则,则返回循环重新进行询问
                updateBoard(move);
                switchPlayer();
            }
        }
    }

    private void createPlayers() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Quel est le nom du joueur des pions rouges (blancs) ? : ");
        String nameWhite = scanner.next();//询问白棋玩家的姓名
        System.out.print("Quel est le nom du joueur des pions bleus (noirs) ? : ");
        String nameBlack = scanner.next();//询问黑棋玩家的姓名
        players[0] = new Player();//在对应的数组创建对象
        players[0].init(nameWhite, 0);//将收到的名字和颜色赋值
        players[1] = new Player();//在对应的数组创建对象
        players[1].init(nameBlack, 1);//将收到的名字和颜色赋值
        currentPlayer = players[0];//白棋先动
    }

    private void initialiseBoard() {//将初始化棋盘

        // 将棋盘初始化,并且重置棋子,同时分配位置
        board = new Cell[8][8];//对创建以Cell为类型的数组(棋盘)board进行声明大小,此时为null
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell();//使用覆历的方法,将棋盘里的每个空间都创立Cell对象
            }
        }

        Position[][] position = new Position[8][8];//创建Position的数组
        for (int i = 0; i < position.length; i++) {
            for (int j = 0; j < position[i].length; j++) {
                position[i][j] = new Position();//对数组里的每个位置创建一个Position对象
            }
        }

        //棋盘的最左上角为a8,所以先创建对应的符号
        char columnChar = 'a';
        int row = 8;

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                position[i][j].init(columnChar, row);//使用.init方法对position进行赋值,从最左上角开始
                board[i][j].init(position[i][j], true, null);//position复制之后,再将它赋值到对应的Cell.并且每个Cell都为空,没有棋子
                columnChar += 1;//因为是一行一行的赋值,所以,行不变,列每次++
            }
            row -= 1;//因为从上往下数字以此减少,当一行赋值完后,需要--再复制下一行
            columnChar = 'a';//每行赋值完后,又从a开始
        }


        //重置棋盘上的所有棋子--白色棋子

        King WK = new King(); // 设置白色King的对象
        Position positionWK = new Position(); // 创建Position对象
        positionWK.init('e', 1); // 对这个对象设置对应位置的值
        WK.init(positionWK, 0);//然后调用Piece里的函数,定义它的位置还有颜色
        board[7][4].setPiece(WK);//最终在board设立这枚棋子

        Queen WQ = new Queen();
        Position positionWQ = new Position();
        positionWQ.init('d', 1);
        WQ.init(positionWQ, 0);
        board[7][3].setPiece(WQ);

        Pawn[] WP = new Pawn[8];//对于有好几个的棋子,则先设立对应的数组
        Position[] positionWP = new Position[8];//数组的每个位置设立Position对象
        for (int i = 0; i <= 7; i++) {
            WP[i] = new Pawn();//创立8个Pawn
            positionWP[i] = new Position();//每个Pawn都设置一个Position
            positionWP[i].init(columnChar, 2);
            WP[i].init(positionWP[i], 0);
            board[6][i].setPiece(WP[i]);//对于第7行,每一列都设置Pawn棋子
            columnChar += 1;
        }

        Bishop WB1 = new Bishop();
        Bishop WB2 = new Bishop();
        Position positionWB1 = new Position();
        Position positionWB2 = new Position();

        positionWB1.init('c', 1);
        WB1.init(positionWB1, 0);
        board[7][2].setPiece(WB1);

        positionWB2.init('f', 1);
        WB2.init(positionWB2, 0);
        board[7][5].setPiece(WB2);


        Knight WN1 = new Knight();
        Knight WN2 = new Knight();
        Position positionWN1 = new Position();
        Position positionWN2 = new Position();

        positionWN1.init('b', 1);
        WN1.init(positionWN1, 0);
        board[7][1].setPiece(WN1);

        positionWN2.init('g', 1);
        WN2.init(positionWN2, 0);
        board[7][6].setPiece(WN2);


        Rook WR1 = new Rook();
        Rook WR2 = new Rook();
        Position positionWR1 = new Position();
        Position positionWR2 = new Position();

        positionWR1.init('a', 1);
        WR1.init(positionWR1, 0);
        board[7][0].setPiece(WR1);

        positionWR2.init('h', 1);
        WR2.init(positionWR2, 0);
        board[7][7].setPiece(WR2);

        //设置完白棋之后,需要设置黑棋
        King BK = new King();
        Position positionBK = new Position();
        positionBK.init('e', 8);
        BK.init(positionBK, 1);
        board[0][4].setPiece(BK);

        Queen BQ = new Queen();
        Position positionBQ = new Position();
        positionBQ.init('d', 8);
        BQ.init(positionBQ, 1);
        board[0][3].setPiece(BQ);

        Pawn[] BP = new Pawn[8];
        Position[] positionBP = new Position[8];
        columnChar = 'a';
        for (int i = 0; i <= 7; i++) {
            BP[i] = new Pawn();
            positionBP[i] = new Position();
            positionBP[i].init(columnChar, 7);
            BP[i].init(positionBP[i], 1);
            board[1][i].setPiece(BP[i]);
            columnChar += 1;
        }

        Bishop BB1 = new Bishop();
        Bishop BB2 = new Bishop();
        Position positionBB1 = new Position();
        Position positionBB2 = new Position();

        positionBB1.init('c', 8);
        BB1.init(positionBB1, 1);
        board[0][2].setPiece(BB1);

        positionBB2.init('f', 8);
        BB2.init(positionBB2, 1);
        board[0][5].setPiece(BB2);


        Knight BN1 = new Knight();
        Knight BN2 = new Knight();
        Position positionBN1 = new Position();
        Position positionBN2 = new Position();

        positionBN1.init('b', 8);
        BN1.init(positionBN1, 1);
        board[0][1].setPiece(BN1);

        positionBN2.init('g', 8);
        BN2.init(positionBN2, 1);
        board[0][6].setPiece(BN2);

        Rook BR1 = new Rook();
        Rook BR2 = new Rook();
        Position positionBR1 = new Position();
        Position positionBR2 = new Position();

        positionBR1.init('a', 8);
        BR1.init(positionBR1, 1);
        board[0][0].setPiece(BR1);

        positionBR2.init('h', 8);
        BR2.init(positionBR2, 1);
        board[0][7].setPiece(BR2);
    }

    private void printBoard() {//打印当前的棋局
        char columnChar = 'a';
        int row = 8;
        System.out.print(" " + '\t' + "|" + '\t');
        for (int j = 0; j <= 7; j++) {//首先先打印最上面代表列的字母
            System.out.print(columnChar);
            System.out.print('\t' + "|" + '\t');
            columnChar += 1;
        }
        System.out.println();//换行2次,更美观
        System.out.println();
        for (int i = 0; i <= 7; i++) {//开始打印棋盘部分
            System.out.print(row);//首先需要打印出在左侧代表行的数字
            System.out.print('\t' + "|" + '\t');
            for (int j = 0; j <= 7; j++) {
                Piece piece = board[i][j].getPiece();//创建棋子并判断是什么棋子
                if (piece != null) {//判断是empty还是有棋子
                    if (piece.getColor() == 0) {//判断为白色
                        System.out.print(ConsoleColors.GREEN + piece + ConsoleColors.RESET);//由于在运行时所有字符都为白色,所以,将白色棋子改为红色
                    }
                    else if (piece.getColor() == 1) {
                        System.out.print(ConsoleColors.YELLOW + piece + ConsoleColors.RESET);//将黑色棋子改为绿色
                    }

                } else {
                    System.out.print(" ");//如果是empty则输出空格
                }
                System.out.print('\t' + "|" + '\t');
            }
            System.out.print(row);//输出右侧代表列的数字
            System.out.println();//换行,再次进行循环
            row -= 1;
        }
        System.out.println();
        columnChar = 'a';
        System.out.print(" " + '\t' + "|" + '\t');
        for (int j = 0; j <= 7; j++) {//输出底层字母
            System.out.print(columnChar);
            System.out.print('\t' + "|" + '\t');
            columnChar += 1;
        }
        System.out.println();
    }

    private String askMove() {//询问下一步棋
        Scanner scanner = new Scanner(System.in);
        String move;
        // 使用正则表达式判断属于步骤是否符合规则
        pattern = Pattern.compile("\\w\\w\\d\\s\\w\\w\\d");//W为字母,D为数字,S为空格
        do {
            System.out.print("Quel est votre action " + currentPlayer.getName() + " ? (Exemples:Nb1  Nc3, Pd1  Pd3, Pc1  Pc4): ");
            move = scanner.nextLine();
            matcher = pattern.matcher(move);
        } while (!matcher.find());

        return move;
    }

    private boolean isCheckMate() {
        return false;
    }

    private boolean isValidMove(String move) {//得到move的步骤之后,就开始检验是否符合规则
        String[] pieces = move.split(" ");//根据匹配给定的正则表达式来拆分字符串。(regex -- 正则表达式分隔符)
        String sourcePiece = pieces[0];//得到的是空格前面部分
        String isValidPiece = pieces[1];//得到空格后面的部分
        int sourceColumn = sourcePiece.charAt(1) - 97;//字母"a"在Unicode里为97,使用第二个字母 - 97得到列
        int sourceRow = 8 - Integer.parseInt(String.valueOf(sourcePiece.charAt(2)));//用8 - 目标的行可以得知第几行
        Position isValidPosition = new Position();
        isValidPosition.init(isValidPiece.charAt(1), Integer.parseInt(String.valueOf(isValidPiece.charAt(2))));//获取想要移动目标的行和列
        Position sourcePosition = new Position();
        sourcePosition.init(sourcePiece.charAt(1), Integer.parseInt(String.valueOf(sourcePiece.charAt(2))));//获取原本位置的行和列
        boolean isValid = false;

        if (currentPlayer.getColor() == board[sourceRow][sourceColumn].getPiece().getColor()) {//判断这个棋子的颜色是否是当前玩家的颜色
            isValid = board[sourceRow][sourceColumn].getPiece().isValidMove(isValidPosition, board);//如果一样则返回True
        }

        System.out.println("Ordinateur, ce coup est-il bon ? : " + isValid);
        return isValid;
    }

    private void updateBoard(String move) {//在棋子更新后更新棋盘
        String[] pieces = move.split(" ");
        String sourcePiece = pieces[0];
        String destinationPiece = pieces[1];
        int sourceColumn = sourcePiece.charAt(1) - 97;
        int sourceRow = 8 - Integer.parseInt(String.valueOf(sourcePiece.charAt(2)));
        int destinationColumn = destinationPiece.charAt(1) - 97;
        int destinationRow = 8 - Integer.parseInt(String.valueOf(destinationPiece.charAt(2)));

        board[sourceRow][sourceColumn].movePiece(board[destinationRow][destinationColumn]);
    }

    private void switchPlayer() {//用来改变currentPlayer的值
        if (currentPlayer == players[0]) {//进行交换
            currentPlayer = players[1];
        } else if (currentPlayer == players[1]) {
            currentPlayer = players[0];
        }
    }
}
