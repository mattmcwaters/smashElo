import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by Matt on 2015-07-19.
 */
public class ChallongeInteractor {
    private static HashMap<String, String> participantMap = new HashMap<String, String>();

    public void getMatches() throws Exception{

            URL url = new URL("https://api.challonge.com/v1/tournaments/sot5/matches.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");


            BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                    System.out.println(output);
            }
            conn.disconnect();

    }
    public  void loadMatches(String tourneyName, Tournament tourney) throws Exception {
        String url =
            "https://methodApi:Ba06wa7vya0kdnulyhLtOT4hTiwgHcMgmh5rCpPm@api.challonge.com/v1/tournaments/"
                + tourneyName + "/matches.xml";
        String result = "";

        URL newUrl = new URL(url);

        String loginPassword =
            "methodApi:Ba06wa7vya0kdnulyhLtOT4hTiwgHcMgmh5rCpPm";
        String encoded = new sun.misc.BASE64Encoder().encode(loginPassword.getBytes());
        URLConnection conn = newUrl.openConnection();
        conn.setRequestProperty("Authorization", "Basic " + encoded);
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        String output;
        while ((output = br.readLine()) != null) {
            result += output;
        }
        Pattern id1Pattern = Pattern.compile("<winner-id type=\"integer\">(.*?)</winner-id>");
        Matcher id1Matcher = id1Pattern.matcher(result);

        Pattern id2Pattern = Pattern.compile("<loser-id type=\"integer\">(.*?)</loser-id>");
        Matcher id2Matcher = id2Pattern.matcher(result);

        Pattern scorePattern = Pattern.compile("<scores-csv>(.*?)</scores-csv>");
        Matcher scoreMatcher = scorePattern.matcher(result);

        Pattern updatedPattern = Pattern.compile("<updated-at type=\"dateTime\">(.*?)</updated-at>");
        Matcher updatedMatcher = updatedPattern.matcher(result);

        while(id1Matcher.find() && id2Matcher.find() && scoreMatcher.find() && updatedMatcher.find() ){
            String score = scoreMatcher.group(1);
            Match m =
                new Match(score, participantMap.get(
                    id1Matcher.group(1)), participantMap.get(
                    id2Matcher.group(1)), updatedMatcher.group(1));
            tourney.addMatch(m);
        }

    }
    public  void loadParticipantsMap(String tourneyName, Tournament tourney, boolean Option) throws Exception
    {
        String url = "https://methodApi:Ba06wa7vya0kdnulyhLtOT4hTiwgHcMgmh5rCpPm@api.challonge.com/v1/tournaments/" + tourneyName+"/participants.xml";
        String result="";

        URL newUrl = new URL(url);

        String loginPassword = "methodApi:Ba06wa7vya0kdnulyhLtOT4hTiwgHcMgmh5rCpPm";
        String encoded = new sun.misc.BASE64Encoder().encode (loginPassword.getBytes());
        URLConnection conn = newUrl.openConnection();
        conn.setRequestProperty ("Authorization", "Basic " + encoded);
        BufferedReader br = new BufferedReader(new InputStreamReader(
            (conn.getInputStream())));

        String output;
        while ((output = br.readLine()) != null) {
            result+=output;
        }


        Pattern idPattern = Pattern.compile("<id type=\"integer\">(.*?)</id>");
        Matcher idMatcher = idPattern.matcher(result);

        Pattern namePattern = Pattern.compile("<display-name-with-invitation-email-address>(.*?)</display-name-with-invitation-email-address>");
        Matcher nameMatcher = namePattern.matcher(result);
        while(idMatcher.find() && nameMatcher.find()){
            PlayerCollection pc = new PlayerCollection();
            participantMap.put(idMatcher.group(1), pc.fixName(pc.clean(nameMatcher.group(1))));
        }
        tourney.setPlayers(participantMap, Option);
    }




}
