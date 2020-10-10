package solutions.misi.clymeskyblockcore.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public String getTimeDifference(Date from, Date to) {
        long timeDifference = from.getTime() - to.getTime();

        long daysLeft= TimeUnit.MILLISECONDS.toDays(timeDifference);
        timeDifference -= TimeUnit.DAYS.toMillis(daysLeft);
        long hoursLeft = TimeUnit.MILLISECONDS.toHours(timeDifference);
        timeDifference -= TimeUnit.HOURS.toMillis(hoursLeft);
        long minutesLeft = TimeUnit.MILLISECONDS.toMinutes(timeDifference);
        timeDifference -= TimeUnit.MINUTES.toMillis(minutesLeft);
        long secondsLeft = TimeUnit.MILLISECONDS.toSeconds(timeDifference);

        String days = String.valueOf(daysLeft);
        if(daysLeft == 0) { days = ""; } else if(daysLeft == 1) { days += " day, "; } else { days += " days, "; }
        String hours = String.valueOf(hoursLeft);
        if(hoursLeft == 0) { hours = ""; } else if(hoursLeft == 1) { hours += " hour, "; } else { hours += " hours, "; }
        String minutes = String.valueOf(minutesLeft);
        if(minutesLeft == 0) { minutes = ""; } else if(minutesLeft == 1) { minutes += " minute, "; } else { minutes += " minutes, "; }
        String seconds = String.valueOf(secondsLeft);
        if(secondsLeft == 0) { seconds = ""; } else if(secondsLeft == 1) { seconds += " second, "; } else { seconds += " seconds, "; }

        String timeLeft = days + hours + minutes + seconds;
        return timeLeft.substring(0, timeLeft.length() - 2);
    }
}