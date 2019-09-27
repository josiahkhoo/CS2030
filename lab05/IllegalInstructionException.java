class IllegalInstructionException extends Exception {

    private final String message;
    
    public IllegalInstructionException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
