class BigCruise extends Cruise {
    
    public BigCruise(String identifier, int arrivalTime, 
            int numLoadersRequired, int serviceTimeRequired) {
        super(identifier, arrivalTime);
        this.serviceTimeRequired = serviceTimeRequired;
        this.numLoadersRequired = numLoadersRequired;
    }
}
