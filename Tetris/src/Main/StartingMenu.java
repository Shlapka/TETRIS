package Main;

import java.awt.*;

import java.util.Random;

import javax.swing.JPanel;

public class StartingMenu extends JPanel {
    public static final int WIDTH = 600; // ширина вікна меню
    public static final int HEIGHT = 500; // висота вікна меню
    public Point mousePoint; // координати курсора миші
    private ImageLoader square;// об'єкт класу ImageLoader для завантаження квадратів
    public Thread thread; // потік, який відповідає за час
    Random random; // об'єкт генерації випадкових чисел
    private int spawnCount = 0; // лічильник для підрахунку кількості блоків
    public boolean running = false; // чи відбувається запуск гри
    private int paddingWidth = 20; // розмір відступів для кнопок
    // масив для зберігання кнопок
    public Rectangle[] buttons = {new Rectangle(WIDTH / 2 - 100, 175, 200, 50),  //кнопка "Нова гра"
            new Rectangle(WIDTH / 2 - 100, 225 + 15, 200, 50), // кнопка "Рекорди"
            new Rectangle(WIDTH / 2 - 100, 275 + 30, 200, 50)}; // кнопка "Вихід"
    private Piece[] pieces; // масив для зберігання блоків

    private int speed = 350; // швидкість падіння блоків

    public StartingMenu() { // конструктор класу меню
        mousePoint = new Point(0, 0); // встановлення початкових координат курсора миші
        square = new ImageLoader(ImageLoader.squarePath); // завантаження зображення квадрата
        thread = new Thread(); // створення потоку
    }

    public void init() { // метод для ініціалізації початкових значень
        random = new Random();  // ініціалізація об'єкту генерації випадкових чисел
        running = true; // запуск гри
        pieces = new Piece[15]; // створення масиву блоків
        thread = new Thread(new Clock());  // створення потоку, який відповідає за час
        thread.start();  // запуск потоку

    }

    public void tick() { //Генерує випадковий шанс появи нового блоку.
        int spawnChance;
        spawnChance = random.nextInt(3); // випадково вибираємо шанс появи нового блоку
        // Якщо випадкове число - 0, створюємо новий блок і додаємо його в масив.
        if (spawnChance == 0) {
            if (spawnCount < pieces.length) {
                pieces[spawnCount] = new Piece(random.nextInt(21) * 25, -50, Shapes.randomBlock());
                spawnCount++;
            } else {
                spawnCount = 0;
                pieces[spawnCount] = new Piece(random.nextInt(21) * 25, -50, Shapes.randomBlock());
            }
        }
        // Рухаємо блоки, які вже є на полі, вниз на 25 пікселів.
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] != null) {
                pieces[i].setY(pieces[i].getY() + 25);
            }
        }
    }


    public void render(Graphics g) { // Метод, що оновлює стан гри за кожен кадр. Генерується випадковий шанс появи нового блоку, якщо шанс є, то створюється новий блок. Блоки, які вже є на полі, рухаються вниз на 25 пікселів.
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        int xPos;
        int yPos;
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] != null) { //перевіряє, чи існує блок на даній позиції.
                for (int j = 0; j < pieces[i].getShape().length; j++) {
                    for (int k = 0; k < pieces[i].getShape()[j].length; k++) {
                        if (pieces[i].getShape()[j][k] != 0) {
                            xPos = pieces[i].getX() + k * 25;
                            yPos = pieces[i].getY() + j * 25;
                            g.drawImage(square.getSubImage(Shapes.getColor(pieces[i].getShape())), xPos, yPos, 25, 25, null); // малює блок на екрані за допомогою методу drawImage, використовуючи графічний об'єкт square, який містить всі можливі візуальні представлення блоків, встановлюючи його позицію та розміри.
                        }
                    }
                }
            }
        }
        g.setColor(Color.blue);
        g.fillRoundRect(100, 100, Controller.WIDTH / 3 * 2, Controller.HEIGHT / 3 * 2, 25, 25);
        g.setColor(new Color(144, 157, 168));
        for (int i = 0; i < buttons.length; i++) { // Цикл для кожної кнопки на екрані
            if (buttons[i].contains(mousePoint)) { // Якщо мишка знаходиться над певною кнопкою
                g.fillRoundRect(buttons[i].x, buttons[i].y, buttons[i].width, buttons[i].height, 25, 25);
            }
        }
        g.setColor(Color.red);
        Graphics2D g2d = (Graphics2D) g;
        Stroke oldStroke = g2d.getStroke();

        g2d.setStroke(new BasicStroke(3));

        for (int i = 0; i < buttons.length; i++) { // Відображення прямокутника з закругленими краями для кожної кнопки на екрані
            g2d.drawRoundRect(buttons[i].x, buttons[i].y, buttons[i].width, buttons[i].height, 25, 25);
        }

        g2d.setStroke(oldStroke);
        g.drawImage(new ImageLoader(ImageLoader.tetrisPath).getImage(), WIDTH / 2 - 80, 60, 160, 105, null); // Відображення зображення гри Тетріс у певних координатах та з певною шириною та висотою
        g.setColor(Color.orange);
        Font font = new Font("Helvetica", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("Нова гра", centerText(g, "Нова гра"), buttons[0].y + paddingWidth + (paddingWidth / 2));
        g.drawString("Рекорди", centerText(g, "Рекорди"), buttons[1].y + paddingWidth + (paddingWidth / 2)); //Виводить текст "Нова гра","Рекорди","Вихід" на екран, визначаючи координати за допомогою методу centerText()
        g.drawString("Вихід", centerText(g, "Вихід"), buttons[2].y + paddingWidth + (paddingWidth / 2));
        g.setColor(Color.orange);
    }

    public int centerText(Graphics g, String text) {  // метод для знаходження координат центру тексту
        return WIDTH / 2 - g.getFontMetrics().stringWidth(text) / 2;
    }


    public void highScores() {  // метод, що переходить до розділу з рекордами
        Controller.switchClasses(Controller.STATE.SCORES);
    }

    public void playGame() {  // метод, що починає нову гру
        Controller.switchClasses(Controller.STATE.GAME);
    }

    private class Clock implements Runnable { // клас, що реалізує інтерфейс Runnable, який потрібен для створення потоку
        public void run() { // метод, який виконується під час роботи потоку
            while (running == true) { // виконується, поки гра продовжується
                tick(); // виконує один кадр гри
                try {
                    Thread.sleep(speed); // затримка між кадрами гри
                } catch (InterruptedException e) {
                    break; // якщо потік було перервано, виходить з циклу
                }
                if (running == false) break; // якщо гра закінчилась, виходить з циклу
            }
        }
    }
}
//Клас StartingMenu є одним із класів у грі Tetris і відповідає за початкове меню гри. Він є підкласом класу JPanel та містить методи для початкової настройки гри та її відображення.
//
//У класі містяться константи, які задають ширину та висоту вікна меню гри.
// Також міститься змінна mousePoint, що зберігає координати курсора миші, змінні для завантаження зображення квадрата, створення потоку та об'єкту генерації випадкових чисел.
//
//Клас також містить масив для зберігання кнопок, об'єкти блоків та лічильник spawnCount для підрахунку кількості блоків.

