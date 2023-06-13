package Main;

import java.awt.*;

public class Game {
    public static final int WIDTH = 600; // ширина вікна меню
    public static final int HEIGHT = 500; // висота вікна меню
    private Thread tickThread; // потік гри
    public boolean running; // гра виконується чи ні
    private Player player; // гравець
    public Point mousePoint; // координати курсора

    private int cellSize = 25; // розмір однієї клітинки на ігровому полі
    public static int gridWidth = 10; // ширина ігрового поля
    public static int gridHeight = 16; // висота ігрового поля
    private int[][] gamePos = new int[10][16]; // зберігаємо позиції блоків, що вже "упали"
    public boolean hasCreated = false; // чи є поточний блок у грі
    public Piece controlPiece; // поточний блок
    private Piece nextPiece; // наступний блок
    private ImageLoader square; // зображення для блоків
    private int speed = 1100; // швидкість блоків
    public boolean hasLost = false; // чи програв гравець
    public Rectangle[] buttons = {new Rectangle(WIDTH / 3 + 135, 165, 200, 70),
            new Rectangle(WIDTH / 3 + 135, 275, 200, 70)};

    public Game() {
        init();
    }

    public void start() { // запуск гри
        clearGame(); // очищення ігрового поля
        tickThread = new Thread(new Clock()); // створення потоку гри
        tickThread.start(); // запуск потоку гри
        running = true; // гра виконується
    }

    private void init() { // ініціалізація гри
        player = new Player(0, 0); // створення гравця
        square = new ImageLoader(ImageLoader.squarePath); // завантаження зображення блоків
        for (int i = 0; i < gamePos.length; i++) {
            for (int j = 0; j < gamePos[0].length; j++) {
                gamePos[i][j] = 0; // заповнення масиву значеннями 0
            }
        }
        nextPiece = new Piece(4, 0, Shapes.randomBlock()); // створення наступного блоку
        mousePoint = new Point(0, 0); // початкові координати курсора
    }

    public void tick() {
        if (!hasCreated) { // Якщо ігрове поле ще не було створено
            checkRowCompletion(); // Перевіряємо, чи є рядки, які можна видалити
            checkLoss(); // Перевіряємо, чи гравець програв
            if (!hasLost) { // Якщо гравець ще не програв
                controlPiece = nextPiece; // Фігура, яку гравець керує, стає фігурою, що йде після наступної
                nextPiece = new Piece(4, 0, Shapes.randomBlock()); // Створюємо наступну фігуру
                updateCurrentPiece(); // Оновлюємо поточну фігуру на ігровому полі
                hasCreated = true; // Поле створено
            }
        } else { // Якщо поле вже створено
            if (!hasLost) { // Якщо гравець ще не програв
                updateBlockPosition(false, false); // Оновлюємо позицію фігури на ігровому полі
            }
        }
    }

