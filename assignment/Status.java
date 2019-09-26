/**
 * This is a Status enum that will be used in the customer class.
 */
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

    /** 
     * Creates a Status with the specified value.
     * @param   value   value corresponding to status
     */ 
    Status(int value) {
        this.value = value;
    }
  
    /**
     * this method returns the value of this status.
     * @return  {@link  Status#value}
     */
    public int getValue() {
        return this.value;
    }
    
    /**
     * This method overrides the toString() method of Object and returns a
     * string corresponding to this status.
     * @return  String
     */
    @Override
    public String toString() {
        String string = new String();
        switch (value) {
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

            

