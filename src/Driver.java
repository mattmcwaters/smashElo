import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Matt on 2015-08-01.
 */
public class Driver {

    static PlayerCollection pc = PlayerCollection.getPc();

    public static void main(String[] args) throws Exception{
        File f;
        PlayerCollection loadedPc;

        try{
             f = new File("PC.ser");
            if(f.exists()){

                loadedPc = new PlayerCollection();
                FileInputStream fileIn = new FileInputStream("PC.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                loadedPc = (PlayerCollection) in.readObject();
                in.close();
                fileIn.close();

                pc.loadPc(loadedPc);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }


        Tournament tourney = new Tournament();
        Tournament tourney2 = new Tournament();
        ChallongeInteractor cI = new ChallongeInteractor();


        String tourneyName = "letsplaykw-exp2015wiiu";
        cI.loadParticipantsMap(tourneyName, tourney, false);
        cI.loadMatches(tourneyName, tourney);
        tourney.calcAllElo();




/*
        for(Player p: test.values()){
            System.out.println("Player: " + p.name + "\n\tElo rating: " + p.elo+"\n");
        }
        for(String s: test.keySet()){
            System.out.println(s);
        }*/
        try
        {
            FileOutputStream fileOut =
                new FileOutputStream("PC.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(pc);
            out.close();
            fileOut.close();
            FileOutputStream fileOu2t =
                new FileOutputStream("PC"+tourneyName+".ser");
            ObjectOutputStream out2 = new ObjectOutputStream(fileOut);
            out.writeObject(pc);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in PC.ser");
        }catch(IOException i)
        {
            i.printStackTrace();
        }

    }
}
