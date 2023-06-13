package Handling;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import Main.Controller;
import Main.Game;
import Main.HighScores;
import Main.StartingMenu;
import Main.EndScreen;

public class MouseMotion implements MouseMotionListener{

	StartingMenu menu;
	Game game;
	HighScores scores;
	EndScreen endscreen;
	
	public MouseMotion(StartingMenu menu, Game game, HighScores scores, EndScreen endscreen) {
		this.game = game;
		this.menu = menu;
		this.scores = scores;
		this.endscreen = endscreen;
	}
	
	public void mouseDragged(MouseEvent e) {
		
	}

	public void mouseMoved(MouseEvent e) {
		if(Controller.state == Controller.STATE.GAME) {
		game.mousePoint.x = e.getX();
		game.mousePoint.y = e.getY();
		}
		if(Controller.state == Controller.STATE.MENU) {
			menu.mousePoint.x = e.getX();
			menu.mousePoint.y = e.getY();
		}
		if(Controller.state == Controller.STATE.SCORES) {
			scores.mousePoint.x = e.getX();
			scores.mousePoint.y = e.getY();
		}
		if(Controller.state == Controller.STATE.ENDSCREEN) {
			endscreen.mousePoint.x = e.getX();
			endscreen.mousePoint.y = e.getY();
		}
	}
}
//Цей клас відповідає за обробку подій руху миші в грі тетріс.
// Клас реалізує інтерфейс MouseMotionListener, що дозволяє обробляти події руху миші.
// Конструктор класу приймає об'єкти класів StartingMenu, Game, HighScores та EndScreen, які використовуються для доступу до потрібних об'єктів інших класів.
//
//Метод mouseMoved обробляє подію руху миші та зберігає координати миші в об'єкті mousePoint відповідно до стану гри, який зберігається у змінній state об'єкту класу Controller.
// Якщо стан гри - гра (STATE.GAME), то координати миші зберігаються в об'єкті game, якщо стан гри - меню (STATE.MENU), то координати миші зберігаються в об'єкті menu, якщо стан гри - рекорди (STATE.SCORES), то координати миші зберігаються в об'єкті scores, а якщо стан гри - екран закінчення гри (STATE.ENDSCREEN), то координати миші зберігаються в об'єкті endscreen.