public class RubikUp extends Rubik {

    public RubikUp(Rubik rubik) {
        super(rubik);
    }

    @Override
    public Rubik left() {
        return super.upView().left().downView();
    }

    @Override
    public Rubik right() {
        return super.upView().right().downView();
    }

    @Override
    public Rubik half() {
        return super.upView().half().downView();
    }
        
}
