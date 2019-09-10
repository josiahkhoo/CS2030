import java.util.List;
import java.util.ArrayList;

public class Rubik implements SideViewable, Cloneable {
    private Face top;
    private Face left;
    private Face front;
    private Face right;
    private Face down;
    private Face back;

    public Rubik(int[][][] grid) {
        for (int i = 0; i < 6; i++) {
            Face face = new Face(grid[i]);
            switch (i) {
                case 0:
                    top = face;
                    break;
                case 1:
                    left = face;
                    break;
                case 2:
                    front = face;
                    break;
                case 3:
                    right = face;
                    break;
                case 4:
                    down = face;
                    break;
                case 5:
                    back = face;
                    break;
            }
        }
    }

    public Rubik left() {
        int[][] gridFront = this.front.clone().right().toIntArray();
        int[][] gridTop = this.top.clone().toIntArray();    
        int[][] gridLeft = this.left.clone().toIntArray();
        int[][] gridRight = this.right.clone().toIntArray();
        int[][] gridDown = this.down.clone().toIntArray();
        int[][] gridBack = this.back.clone().toIntArray();

        List<Integer> faceSides = new ArrayList<>();
        //add top to faceSides
        for (int i = 0; i <= 2; i++) {
            faceSides.add(gridTop[2][i]);
        }
        //add right to faceSides
        for (int i = 0; i <= 2; i++) {
            faceSides.add(gridRight[i][0]);
        }
        //add bottom to faceSides
        for (int i = 0; i <= 2; i++) {
            faceSides.add(gridDown[0][i]);
        }
        //add left to faceSides
        for (int i = 0; i <= 2; i++) {
            faceSides.add(gridLeft[i][2]);
        }
        System.out.println(faceSides);
        //rotate faceSides clockwise
        for (int i = 0; i <= 2; i++) {
            int current = faceSides.remove(0);
            faceSides.add(current);
        }
        System.out.println(faceSides);
        for (int i = 0; i <= 11; i++) {
            switch (i) {
                case 0: case 1: case 2:
                    gridTop[2][i] = faceSides.get(i);
                    break;
                case 3: case 4: case 5:
                    gridRight[5-i][0] = faceSides.get(i);
                    break;
                case 6: case 7: case 8:
                    gridDown[0][i-6] = faceSides.get(i);
                    break;
                case 9: case 10: case 11:
                    gridLeft[11-i][2] = faceSides.get(i);
                    break;
            }
        }
        return new Rubik(new int[][][]{gridTop, gridLeft, gridFront, gridRight, gridDown, gridBack});
    }

    public Rubik right() {
        return this.left().left().left();
    }

    public Rubik half() {
        return this.left().left();
    }

    public Rubik rightView() {
        int[][] gridFront = this.right.clone().toIntArray();
        int[][] gridTop = this.top.clone().right().toIntArray();
        int[][] gridLeft = this.front.clone().toIntArray();
        int[][] gridRight = this.back.clone().toIntArray();
        int[][] gridDown = this.down.clone().left().toIntArray();
        int[][] gridBack = this.left.clone().toIntArray();

        return new Rubik(new int[][][]{gridTop, gridLeft, gridFront, gridRight, gridDown, gridBack});
    }

    public Rubik leftView() {
        return this.rightView().rightView().rightView();
    }

    public Rubik backView() {
        return this.rightView().rightView();
    }

    public Rubik frontView() {
        return this.rightView().rightView().rightView().rightView();
    }

    public Rubik upView() {
        int[][] gridFront = this.top.clone().toIntArray();
        int[][] gridTop = this.back.clone().toIntArray();
        int[][] gridLeft = this.left.clone().right().toIntArray();
        int[][] gridRight = this.right.clone().left().toIntArray();
        int[][] gridDown = this.front.clone().toIntArray();
        int[][] gridBack = this.down.clone().toIntArray();
        
        return new Rubik(new int[][][]{gridTop, gridLeft, gridFront, gridRight, gridDown, gridBack});
    }

    public Rubik downView() {
        return this.upView().upView().upView();
    }

    public Rubik clone() {
        return this.frontView();
    }

    @Override
    public String toString() {
        int[][] gridFront = this.front.clone().right().toIntArray();
        int[][] gridTop = this.top.clone().toIntArray();    
        int[][] gridLeft = this.left.clone().toIntArray();
        int[][] gridRight = this.right.clone().toIntArray();
        int[][] gridDown = this.down.clone().toIntArray();
        int[][] gridBack = this.back.clone().toIntArray();

        StringBuilder output = new StringBuilder();
        //...Top...
        for (int i = 0; i <= 2; i++) {
            output.append("\n");
            output.append("......");
            for (int j = 0; j <= 2; j++) {
                if (gridTop[i][j] < 10) {
                    output.append(0);
                }
                output.append(gridTop[i][j]);
            }
            output.append("......");
        }
        //LeftFrontRight
        for (int i = 0; i <= 2; i++) {
            output.append("\n");
            for (int j = 0; j <= 8; j++) {
                switch (j) {
                    case 0: case 1: case 2:
                        if (gridLeft[i][j] < 10) {
                            output.append(0);
                        }
                        output.append(gridLeft[i][j]);
                        break;
                    case 3: case 4: case 5:
                        if (gridFront[i][j-3] < 10) {
                            output.append(0);
                        }
                        output.append(gridFront[i][j-3]);
                        break;
                    case 6: case 7: case 8:
                        if (gridRight[i][j-6] < 10) {
                            output.append(0);
                        }
                        output.append(gridRight[i][j-6]);
                        break;
                }
            }
        }
        //Bottom
        for (int i = 0; i <= 2; i++) {
            output.append("\n");
            output.append("......");
            for (int j = 0; j <= 2; j++) {
                if (gridDown[i][j] < 10) {
                    output.append(0);
                }
                output.append(gridDown[i][j]);
            }
            output.append("......");
        }
        //Back        
        for (int i = 0; i <= 2; i++) {
            output.append("\n");
            output.append("......");
            for (int j = 0; j <= 2; j++) {
                if (gridBack[i][j] < 10) {
                    output.append(0);
                }
                output.append(gridBack[i][j]);
            }
            output.append("......");
        }
        return output.toString();
    }
}
                        


                


