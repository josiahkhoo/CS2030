public class RubikBack extends Rubik {

    public RubikBack(Rubik rubik) {
        super(rubik);
    }

    @Override
    public Rubik left() {
        return super.backView().left().backView();
    }

    @Override
    public Rubik right() {
        return super.backView().right().backView();
    }

    @Override
    public Rubik half() {
        return super.backView().half().backView();
    }
        
}
