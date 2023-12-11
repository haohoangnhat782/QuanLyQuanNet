import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Utils {
    public static Date randomDate(Date start, Date end) {
        long diff = end.getTime() - start.getTime();
        return new Date(start.getTime() + (long) (Math.random() * diff));
    }
    public static Date parseDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return format.parse(date);
        } catch (Exception e) {
            return null;
        }
    }
    public static Date randomDate(String start, String end) {
        return randomDate(Objects.requireNonNull(parseDate(start)), Objects.requireNonNull(parseDate(end)));
    }
    public static Date addRandomHour(Date date,int min, int max) {
        return new Date(date.getTime() + (long) (Math.random() * (max - min) + min) * 60 * 60 * 1000);
    }
}
