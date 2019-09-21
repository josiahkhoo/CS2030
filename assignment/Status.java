enum Status {
    
    ARRIVES(1) {
        @Override
        public String getMessage() {
            return new String("arrives");
        } 
    },
    WAITS(2) {
        @Override
        public String getMessage() {
            return new String("waits to be served by");
        }
    },
    SERVED(3) {
        @Override
        public String getMessage() {
            return new String("served by");
        }
    },
    DONE(4) {
        @Override
        public String getMessage() {
            return new String("done serving by");
        }
    },
    LEAVES(5) {
        @Override
        public String getMessage() {
            return new String("leaves");
        }
    };

    private final int value;
    
    public abstract String getMessage();
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

            

