public class RubikRight extends Rubik {

    public RubikRight(Rubik rubik) {
        super(rubik);
    }

    @Override
    public Rubik left() {
        return super.rightView().left().leftView();
    }

    @Override
    public Rubik right() {
        return super.rightView().right().leftView();
    }

    @Override
    public Rubik half() {
        return super.rightView().half().leftView();
    }
        
}
