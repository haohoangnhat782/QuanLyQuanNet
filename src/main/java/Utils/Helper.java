package Utils;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Helper {
    public static File[] getResourceFolderFiles(String folder) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(folder);
        assert url != null;
        String path = url.getPath();
        return new File(path).listFiles();
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static String getDateString(Date date) {
        return dateFormat.format(date);
    }

    public static String formatMoney(double money) {
        return String.format("%,.2fđ", money);
    }

    public static File getResourceFile(String path) {
        var a = Helper.class.getResource(path).getPath();
        return new File(a);

    }

    public static String toSqlDateString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static void initUI() {
        try {
            FlatMacLightLaf.setup();
            UIManager.getLookAndFeelDefaults()
                    .put("defaultFont", new Font("nunito", Font.PLAIN, 14));
            UIManager.put("Button.font", Fonts.getFont(Font.PLAIN));
            UIManager.put("Label.font", Fonts.getFont(Font.PLAIN));
            UIManager.put("TextField.font", Fonts.getFont(Font.PLAIN));
            UIManager.put("TextArea.font", Fonts.getFont(Font.PLAIN));
            UIManager.put("ComboBox.font", Fonts.getFont(Font.PLAIN));
            UIManager.put("Table.font", Fonts.getFont(Font.PLAIN));
            UIManager.put("TableHeader.font", Fonts.getFont(Font.PLAIN));
            UIManager.put("List.font", Fonts.getFont(Font.PLAIN));
            UIManager.put("OptionPane.font", Fonts.getFont(Font.PLAIN));
            UIManager.put("OptionPane.messageFont", Fonts.getFont(Font.PLAIN));
            UIManager.put("OptionPane.buttonFont", Fonts.getFont(Font.PLAIN));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ImageIcon getIcon(String path, int width, int height) {
        var icon = new ImageIcon(getResource(path));
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    public static URL getResource(String path) {
        if (!path.startsWith("/")
                && !path.startsWith("\\")
                && !path.startsWith("http")
                && !path.startsWith("https")
                && !path.startsWith("file")) {
            path = "/" + path;
        }
        var url = Helper.class.getResource(path);
        if (url == null) {
            throw new RuntimeException("Resource not found: " + path);
        }
        return url;
    }

    public static Image getImage(String path, int width, int height) {
        var icon = new ImageIcon(getResource(path));
        Image img = icon.getImage();
        if (width == 0 || height == 0)
            return img;
        return img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
    }

    public static ImageIcon getIcon(String path) {
        var projectPath = System.getProperty("user.dir");
         path ="src/main/resources/"+path;
        var joined = String.join("/", projectPath, path);


        return new ImageIcon(joined);
    }

    public static String toHHMM(int seconds, boolean withColon) {
        if (seconds < 0) {
            return "+ ∞";
        }
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        return String.format("%02d%s%02d", hours, withColon ? ":" : " ", minutes);
    }

    public static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {

            return false;
        }
    }

    public static void showSystemNoitification(String title, String message, TrayIcon.MessageType type) {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
            TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Information");
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
            trayIcon.displayMessage(title, message, type);
        }
    }

    public static boolean ValidateDate(String strDate) {
        String regexDate = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
        if (strDate.matches(regexDate))
            return true;
        return false;
    }

    public static boolean compareDate(String date1, String date2) {
        if (date1.compareTo(date2) > 0) {
            return false;
        }
        return true;
    }

    public static ImageIcon getImageIcon(String path, int width, int height) {
        try {
//            Image image = ImageIO.read(new URL(path));
            Image image = ImageIO.read(new File(path));
            if (width == -1 || height == -1) {
                return new ImageIcon(image);
            }
            Image image1 = image.getScaledInstance(width, height, 0);
            return new ImageIcon(image1);
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean checkNumber(String text) {
        if (text.equals(".*[!@#$%].*") || text.equals("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(null, "Không Được Chứa Các Ký Tự Đặt Biệt Hoặc Chữ Cái.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
