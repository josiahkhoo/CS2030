class Main {
    public static void main(String args[]) {
        Face f = new Face(new int[][]{{1,2,3},{4,5,6},{7,8,9}});
        f.toString();
        new Face(new int[][]{{1,2,3},{4,5,6},{7,8,9}}).clone();
        new Face(new int[][]{{1,2,3},{4,5,6},{7,8,9}}).right();
        new Face(new int[][]{{1,2,3},{4,5,6},{7,8,9}}).left();
        new Face(new int[][]{{1,2,3},{4,5,6},{7,8,9}}).half();
        new Face(new int[][]{{1,2,3},{4,5,6},{7,8,9}}).toIntArray();
    }
}
