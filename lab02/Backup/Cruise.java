class Cruise {
    private String identifier;
    private int arrivalTime;
    protected int serviceTimeRequired;
    protected int numLoadersRequired;
    protected int numLoadersRemaining;

    public Cruise(String identifier, int arrivalTime) {
        this.arrivalTime = arrivalTime;
        this.identifier = identifier;
        this.serviceTimeRequired = 30;
        this.numLoadersRequired = 1;
        this.numLoadersRemaining = this.numLoadersRequired;
    }

    public int getArrivalTime() {
        int arrivalMinutes;
        if (this.arrivalTime >= 100) {
            arrivalMinutes = Math.floorDiv(this.arrivalTime, 100) * 60 +
                    this.arrivalTime % 100;
        } else {
            arrivalMinutes = this.arrivalTime;
        }
        return arrivalMinutes;
    }
    
    public void setNumLoadersRemaining(int n) {
        this.numLoadersRemaining = n;
    }

    public int getNumLoadersRemaining() {
        return this.numLoadersRemaining;
    }

    public int getNumLoadersRequired() {
        return this.numLoadersRequired;
    }

    public int getServiceCompletionTime() {
        int arrivalMinutes = this.getArrivalTime();
        int completionMinutes = arrivalMinutes + this.serviceTimeRequired;
        return completionMinutes;
    }

    @Override
    public String toString() {
        return this.identifier + String.format("@%04d", this.arrivalTime);
    }
}

   
