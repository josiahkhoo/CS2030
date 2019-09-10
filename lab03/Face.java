public class Face {
    private int[][] grid;

    public Face(int[][] grid) {
        this.grid = grid;
    }

    public Face right() {
        int[][] newGrid = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newGrid[i][j] = this.grid[2 - j][i];
            }
        }
        return new Face(newGrid);
    }

    public Face left() {
        int[][] newGrid = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newGrid[i][j] = this.grid[j][2 - i];
            }
        }
        return new Face(newGrid);
    }

    public Face half() {
        int[][] newGrid = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newGrid[i][j] = this.grid[2 - i][2 - j];
            }
        }
        return new Face(newGrid);
    }

    public int[][] toIntArray() {
        return this.grid;
    }
   
    @Override
    public Face clone() {
        int[][] newGrid = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newGrid[i][j] = this.grid[i][j];
            }
        }
        return new Face(newGrid);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder(); 
        for (int i = 0; i < 3; i++) {
            output.append("\n");
            for (int j = 0; j < 3; j++) {
                if (this.grid[i][j] < 10) {
                    output.append(0);
                }
                output.append(this.grid[i][j]); 
            }
        }
        output.append("\n");
        return output.toString();
    }
}
