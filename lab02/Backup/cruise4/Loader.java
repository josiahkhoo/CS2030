class Loader {
    protected final int identifier;
    protected Cruise servingCruise;
    protected final Cruise nullCruise;

    public Loader(int identifier) {
        this.identifier = identifier;
        this.nullCruise = new Cruise("NULL", 0);
        this.servingCruise = nullCruise;
    }

    public Loader serve(Cruise cruise) {
        if (this.servingCruise == this.nullCruise) {
            this.servingCruise = cruise;
            cruise.setNumLoadersRemaining(cruise.getNumLoadersRemaining() - 1);
        } else if (this.servingCruise.getServiceCompletionTime() <= 
                cruise.getArrivalTime()) {
            this.servingCruise = cruise;
            cruise.setNumLoadersRemaining(cruise.getNumLoadersRemaining() - 1);
        } else {
            return null;
        }
        return this;
    }
        
    public Cruise getCruise() {
        return this.servingCruise;
    }
        
    @Override
    public String toString() {
        if (this.servingCruise == this.nullCruise) {
            return String.format("Loader %d", this.identifier);
        } else {
            return String.format("Loader %d serving ", this.identifier) + 
                     this.servingCruise.toString();
        }
    }
    
}

