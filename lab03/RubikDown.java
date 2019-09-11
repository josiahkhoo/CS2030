public class RubikDown extends Rubik {

    public RubikDown(Rubik rubik) {
        super(rubik);
    }

    @Override
    public Rubik left() {
        return super.downView().left().upView();
    }

    @Override
    public Rubik right() {
        return super.downView().right().upView();
    }

    @Override
    public Rubik half() {
        return super.downView().half().upView();
    }
        
}