    public void render(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT); // Фоновий квадрат всього поля
        g.setColor(Color.white);
        g.fillRect(50, 20, 250, HEIGHT - 100); // Квадрат, що містить головне ігрове поле
        g.setColor(new Color(255, 0, 0, 20));
        //Малюємо червону сітку на фоні
        for (int i = 0; i < gridWidth; i++) { //ширина сітки
            for (int j = 0; j < gridHeight; j++) { //висота
                g.drawRect(i * cellSize + 50, j * cellSize + 20, cellSize, cellSize); // Окантовка кожної клітинки сітки
            }
        }
        g.setColor(Color.orange);
        g.drawString("Наступна фігура", (10 + gridWidth * cellSize) + 52, 35);
        g.setColor(Color.white);
        g.fillRect((50 + gridWidth * cellSize) + 50, 45, 100, 100);
        g.setColor(Color.red);
        // Малюємо сітку
        for (int i = 0; i < 4; i++) { // проходимось по рядках
            for (int j = 0; j < 4; j++) { // проходимось по стовпцях
                g.drawRect((i * cellSize) + (50 + gridWidth * cellSize) + 50, j * cellSize + 45, cellSize, cellSize); // малюємо прямокутники які утворюють клітинки сітки
            }
        }
// Малюємо наступний блок
        for (int i = 0; i < nextPiece.width(); i++) { // проходимось по рядках блоку
            for (int j = 0; j < nextPiece.height(); j++) { // проходимось по стовпцях блоку
                if (nextPiece.getShape()[i][j] != 0) { // якщо значення елемента не дорівнює 0, то малюємо його
                    g.drawImage(square.getSubImage(nextPiece.getShape()[i][j]), (i * cellSize) + (50 + gridWidth * cellSize) + 50 + cellSize, j * cellSize + 45, cellSize, cellSize, null); // малюємо квадрат блоку на відповідній позиції
                    g.setColor(Color.BLACK); // встановлюємо колір обводки
                    g.drawRoundRect((i * cellSize) + (50 + gridWidth * cellSize) + 50 + cellSize, j * cellSize + 45, cellSize, cellSize, 1, 1); // малюємо округлі обводки навколо блоку
                }
            }
        }
        g.setColor(Color.blue);
        g.fillRoundRect(Controller.WIDTH / 3 + 125, 150, 250, 275, 25, 25); // малюємо блакитний прямокутник
        /*Малюємо кнопки*/
        g.setColor(new Color(144, 157, 168)); // встановлюємо колір фону
        for (int i = 0; i < buttons.length; i++) { // проходимось по масиву кнопок
            if (buttons[i].contains(mousePoint)) { // якщо мишка знаходиться над кнопкою, то малюємо її з заповненням
                g.fillRoundRect(buttons[i].x, buttons[i].y, buttons[i].width, buttons[i].height, 25, 25);
            }
        }
        g.setColor(Color.red);
        // Рисуємо кнопки
        for (int i = 0; i < buttons.length; i++) {
            ((Graphics2D) g).setStroke(new BasicStroke(3)); // встановлюємо ширину лінії для малювання
            g.drawRoundRect(buttons[i].x, buttons[i].y, buttons[i].width, buttons[i].height, 25, 25); // малюємо кнопку з заокругленими кутами
        }
        ((Graphics2D) g).setStroke(new BasicStroke(0)); // встановлюємо тонку лінію
        Font font = new Font("Helvetica", Font.BOLD, 25); // встановлюємо шрифт
        g.setFont(font);
        g.setColor(Color.ORANGE); // встановлюємо колір
        g.drawString("Нова гра", WIDTH / 3 + 180, 200); // малюємо "Нова гра"
        g.drawString("Меню", WIDTH / 3 + 200, 310); // малюємо "Меню"
        g.drawString("Рівень: " + player.getLevel(), WIDTH / 3 + 150, 375); // малюємо "Рівень" та рівень гравця
        g.drawString("Рахунок: " + player.getScore(), WIDTH / 3 + 150, 400); // малюємо "Рахунок" та рахунок гравця
        g.setColor(Color.white); // змінюємо колір на білий
        /*End Drawing Scores and Details*/

