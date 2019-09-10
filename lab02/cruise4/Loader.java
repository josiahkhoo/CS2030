class Loader {
    protected final int identifier;
    protected Cruise servingCruise;

    public Loader(int identifier) {
        this.identifier = identifier;
        this.servingCruise = null;
    }

    public Loader(int identifier, Cruise cruise) {
        this.identifier = identifier;
        this.servingCruise = cruise;
    }

    public Loader serve(Cruise cruise) {
        if (this.servingCruise == null) {
            return new Loader(this.identifier, cruise);
        } else if (this.servingCruise.getServiceCompletionTime() <= 
                cruise.getArrivalTime()) {
            return new Loader(this.identifier, cruise); 
        } else {
            return null;
        }
    }
        
    public Cruise getCruise() {
        return this.servingCruise;
    }
        
    @Override
    public String toString() {
        if (this.servingCruise == null) {
            return String.format("Loader %d", this.identifier);
        } else {
            return String.format("Loader %d serving ", this.identifier) + 
                     this.servingCruise.toString();
        }
    }
    
}

