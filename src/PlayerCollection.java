import info.debatty.java.stringsimilarity.NGram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matt on 2015-08-01.
 */
public class PlayerCollection implements Serializable {
    static PlayerCollection  pc = null;
    public HashMap<String, Player> participantMap = new HashMap<String, Player>();
    public HashMap<String, List<String>> possibleNames = new HashMap<String, List<String>>();
    public HashMap<String, ArrayList<String>> badNames = new HashMap<String, ArrayList<String>>();
    static int loopCount=0;

    public static PlayerCollection getPc(){
        if(pc == null){
            pc = new PlayerCollection();
            return pc;
        }
        else{
            return pc;
        }
    }

    public HashMap<String, Player> getParticipantMap(){
        return participantMap;
    }
    public ArrayList<Player> getParticipantList(){

        return new ArrayList<Player>(participantMap.values());
    }

    public HashMap<String, List<String>> getPossibleNames(){
        return possibleNames;

    }
    public void addPlayer(Player p){
        NGram similarityScorer = new NGram(2);
        for(Map.Entry<String, List<String>> entry : getPossibleNames().entrySet()){
            String e = entry.getKey();
            String cleaned =  (clean(e));
            String pCleaned = (clean(p.name));

            Double score = similarityScorer.distance(cleaned, pCleaned);
            for(String name : entry.getValue()){
                if(pCleaned.equals(clean(name))){
                    return;
                }
            }
            if(!nameInBadNames(cleaned,pCleaned)) {
                if (score > 0.38) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    System.out.println(
                        "Is this player:  " + p.name + " actually this player? "
                            + e);
                    String userInput = "";
                    try {
                        userInput = br.readLine();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    if (userInput.equals("y")) {
                        if (possibleNames.get(cleaned) != null) {
                            possibleNames.get(cleaned).add(pCleaned);

                        } else {

                        }
                        return;
                    }
                } else {
                    if (!badNames.containsKey(cleaned)) {
                        ArrayList<String> badNamesTemp = new ArrayList<String>();
                        badNamesTemp.add(pCleaned);
                        this.badNames.put(cleaned, badNamesTemp);
                    } else {
                        this.badNames.get(cleaned).add(pCleaned);
                    }
                }
            }
        }
        if(!participantMap.containsKey(clean(p.name))){
            participantMap.put(clean(p.name), p);
            possibleNames.put(clean(p.name), new ArrayList<String>());
        }
    }
    public String fixName(String toFix){
        NGram simScore = new NGram(2);
        HashMap<String, String> fixNames = new HashMap<String, String>();
        fixNames.put("king ace", "phillycheese");
        fixNames.put("armen", "astro");
        fixNames.put("meehow", "blank");
        fixNames.put("stefan lemke", "awesomecake");
        fixNames.put("to joe", "toronto joe");
        for(String badName : fixNames.keySet()){
            if(simScore.distance(toFix, badName)>0.75){
                return fixNames.get(badName);
            }
        }
        return toFix;
    }
    /*
    public void addPlayer(Player p){

        for(Map.Entry<String, List<String>> entry : getPossibleNames().entrySet()){
            String e = entry.getKey();
            String cleaned = clean(e);
            String pCleaned = clean(p.name);
            loopCount++;
            String[] splitBySpaces = clean(p.name).split("\\s+");
            splitBySpaces = removeEmptys(splitBySpaces);
            for(String name : entry.getValue()){
                if(pCleaned.equals(clean(name))){
                    return;
                }
            }
            if(cleaned.equals(pCleaned)){
                return;
            }
            if(!nameInBadNames(cleaned,pCleaned)){
                for(String s: splitBySpaces){
                    if(e.replaceAll(" ", "").contains(s)){
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        System.out.println("Is this player:  " + p.name + " actually this player? " + e);
                        String userInput="";
                        try {
                            userInput = br.readLine();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        if(userInput.equals("y")){
                            if(possibleNames.get(cleaned)!=null){
                                possibleNames.get(cleaned).add(pCleaned);

                            }
                            else{

                            }
                            return;
                        }
                        else{
                            if(!badNames.containsKey(cleaned)){
                                ArrayList<String> badNamesTemp = new ArrayList<String>();
                                badNamesTemp.add(pCleaned);
                                this.badNames.put(cleaned, badNamesTemp);
                            }
                            else{
                                this.badNames.get(cleaned).add(pCleaned);
                            }
                        }
                    }
                }
            }

        }
        if(!participantMap.containsKey(clean(p.name))){
            participantMap.put(clean(p.name), p);
            possibleNames.put(clean(p.name), new ArrayList<String>());
        }

    }
    */
    private Boolean nameInBadNames(String name1, String name2){
        ArrayList<String> name1BadNames= this.badNames.get(name1);
        if(name1BadNames!=null){
            for(String badName: name1BadNames){
                if(name2.equals(badName)){
                    return true;
                }
            }
        }
        return false;
    }
    public String clean(String nameToClean){
        String cleaned = nameToClean;
        cleaned = cleaned.toLowerCase();

        cleaned = cleaned.replaceAll("\\([^)]*\\)", "");
        cleaned = cleaned.replaceAll("_", " ");
        cleaned = cleaned.replaceAll("\\[[^\\]]*\\]", "");
        cleaned = cleaned.replaceAll("\\|", " ");
        cleaned = cleaned.trim();
        cleaned = cleaned.replaceAll("\\s+", " ");;
        cleaned = cleaned.replaceAll(" [0-9] ", "");;
        cleaned = cleaned.replaceAll(" l ", " ");
        cleaned = cleaned.replaceAll("apw", "");
        cleaned = cleaned.replaceAll("scs", "");
        cleaned = cleaned.replaceAll("emg", "");

        return cleaned;
    }
    public Boolean nameIsPresent(String name){
        return participantMap.containsKey(clean(name));
    }
    public void loadPc(PlayerCollection newPc){
        this.participantMap = newPc.getParticipantMap();
        this.possibleNames = newPc.getPossibleNames();
    }

    public static String[] removeEmptys(final String[] v) {
        int r, w;
        final int n = r = w = v.length;
        while (r > 0) {
            final String s = v[--r];
            if (!s.equals("")) {
                v[--w] = s;
            }
        }
        return Arrays.copyOfRange(v, w, n);
    }

}
