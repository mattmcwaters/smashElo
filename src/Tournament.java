import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Matt on 2015-08-01.
 */
public class Tournament {
    PlayerCollection pc = PlayerCollection.getPc();
    ArrayList<Player> players = pc.getParticipantList();
    ArrayList<Match> matches = new ArrayList<Match>();

    public Tournament(){
    }

    public void setPlayers(HashMap<String, String> playerMap, Boolean fixNames)throws Exception{

        for(String s: playerMap.keySet()){

            Player p=null;
            if(fixNames){
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Is this player's tag the right tag?: " + playerMap.get(s) + " (y/n)");
                String userInput = br.readLine();
                if(userInput.equals("y")){
                   p = new Player(s, playerMap.get(s));
                }
                else if(userInput.equals("n")){
                    System.out.println("Okay please fix player's tag");
                    userInput = br.readLine();
                    p = new Player(s, userInput);

                }
            }
            else{
                p = new Player(s, playerMap.get(s));

            }
            if(!pc.nameIsPresent(p.name)){
                pc.addPlayer(p);
            }
        }
    }
    public void addUpdatePlayers(ArrayList<Player> morePlayers){
        ArrayList<Player> players = pc.getParticipantList();
    }
    public void addMatch(Match match){
        matches.add(match);
    }
    public ArrayList<Player> getPlayers(){
        return players;
    }
    public void sortMatches(){
        Collections.sort(matches, new matchComparitor());
    }
    public void calcAllElo(){
        sortMatches();
        for(Match m: matches){
            m.setPlayers(pc);
            Player.updateElos(m.getWinnerPlayer(), m.getLoserPlayer(), 33);
        }
        return;
    }
}
