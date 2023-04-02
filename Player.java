public class Player {
    private String name;
    private int color;

    public void init(String name, int color) {//将收到的名字和颜色赋值
        this.name = name;
        this.color = color;
    }

    public int getColor(){

        return this.color;
    }

    public String getName() {

        return this.name;
    }

}
