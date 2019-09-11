public class RubikLeft extends Rubik {

    public RubikLeft(Rubik rubik) {
        super(rubik);
    }

    @Override
    public Rubik left() {
        return super.leftView().left().rightView();
    }

    @Override
    public Rubik right() {
        return super.leftView().right().rightView();
    }

    @Override
    public Rubik half() {
        return super.leftView().half().rightView();
    }
        
}
