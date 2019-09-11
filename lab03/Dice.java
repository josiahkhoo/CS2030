public class Dice implements SideViewable {
    private String top;
    private String front;
    private String right;
    private String back;
    private String left;
    private String down;

    public Dice() {
        top = "U";
        front = "F";
        right = "R";
        back = "B";
        left = "L";
        down = "D";
    }

    public Dice(String[] unfolded) {
        top = unfolded[0];
        front = unfolded[1];
        right = unfolded[2];
        back = unfolded[3];
        left = unfolded[4];
        down = unfolded[5];
    }
    
    public String[] unfold() {
        return new String[]{top, front, right, back, left, down};
    }

    public Dice upView() {
        String[] unfolded = dice.unfold();
        swap(unfolded[1], unfolded[0], unfolded[3], unfolded[5]);
        return new Dice(unfolded);
    }

    public Dice downView() {
        return this.upView().upView().upView();
    }

    public Dice leftView() {
        String[] unfolded = dice.unfold();
        swap(unfolded[4], unfolded[3], unfolded[2], unfolded[1]);
        return new Dice(unfolded);
    }

    public Dice rightView() {
        return this.leftView().leftView().leftView();
    }

    public Dice backView() {
        return this.backView().backView().backView();
    }
        
    public static void swap(String obj1, String obj2, String obj3, String obj4) {
        String temp = new String();
        temp = obj1;
        obj1 = obj2;
        obj2 = obj3;
        obj3 = obj4;
        obj4 = temp;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("\n");
        output.append(top);
        output.append("   \n");
        output.append(front);
        output.append(right);
        output.append(back);
        output.append(left);
        output.append("\n...");
        output.append(down);
        output.append("\n");
        return output.toString();
    }
}
        
        

