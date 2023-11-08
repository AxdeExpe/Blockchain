import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Block {

    private final String data;
    private final String timestamp;
    private final String timezone;
    private final String user;
    private final double value;
    private String hash;

    //difficulty for generating the hash
    private final int difficulty = 2;

    public Block(String data, String user, double value){
        this.data = data;
        this.timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        TimeZone tz = TimeZone.getDefault();
        this.timezone = tz.getID();
        System.out.println(timestamp + " " + timezone);
        this.user = user;
        this.value = value;
        this.hash = null;
    }

    public String mineBlock(Block previousBlock){

        String previousHash = previousBlock.getHash();

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        String substring = "0";
        for(int i = 1; i < difficulty; i++){
            substring += "0";
        }

        System.out.println(substring);

        String hash;
        int length = 0;

        //TODO was wenn es keinen hash im Intervall des index gibt?  
        double i = 0.0;

        do{
            String stringToHash = data + timezone + timestamp + user + Double.toString(value) + previousHash + i;
            messageDigest.reset(); // Zurücksetzen des messageDigest-Zustands
            messageDigest.update(stringToHash.getBytes());
            byte[] hashBytes = messageDigest.digest();
            hash = new String(hashBytes, StandardCharsets.UTF_8);
            //System.out.println(hash);
            i = i + 0.00000000000001;
            //System.out.println(i);
            //System.out.println(hash.length());
            if(length < hash.length()){
                length = hash.length();
            }
        } while(!hash.substring(0, difficulty).equals(substring));

        System.out.println(length);
        System.out.println("HASH: " + hash);

        this.hash = hash;

        return hash;
    }

    //getter for Blockchain
    public String getHash(){
        return this.hash;
    }
}
