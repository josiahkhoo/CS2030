class RecycledLoader extends Loader {
    
    public RecycledLoader(int identifier) {
        super(identifier);
    }

    @Override
    public RecycledLoader serve(Cruise cruise) {
        if (this.servingCruise == this.nullCruise) {
            this.servingCruise = cruise;
            cruise.setNumLoadersRemaining(cruise.getNumLoadersRemaining() - 1);
        } else if (this.servingCruise.getServiceCompletionTime() + 60  <= 
                cruise.getArrivalTime()) {
            this.servingCruise = cruise;
            cruise.setNumLoadersRemaining(cruise.getNumLoadersRemaining() - 1);
        } else {
            return null;
        }
        return this;
    }

    @Override
    public String toString() {
        if (this.servingCruise == this.nullCruise) {
            return String.format("Loader %d (recycled)", this.identifier);
        } else {
            return String.format("Loader %d (recycled) serving ", this.identifier) + 
                     this.servingCruise.toString();
        }
    }
 
}
 

