package Main;

import java.util.Random;

public class Shapes {// Оголошення двовимірних масивів, що відповідають різним фігурам

    public static int[][] zBlock = {{1, 1, 0},
            {0, 1, 1}};

    public static int[][] lBlock = {{0, 0, 0, 2},
            {2, 2, 2, 2}};

    public static int[][] oBlock = {{3, 3},
            {3, 3}};

    public static int[][] sBlock = {{0, 4, 4},
            {4, 4, 0}};

    public static int[][] lineBlock = {{5, 5, 5, 5}};

    public static int[][] jBlock = {{6, 0, 0, 0},
            {6, 6, 6, 6}};


    public static int[][] tBlock = {{0, 7, 0},
            {7, 7, 7}};

    public static int[][] randomBlock() {// Метод, який повертає випадковий двовимірний масив, що відповідає фігурі
        int random;
        Random r = new Random();
        random = r.nextInt(7);
        if (random == 0) {
            return lineBlock;
        } else if (random == 1) {
            return jBlock;
        } else if (random == 2) {
            return lBlock;
        } else if (random == 3) {
            return oBlock;
        } else if (random == 4) {
            return tBlock;
        } else if (random == 5) {
            return sBlock;
        } else {
            return zBlock;
        }
    }

    public static int getColor(int[][] shape) { // Метод, який повертає значення кольору фігури
        int section = 0;
        for (int i = 0; i < shape[0].length; i++) {
            if (shape[0][i] > 0) {
                section = shape[0][i];
            }
        }
        return section;
    }

}
//Клас "Shapes" містить в собі оголошення двовимірних масивів, які відповідають різним фігурам в грі Тетріс.
// Кожна фігура представлена у вигляді матриці чисел, де кожне число вказує на наявність (1) або відсутність (0) блоку відповідного кольору.
//
//Метод "randomBlock" повертає випадкову фігуру, використовуючи клас Random. Метод перевіряє випадкове число і вибирає матрицю, що відповідає випадковій фігурі.
//
//Метод "getColor" повертає значення кольору фігури. Метод перевіряє перший рядок матриці фігури, знаходить перше ненульове число та повертає його значення як індекс кольору.
//
//Загалом, клас "Shapes" містить необхідні фігури та методи для отримання випадкової фігури та кольору фігури, що допомагає при розробці гри Тетріс.