        // малюємо блоки
        for (int i = 0; i < gridWidth; i++) { // ширина сітки
            for (int j = 0; j < gridHeight; j++) { // висота сітки
                g.drawImage(square.getSubImage(gamePos[i][j]), i * cellSize + 50, j * cellSize + 20, cellSize, cellSize, null); // малюємо блок
                if (gamePos[i][j] > 0) {
                    g.setColor(Color.BLACK); // встановлюємо чорний колір
                    g.drawRoundRect(i * cellSize + 50, j * cellSize + 20, cellSize, cellSize, 1, 1); // малюємо рамку навколо блоку
                }
            }
        }
    }

    public void updateBlockPosition(boolean update, boolean extraPoint) { // Метод для оновлення позиції блоку
        boolean canUpdate = true;
        // перевірка, чи існує блок для управління
        if (controlPiece != null) {
            // перевірка, чи блок знаходиться не на найнижчому рівні
            if (controlPiece.getY() + controlPiece.height() < gridHeight) {
                // перевірка всіх горизонтальних ліній блоку
                for (int i = 0; i < controlPiece.width(); i++) {
                    if (canUpdate) {
                        // якщо на останній лінії блоку є заповнені комірки, то перевіряємо, чи є під ними порожні комірки в грі
                        if (controlPiece.getShape()[i][controlPiece.height() - 1] > 0) {
                            if (gamePos[controlPiece.getX() + i][controlPiece.getY() + controlPiece.height()] > 0) {
                                canUpdate = false;
                                hasCreated = false;
                            }
                            // якщо на передостанній лінії блоку є заповнені комірки, то перевіряємо, чи є під ними порожні комірки в грі
                        } else if (controlPiece.getShape()[i][controlPiece.height() - 2] > 0) {
                            if (gamePos[controlPiece.getX() + i][controlPiece.getY() + controlPiece.height() - 1] > 0) {
                                canUpdate = false;
                                hasCreated = false;
                            }
                            // якщо на лінії, що на дві комірки вище передостанньої лінії, є заповнені комірки, то перевіряємо, чи є під ними порожні комірки в грі
                        } else if (controlPiece.getShape()[i][controlPiece.height() - 3] > 0) {
                            if (gamePos[controlPiece.getX() + i][controlPiece.getY() + controlPiece.height() - 2] > 0) {
                                canUpdate = false;
                                hasCreated = false;
                            }
                            // якщо на лінії, що на три комірки вище передостанньої лінії, є заповнені комірки, то перевіряємо, чи є під ними порожні комірки в грі
                        } else if (controlPiece.getShape()[i][controlPiece.height() - 4] > 0) { // якщо так, то перевіряємо, чи є порожні комірки під ними
                            if (gamePos[controlPiece.getX() + i][controlPiece.getY() + controlPiece.height() - 3] > 0) { // якщо немає, то не можна оновлювати блок, що керується
                                canUpdate = false;
                                hasCreated = false;
                            }
                        } else { // якщо зустрічаємо блок з цілою рядкою порожніх комірок, то щось пішло не так
                            System.out.println("Apparently this block(" + controlPiece + ") has a whole row of empty 0.. Error in updateBlockPos");
                        }

                    }
                }
                if (canUpdate) { // якщо можна оновити блок
                    removeCurrentPiece(); // видаляємо поточний блок з положення, в якому він знаходиться
                    if (update) {
                        controlPiece.updateY();
                    }
                    updateCurrentPiece(); // оновлюємо блок у новому положенні
                    if (extraPoint) {
                        player.addScore(); // якщо блок зайшов на додатковий рівень, то додаємо гравцеві бонусний бал
                    }
                }
            } else { // якщо не можна оновити блок, то встановлюємо hasCreated в значення false, щоб згодом створити новий блок
                hasCreated = false;
            }
        }
    }

    public void moveLeft() {
        boolean canMove = true; // Ініціалізація змінної canMove яка показує, чи можна здійснити рух вліво
        if (controlPiece.getX() > 0) { // Перевірка чи фігура не знаходиться на крайньому лівому полі ігрового поля
            for (int i = 0; i < controlPiece.height(); i++) { // Цикл по висоті фігури
// Якщо блок фігури не знаходиться безпосередньо зліва від блоку на грі, то можна здійснити рух вліво
                if (gamePos[controlPiece.getX() - 1][controlPiece.getY() + i] > 0 && controlPiece.getShape()[0][i] > 0) {
                    canMove = false; // Якщо фігуру не можна здійснити рух вліво, то змінна canMove стає false
                }
            }
            if (canMove) { // Якщо можна здійснити рух вліво, то змінюємо позицію фігури на грі
                removeCurrentPiece();
                controlPiece.setX(controlPiece.getX() - 1);
                updateCurrentPiece();
            }
        }
    }

    // Оголошення методу moveRight() без параметрів та повернення значення

    public void moveRight() {
// Ініціалізація логічної змінної canMove як true
        boolean canMove = true;
// Перевірка чи можна здійснити рух вправо, перевіряючи чи права сторона блоку не виходить за межі поля гри
        if (controlPiece.getX() + controlPiece.width() < gridWidth) {
// Проходимо по всіх елементах у висоту блоку
            for (int i = 0; i < controlPiece.height(); i++) {
// Якщо блок під індексом (X + ширина блоку, Y + і) більше 0 та блок в controlPiece під індексом (ширина блоку - 1, і) більше 0, то рух не можливий
                if (gamePos[controlPiece.getX() + controlPiece.width()][controlPiece.getY() + i] > 0 && controlPiece.getShape()[controlPiece.width() - 1][i] > 0) {
                    canMove = false;
                }
            }
// Якщо можна здійснити рух
            if (canMove) {
// Видаляємо поточний блок
                removeCurrentPiece();
// Зміщуємо блок вправо
                controlPiece.setX(controlPiece.getX() + 1);
// Оновлюємо положення блоку на полі гри
                updateCurrentPiece();
            }
        }
    }

    // Метод, що видаляє поточний елемент гри
    public void removeCurrentPiece() {
// Перевірка на те, чи елемент перебуває у межах гри
        if (controlPiece.getY() >= 0) {
// Прохід по елементу у формі матриці
            for (int i = 0; i < controlPiece.height(); i++) {
                for (int j = 0; j < controlPiece.width(); j++) {
// Якщо елемент не є пустим, то видаляємо його з позиції на ігровому полі
                    if (controlPiece.getShape()[j][i] != 0) {
                        gamePos[controlPiece.getX() + j][controlPiece.getY() + i] = 0;
                    }
                }
            }
        }
    }

    // Перевірка на заповненість рядка
    public void checkRowCompletion() {
        int rowCompletion = 0; // Лічильник заповнених рядків
        int count = 0; // Лічильник кількості заповнених комірок у рядку
        for (int i = 0; i < gridHeight; i++) { // Ітерація по рядках
            count = 0;
            for (int j = 0; j < gridWidth; j++) { // Ітерація по комірках рядка
                if (gamePos[j][i] > 0) { // Якщо комірка заповнена
                    count++; // Додаємо до лічильника заповнених комірок
                }
            }
            if (count == gridWidth) { // Якщо весь рядок заповнений
                for (int j = 0; j < gridWidth; j++) { // Очищуємо комірки рядка
                    gamePos[j][i] = 0;
                }
                player.clearedLine(); // Збільшуємо лічильник очищених рядків у гравця
                moveDownRows(i); // Переміщуємо усі рядки вище очищеного рядка вниз
                rowCompletion++; // Додаємо до лічильника заповнених рядків
            }
        }
        if (rowCompletion > 0) { // Якщо є заповнені рядки
            player.addScore(rowCompletion); // Додаємо до суми балів гравця кількість очищених рядків
        }
    }

    // Перевіряємо, чи відбувся програш у грі
    public void checkLoss() {
// Якщо блок потрапив у верхні дві рядки, то гравець програв
        if (gamePos[4][0] > 0 || gamePos[5][0] > 0) {
// Встановлюємо прапорець "програв" в true
            hasLost = true;
// Зупиняємо гру
            running = false;
// Зберігаємо останній рахунок гравця
            Controller.recentScore = player.getScore();
// Переходимо до екрану кінцевого результату
            Controller.switchClasses(Controller.STATE.ENDSCREEN);
        }
    }

    // Зміщуємо рядки вниз
    public void moveDownRows(int height) {
// Починаємо знизу і працюємо догори
        for (int i = height; i > 1; i--) {
// Для кожної клітинки у рядку
            for (int j = 0; j < gridWidth; j++) {
// Замінюємо значення поточної клітинки значенням попередньої клітинки
                gamePos[j][i] = gamePos[j][i - 1];
            }
        }
    }

    // Метод для оновлення поточної фігури в грі
    public void updateCurrentPiece() {
        for (int i = 0; i < controlPiece.height(); i++) {// цикл по висоті фігури
            for (int j = 0; j < controlPiece.width(); j++) { // цикл по ширині фігури
                if (controlPiece.getShape()[j][i] != 0) {
                    gamePos[controlPiece.getX() + j][controlPiece.getY() + i] = controlPiece.getShape()[j][i];
                }
            }
        }
    }

    // Метод для обертання фігури
    public void rotatePiece() {
        removeCurrentPiece(); // видалення попередньої фігури зі старої позиції
        controlPiece.rotateSelf(gamePos); // обертання фігури та оновлення позиції
        updateCurrentPiece(); // оновлення поточної фігури в грі
    }

    // Метод, що очищує гру
    public void clearGame() {
// Створення нового гравця в початковому положенні
        player = new Player(0, 0);
// Змінна, що показує чи програв гравець
        hasLost = false;
// Змінна, що показує чи створена нова фігура
        hasCreated = false;
// Створення нової фігури для наступного ходу
        nextPiece = new Piece(4, 0, Shapes.randomBlock());
// Цикл, який обнуляє всі значення на ігровому полі
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                gamePos[j][i] = 0;
            }
        }
    }

    // Внутрішній клас, що відповідає за годинник гри
    private class Clock implements Runnable {
        // Метод, що відтворює логіку годинника
        public void run() {
// Безкінечний цикл, який виконується поки гра продовжується
            while (running == true) {
                try {
// Затримка на потрібну кількість мілісекунд відповідно до складності гри
                    Thread.sleep(speed - (player.getLevel() * 100));

                } catch (InterruptedException e) {
                    // Переривання циклу у випадку виключення
                    break;
                }
                // Перевірка на те, що гравець ще не програв
                if (!hasLost) {
                    // Оновлення позиції фігури
                    updateBlockPosition(true, false);
                }
                // Перевірка на те, що гра продовжується
                if (running == false) break;
            }
        }
    }
}
//Цей клас коду являє собою основний клас гри "Тетріс" з графічним інтерфейсом користувача.
// В ньому оголошені основні змінні, такі як розміри гри, змінна, що показує, чи запущена гра, координати миші, змінні, які відповідають за розміри блоків і ігрового поля, а також змінні, які відповідають за поточний блок, наступний блок і ігрову позицію.
// Клас також відповідає за рендеринг гри і містить методи для переміщення блоків та перевірки виконання рядків.
//
//Клас містить конструктор, який викликає метод init(), який ініціалізує змінні, встановлює значення для ігрового поля та наступного блоку.
//Метод start() викликається для початку гри, він запускає окремий потік, щоб оновлювати ігрову позицію.
// Метод tick() відповідає за перевірку виконання рядків, викликає метод updateCurrentPiece() для оновлення поточного блоку і викликає метод updateBlockPosition() для переміщення блоків.
// Метод render() відповідає за рендеринг ігрового поля та наступного блоку. Він також відповідає за відображення кнопок та інформації про гру.
