import java.io.Serializable;

/**
 * Created by Matt on 2015-08-01.
 */
public class Player implements Serializable{
    String id;
    String name;
    double elo;



    public Player(String id, String name){
        this.id = id;
        this.name = name;
        this.elo  = 1500;
    }
    public void setElo(double newElo){
        this.elo = newElo;
    }
    public static void updateElos(Player winner, Player loser, int kRating){
        if(winner==null){
            return;
        }
        if(loser==null){
            return;
        }
        double tRating1 = Math.pow(10, (winner.getElo())/400);
        double tRating2 = Math.pow(10, (loser.getElo())/400);

        double eRating1 = tRating1/(tRating1+tRating2);
        double eRating2 = tRating2/(tRating1+tRating2);

        double newElo1 = winner.getElo() + kRating*(1-eRating1);
        double newElo2 = loser.getElo() + kRating*(0-eRating2);

        winner.setElo(newElo1);
        loser.setElo(newElo2);
    }
    public double getElo() {
        return elo;
    }

    public static void main(String[] args) {
        Player method = new Player("1111", "method");
        Player astro = new Player("1111", "method");
        updateElos(method, astro, 33);
        updateElos(method, astro, 33);
        updateElos(method, astro, 33);
        updateElos(method, astro, 33);
        updateElos(method, astro, 33);
        updateElos(method, astro, 33);
        updateElos(method, astro, 33);


    }
}

