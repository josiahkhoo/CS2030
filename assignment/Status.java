enum Status {
    
    ARRIVES(1),
    SERVED(2),
    LEAVES(3);

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
                string = "served";
                break;
            case 3:
                string = "leaves";
                break;
            default:
        }
        return string;
    }
}

            

