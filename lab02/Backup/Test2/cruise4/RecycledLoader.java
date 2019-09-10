class RecycledLoader extends Loader {

    public RecycledLoader(int identifier) {
        super(identifier);
    }

    public RecycledLoader(int identifier, Cruise cruise) {
        super(identifier, cruise);
    }

    @Override
    public RecycledLoader serve(Cruise cruise) {
        if (this.servingCruise == null) {
            return new RecycledLoader(this.identifier, cruise);
        } else if (this.servingCruise.getServiceCompletionTime() + 60 <= 
                cruise.getArrivalTime()) {
            return new RecycledLoader(this.identifier, cruise); 
        } else {
            return null;
        }
    }
    
    @Override
    public String toString() {
        if (this.servingCruise == null) {
            return String.format("Loader %d (recycled)", this.identifier);
        } else {
            return String.format("Loader %d (recycled) serving ", this.identifier) + 
                     this.servingCruise.toString();
        }
    }
}    
    

