package Main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import Handling.KeyHandler;
import Handling.MouseHandler;
import Handling.MouseMotion;

public class Controller extends JPanel implements Runnable { // клас-контролер, який успадковує клас JPanel та імплементує інтерфейс Runnable
    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;// сталі значення розмірів вікна

    private BufferedImage image;
    private Graphics2D g;
    private boolean running = true;
    private Thread thread;

    private static Game game;
    private static StartingMenu menu;
    private static HighScores highscores;
    private static EndScreen endscreen;
    public static int recentScore = 0;


    Font helveticaLarge = new Font("Helvetica", Font.PLAIN, 20);

    public enum STATE { // можливі стани гри
        GAME, // гра
        MENU, // початкове меню
        SCORES, // рекорди
        ENDSCREEN, // екран завершення
        EXIT // вихід
    }

    public static STATE state = STATE.MENU;  //початковий стан - меню

    // конструктор класу
    public Controller() { // конструктор
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT)); // встановлюємо розміри панелі
        setFocusable(true); // встановлюємо фокус на панелі
        requestFocus(true); // запитуємо фокус
    }

    // перевизначення методу addNotify() для створення потоку
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start(); // запускаємо гру в окремому потоці
        }
    }

    // метод для ініціалізації
    private void init() {
        // створення зображення та графічного контексту
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        // ініціалізація класів гри, головного меню, таблиці рекордів та екрану закінчення гри
        menu = new StartingMenu();// створюємо початкове меню
        game = new Game(); // створюємо гру
        highscores = new HighScores(); // створюємо рекорди
        endscreen = new EndScreen(); // створюємо екран завершення
        Scoring.init(); // ініціалізуємо змінну рахунку
        menu.init(); // ініціалізуємо початкове меню
        this.addMouseListener(new MouseHandler(menu, game, highscores, endscreen));
        this.addMouseMotionListener(new MouseMotion(menu, game, highscores, endscreen)); // ініціалізація обробників миші, клавіатури
        this.addKeyListener(new KeyHandler(game));
        g.setFont(helveticaLarge);
    }

    public void run() { // запуск основного циклу гри
        init();
        while (running) {
            tick();    // оновлення стану гри
            render();
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }
    }

    public void tick() { // оновлення стану гри
        if (state == STATE.GAME) {
            game.tick();
        }
    }

    public void render() {
        if (state == STATE.GAME) {
            game.render(g); //гра
        }
        if (state == STATE.MENU) {
            menu.render(g); //меню
        }
        if (state == STATE.SCORES) {
            highscores.render(g); //таблиця рекордів
        }
        if (state == STATE.ENDSCREEN) {
            endscreen.render(g); //екран кінця гри
        }


        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    public static void switchClasses(STATE gamestate) { // Ця функція приймає параметр gamestate, який визначає поточний стан гри.
        // В залежності від значення gamestate ми виконуємо відповідні дії:
        state = gamestate;
        if (state == STATE.GAME) {
            game.start();
        }
        if (state == STATE.SCORES) {
            highscores.init();
        }
        if (state == STATE.ENDSCREEN) {
            endscreen.init();
        }
        if (state == STATE.MENU) {
            menu.init();
        }
        if (state == STATE.EXIT) {
            System.exit(0);
        }
    }
}
// У ньому містяться методи init(), tick(), render(), addNotify() та run(), які відповідають за ініціалізацію, оновлення та відображення гри.
//
//Метод addNotify() викликається, коли компонент з'являється на екрані, та створює новий потік.
//
//Метод init() створює зображення та графічний контекст, ініціалізує класи гри, головного меню, таблиці рекордів та екрану завершення гри, а також ініціалізує обробники миші та клавіатури.
//
//Метод tick() виконує оновлення стану гри.
//
//Метод render() відображає стан гри залежно від поточного стану.
//
//Метод run() виконує головний цикл гри, який оновлює та відображає гру.
//
//Метод switchClasses() приймає параметр gamestate, який визначає поточний стан гри та виконує відповідні дії залежно від значення gamestate.