package Handling;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Main.Controller;
import Main.Game;
import Main.HighScores;
import Main.StartingMenu;
import Main.EndScreen;

public class MouseHandler implements MouseListener {

    // Оголошуємо змінні для посилання на об'єкти StartingMenu, Game, HighScores та EndScreen.
    StartingMenu menu;
    Game game;
    HighScores scores;
    EndScreen endscreen;

    // Створюємо конструктор, який приймає об'єкти StartingMenu, Game, HighScores та EndScreen.
    public MouseHandler(StartingMenu menu, Game game, HighScores scores, EndScreen endscreen) {
        this.menu = menu;
        this.game = game;
        this.scores = scores;
        this.endscreen = endscreen;
    }

// Реалізуємо методи інтерфейсу MouseListener, які слідкують за подіями миші.

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    // Реалізуємо метод mouseReleased, який спрацьовує, коли миша була відпущена.
    public void mouseReleased(MouseEvent e) {
        // Якщо гра запущена, то перевіряємо, яку кнопку натиснули.
        if (Controller.state == Controller.STATE.GAME) {
            if (game.buttons[0].contains(e.getPoint())) { // кнопка "Clear game"
                game.clearGame(); // очищаємо поле гри.
            }
            if (game.buttons[1].contains(e.getPoint())) { // кнопка "Main Menu"
                game.running = false; // зупиняємо гру.
                Controller.switchClasses(Controller.STATE.MENU); // переходимо до головного меню.
            }
        }
        // Якщо знаходимося у головному меню, то перевіряємо, яку кнопку натиснули.
        if (Controller.state == Controller.STATE.MENU) {
            if (menu.buttons[0].contains(e.getPoint())) { // кнопка "Play"
                menu.running = false; // зупиняємо меню.
                menu.playGame(); // починаємо гру.
            }
            if (menu.buttons[1].contains(e.getPoint())) { // кнопка "High Scores"
                menu.running = false; // зупиняємо меню.
                menu.highScores(); // переходимо до таблиці рекордів.
            }
            if (menu.buttons[2].contains(e.getPoint())) { // кнопка "Exit"
                System.exit(0); // закриваємо програму.
            }
        }
        // Якщо знаходимося у таблиці рекордів HighScores:
        if (Controller.state == Controller.STATE.SCORES) {
            if (scores.back.contains(e.getPoint())) {  // кнопка "Back"
                scores.goBack(); // повертаємося на головне меню.
            }
        }
        if (Controller.state == Controller.STATE.ENDSCREEN) { // Якщо знаходимося на екрані закінчення гри:
            if (endscreen.menu.contains(e.getPoint())) { // кнопка "Main Menu"
                Controller.switchClasses(Controller.STATE.MENU); // переходимо на головне меню.
            }
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
// Клас MouseHandler містить обробник подій миші для гри Тетріс.
// У конструкторі класу передаються посилання на об'єкти StartingMenu, Game, HighScores та EndScreen.
//
//Метод mouseReleased реалізує дії, які відбуваються після того, як користувач відпускає кнопку миші.
// Якщо гра запущена, метод перевіряє, яку кнопку натиснуто: якщо це кнопка "Clear game", то метод очищає поле гри, а якщо це кнопка "Main Menu", то метод зупиняє гру та переходить до головного меню.
// Якщо знаходимося у головному меню, метод перевіряє, яку кнопку натиснуто:
// якщо це кнопка "Play", то метод зупиняє меню та починає гру, якщо це кнопка "High Scores", то метод зупиняє меню та переходить до таблиці рекордів,
// а якщо це кнопка "Exit", то метод закриває програму.
// Якщо знаходимося у таблиці рекордів HighScores, метод перевіряє, чи натиснуто кнопку "Back" та повертається до головного меню.
// Якщо знаходимося на екрані закінчення гри, метод перевіряє, чи натиснуто кнопку "Main Menu" та переходить до головного меню.
//
//Цей клас забезпечує обробку подій миші для гри та забезпечує логіку переходів між екранами гри.
