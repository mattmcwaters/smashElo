import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Matt on 2015-08-01.
 */
public class Match {


    int winnerScore;
    int loserScore;
    String winner;
    String loser;
    Player winnerPlayer;
    Player loserPlayer;
    String timeUpdated;
    Date timeUpdatedDate;
    public Match(String score, String winner, String loser, String timeUpdated)throws Exception{
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(score);

        if(m.find()){
            this.winnerScore = Integer.parseInt("" + m.group());
            this.loserScore = Integer.parseInt("" + m.group());
        }
       this.winner = winner;
        this.loser = loser;
        this.timeUpdated = timeUpdated;
        this.timeUpdatedDate = parseDate(timeUpdated);
    }
    public Date parseDate(String date)throws Exception{

        DateFormat df = new SimpleDateFormat("yyyy-mm-dd'T'kk:mm:ssX", Locale.ENGLISH);
        Date result =  df.parse(date);
        return result;
    }
    public Date getUpdatedTime(){
        return this.timeUpdatedDate;
    }
    public void setPlayers(PlayerCollection playerList){

        for(Map.Entry<String, List<String>> entry : playerList.getPossibleNames().entrySet()){
            String originalName = entry.getKey();
            if((originalName.equals(playerList.clean(loser)))){
                loserPlayer = playerList.getParticipantMap().get(originalName);
            }
            if((originalName.equals(playerList.clean(winner)))){
                winnerPlayer = playerList.getParticipantMap().get(originalName);
            }
            for(String name : entry.getValue()){
                if(name.equals(playerList.clean(loser))){
                    loserPlayer = playerList.getParticipantMap().get(originalName);
                }
                if(name.equals(playerList.clean(winner))){
                    winnerPlayer = playerList.getParticipantMap().get(originalName);
                }
            }

        }
    }

    public Player getWinnerPlayer(){
        return winnerPlayer;
    }
    public Player getLoserPlayer(){
        return loserPlayer;
    }
}
