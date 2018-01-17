import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.NGram;
import info.debatty.java.stringsimilarity.QGram;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Matt on 2015-08-03.
 */
public class deserialtest {
    public static void main(String[] args) {

        PlayerCollection pc = PlayerCollection.getPc();
        try
        {
            FileInputStream fileIn = new FileInputStream("PC.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            pc = (PlayerCollection) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c)
        {
            System.out.println("PC class not found");
            c.printStackTrace();
            return;
        }
        HashMap<String, Player> test = pc.getParticipantMap();
        ArrayList<Player> playerList= new ArrayList<Player>();
        playerList.addAll(test.values());
        Collections.sort(playerList, new playerComparitor());
        for(Player p: playerList){
            System.out.println("Player: " + p.name + "\n\tElo rating: " + p.elo+"\n");
        }/*
        NGram l = new NGram(2);
        System.out.println(l.distance("scs iliad 2 ", "illiad"));*/
    }
}
