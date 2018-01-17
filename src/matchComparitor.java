import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Matt on 2015-08-03.
 */
public class matchComparitor implements Comparator<Match> {
    public int compare(Match m1, Match m2){
        if(m1.getUpdatedTime().after(m2.getUpdatedTime())){
            return 1;
        }
        else if(m1.getUpdatedTime().before(m2.getUpdatedTime())){
            return -1;
        }
        else if(m1.getUpdatedTime().equals(m2.getUpdatedTime())){
            return 0;
        }
        return 0;
    }
}
