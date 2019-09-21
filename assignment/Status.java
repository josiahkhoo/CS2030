enum Status {
    
    ARRIVES(1),
    WAITS(2),
    SERVED(3),
    DONE(4),
    LEAVES(5);

    private final int value;
    
    /** Constructor to initialise status when declared based on constants */
    Status(int value) {
        this.value = value;
    }
    
    /** Getter to get value of status */
    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        String string = new String();
        switch(value) {
            case 1:
                string = "arrives";
                break;
            case 2:
                string = "waits";
                break;
            case 3:
                string = "served";
                break;
            case 4:
                string = "done";
                break;
            case 5:
                string = "leaves";
                break;
            default:
                break;
        }
        return string;
    }
}

            

