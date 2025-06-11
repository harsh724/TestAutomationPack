package rough;

public class Rough2 extends Rough{
    private final int i;
    int maxNum = 190;
    public Rough2(int i) {
        this.i = i;
    }

    private int privateMethod(){
        return i;
    }
    public boolean publicMethod(){
        System.out.println(new Rough2(3).privateMethod());
        System.out.println(super.maxNum);
        return true;
    }
}
