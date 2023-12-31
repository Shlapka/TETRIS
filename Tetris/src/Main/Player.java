package Main;

public class Player {

    private int score; // оцінка гравця
    private int level; // рівень гравця
    private int linesCleared;  //кількість рядків, які гравець заповнив

    public Player(int score, int level) { // Конструктор класу
        this.score = score;
        this.level = level;
    }

    public int getScore() {
        return score;
    } // Метод, що повертає очки гравця

    public int getLevel() {
        return level;
    } // Метод, що повертає рівень гравця

    public void clearedLine() { // Метод, який збільшує кількість заповнених рядків
        linesCleared++;
        if (linesCleared % 3 == 0) {
            if (level < 9) {
                level++;
            }
        }
    }

    public void addScore(int rowCompleted) { // Метод, який додає очки гравцю в залежності від кількості заповнених рядків
        switch (rowCompleted) {
            case 1:
                score += 40 * (level + 1);
                break;
            case 2:
                score += 100 * (level + 1);
                break;
            case 3:
                score += 300 * (level + 1);
                break;
            case 4:
                score += 1200 * (level + 1);
                break;
            default:
                break;
        }
    }

    // Метод, який додає 1 до очків гравця
    public void addScore() {
        score += 1;
    }

}
//Клас Player містить інформацію про гравця та його статистику. Він містить поля, що відображають оцінку гравця, рівень гравця та кількість рядків, які гравець заповнив.
//
//Конструктор класу приймає початкову оцінку та рівень гравця і встановлює їх значення відповідним чином.
//
//Метод getScore() повертає поточну оцінку гравця, а метод getLevel() повертає його рівень.
//
//Метод clearedLine() збільшує кількість заповнених рядків та, якщо гравець заповнив кількість рядків, кратну 10, то рівень гравця збільшується на 1.
//
//Метод addScore(int rowCompleted) додає очки гравцю в залежності від кількості заповнених рядків. Він використовує оператор switch для визначення кількості очків, що додаються, в залежності від кількості заповнених рядків.
//
//Метод addScore() додає 1 до очків гравця.
//
//Цей клас є важливим елементом в грі Tetris, оскільки він зберігає інформацію про статистику гравця та відповідає за додавання очків і збільшення рівня гравця.
