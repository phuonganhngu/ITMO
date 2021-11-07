package clientudp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class TimerThread extends Thread {

    protected boolean isRunning;
    protected Locale locale;

    protected JLabel dateLabel;
    protected JLabel timeLabel;

    public TimerThread(JLabel dateLabel, JLabel timeLabel, Locale locale) {
        this.dateLabel = dateLabel;
        this.timeLabel = timeLabel;
        this.isRunning = true;
        this.locale = locale;
    }

    @Override
    public void run() {
        while (isRunning) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {

                    Date today = new Date();
                    String result;
                    DateFormat formatter = null;

                    int[] styles = {DateFormat.DEFAULT, DateFormat.SHORT, DateFormat.MEDIUM,
                        DateFormat.LONG, DateFormat.FULL};

                    for (int k = 0; k < styles.length; k++) {
                        formatter = DateFormat.getDateInstance(styles[k], locale);
                        result = formatter.format(today);
                    }

                    dateLabel.setText(formatter.format(today));
                    
                    formatter = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
                    timeLabel.setText(formatter.format(new Date()));
                }
            });
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
        }
    }
}

public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
    
    public void setLocal(Locale locale){
        this.locale = locale;
        //System.out.println(locale);
    }

}
