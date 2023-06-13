package Main;

public class Piece {

    private int x; // Положення по X
    private int y; // Положення по Y
    private int[][] shape; // Масив, що зберігає форму фігури

    public Piece(int x, int y, int[][] shape) { // Конструктор класу
        this.x = x;
        this.y = y;
        this.shape = shape;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int width() {
        return this.shape.length;
    } // Метод, що повертає ширину фігури

    public int height() {
        return this.shape[0].length;
    } // Метод, що повертає висоту фігури

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void updateY() {
        this.y = this.y + 1;
    } // Метод, що оновлює положення фігури по Y

    public int[][] getShape() {
        return shape;
    }

    public void rotateSelf(int[][] gamePos) { // Метод, що обертає фігуру
        final int H = shape.length;
        final int W = shape[0].length;
        int[][] temp = new int[W][H];
        for (int r = 0; r < H; r++) { // Перебираємо всі елементи масиву shape
            for (int c = 0; c < W; c++) {
                temp[c][H - 1 - r] = shape[r][c]; // Повертаємо елементи на 90 градусів
            }
        }
        boolean canRotate = true; // чи можна обернути фігуру
        if (this.x + temp.length <= Game.gridWidth && this.y + temp[0].length <= Game.gridHeight) { // Перевіряємо, чи не виходить фігура за межі гри
            for (int i = 0; i < temp.length; i++) {
                for (int j = 0; j < temp[0].length; j++) { // Перевіряємо, чи фігура не перетинається з іншими фігурами
                    if (gamePos[this.x + i][this.y + j] > 0) {
                        canRotate = false;
                    }
                }
            }
        } else {
            canRotate = false;
        }
        if (canRotate) {
            this.shape = temp; // Якщо можна обернути фігуру, замінюємо її форму на temp
        } else {
            System.out.println("Не можна обернути.."); // Якщо неможливо обернути фігуру, то не робимо нічого і виводимо повідомлення в консоль.
        }
    }

}
//
//Клас Piece відповідає за фігури у грі тетріс.
//
//private int x; - зберігає положення фігури по X.
//
//private int y; - зберігає положення фігури по Y.
//
//private int[][] shape; - масив, що зберігає форму фігури.
//
//public Piece(int x, int y, int[][] shape) - конструктор класу, приймає початкові координати і форму фігури.
//
//public int getX() - повертає поточне положення фігури по X.
//
//public int getY() - повертає поточне положення фігури по Y.
//
//public int width() - повертає ширину фігури.
//
//public int height() - повертає висоту фігури.
//
//public void setX(int x) - задає нове положення фігури по X.
//
//public void setY(int y) - задає нове положення фігури по Y.
//
//public void updateY() - оновлює положення фігури по Y на 1 вниз.
//
//public int[][] getShape() - повертає масив, що зберігає форму фігури.
//
//public void rotateSelf(int[][] gamePos) - обертає фігуру на 90 градусів за годинниковою стрілкою, якщо це можливо.
// Приймає масив, що зберігає положення всіх фігур у грі.
// Якщо фігура перетинається з іншими фігурами або виходить за межі поля гри, то обертання не відбувається.