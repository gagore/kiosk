package kiosk;
import java.awt.Font;


public class FontSet {
    public static Font defaultFont;
    public static Font bigFont;
    public static Font fontNow;
    public static void main(String[] args) {

        defaultFont = new Font("Nanum Gothic", Font.PLAIN, 15);
        bigFont = new Font("Nanum Gothic", Font.PLAIN, 22);
        fontNow = defaultFont;
    }

    public static void changeFont() {
        
        if (fontNow == defaultFont) {
            fontNow = bigFont;
            // MainGUI.leftButton2.setBackground(Color.GREEN);
        } else {
            fontNow = defaultFont;
            // MainGUI.leftButton2.setBackground(Color.BLUE);
        }
        PopupGUI.changeFont();
        MainGUI.changeFont();
        
        // MainGUI.gui.refreshGUI();
        // MainGUI.gui.basketReload();        
    }
}
