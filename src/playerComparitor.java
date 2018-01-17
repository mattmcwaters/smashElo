import java.util.Comparator;

/**
 * Created by Matt on 2015-08-04.
 */
public class playerComparitor implements Comparator<Player> {
    public int compare(Player p1, Player p2){
        if(p1.getElo()>p2.getElo()){
            return -1;
        }
        else if(p1.getElo()<p2.getElo()){
            return 1;
        }
        return 0;
    }
}
