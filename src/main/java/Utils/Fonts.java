package Utils;

import java.awt.*;

public class Fonts {
    private static final Font NUNITO = new Font("Nunito", Font.PLAIN, 16);
    public static  Font getFont(int style, int size) {
        return  NUNITO.deriveFont(style, size);
    }
    public static  Font getFont(int style) {
        return  NUNITO.deriveFont(style);
    }


}

