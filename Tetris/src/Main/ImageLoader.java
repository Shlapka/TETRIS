package Main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageLoader {

    private BufferedImage img;
    // Шлях до зображень квадратів та логотипу гри
    public static String squarePath = "/Squares.png";
    public static String tetrisPath = "/TetrisLogo.png";

    public ImageLoader(String path) {
        try {
            img = ImageIO.read(ImageLoader.class.getResourceAsStream(path)); // Завантаження зображення за вказаним шляхом

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return img;
    } // Метод для отримання завантаженого зображення

    public BufferedImage getSubImage(int section) { // Метод для отримання підзображення за номером секції
        return img.getSubimage(section * 50, 0, 50, 50); // Вибірка підзображення за номером секції
    }
}

//Клас ImageLoader містить методи для завантаження зображень і вибірки підзображень з цих зображень.
//
//BufferedImage img: зберігає завантажене зображення.
//
//public static String squarePath = "/Squares.png";: шлях до зображення квадратів в грі.
//
//public static String tetrisPath = "/TetrisLogo.png";: шлях до зображення логотипу гри.
//
//public ImageLoader(String path): конструктор класу, завантажує зображення за вказаним шляхом path.
//
//public BufferedImage getImage(): метод для отримання завантаженого зображення.
//
//public BufferedImage getSubImage(int section): метод для отримання підзображення за номером секції section. Вибірка підзображення залежить від розмірів та кількості секцій в зображенні.
//
//Загалом, клас ImageLoader допомагає зробити гру більш гнучкою, дозволяючи динамічно завантажувати зображення та вибірково використовувати їх окремі частини в різних частинах гри.