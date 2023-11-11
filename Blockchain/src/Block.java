import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Block implements Runnable{

    private final String data;
    private final String timestamp;
    private final String timezone;
    private final String user;
    private final double value;
    private String hash;

    //difficulty for generating the hash
    private final int difficulty = 3;

    private String ruledPattern = "0";

    private Block previousBlock = null;
    private String previousHash = null;
    private double start;
    private double end;
    private final BlockchainStatus blockchainStatus;

    public Block(Block previousBlock, double start, double end, String data, String user, double value, BlockchainStatus blockchainStatus){
        this.data = data;
        this.timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        TimeZone tz = TimeZone.getDefault();
        this.timezone = tz.getID();
        this.user = user;
        this.value = value;
        this.hash = null;

        this.previousBlock = previousBlock;
        this.start = start;
        this.end = end;
        this.blockchainStatus = blockchainStatus;
    }

    public Block(String previousHash, String data, String user, double value, BlockchainStatus blockchainStatus){
        this.data = data;
        this.timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        TimeZone tz = TimeZone.getDefault();
        this.timezone = tz.getID();
        this.user = user;
        this.value = value;
        this.hash = previousHash;

        //this.previousHash = previousHash;
        //this.start = start;
        //this.end = end;
        this.blockchainStatus = blockchainStatus;
    }

    public void run(){
        if (this.mineBlock() == 1 || blockchainStatus.isHashFound()) {
            return;
        } else {
            blockchainStatus.setHashFound(true);
            System.out.println("Hash found by thread: " + Thread.currentThread().getId());
        }
    }

    private int mineBlock(){

        // if Block(String hash, ...) got called without Block.setStartAndEnd(...)
        if(this.end == 0.0){
            return 1;
        }

        if(this.previousBlock != null){
            this.previousHash = previousBlock.getHash();
        }

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // Example: "0000000xxxxxxxxxxxxx"
        for(int i = 1; i < difficulty; i++){
            this.ruledPattern += "0";
        }

        String hash;
        String randomString;
        //int length = 0;

        //TODO was wenn es keinen hash im Intervall des index gibt?

        do{
            if (this.blockchainStatus.isHashFound() || start > end) {
                return 1;
            }

            String formattedStart = String.format("%.16f", start);
            randomString = Long.toHexString(Double.doubleToLongBits(Math.random()));
            String stringToHash = data + timezone + timestamp + user + Double.toString(value) + this.previousHash + formattedStart + randomString;

            //System.out.println(stringToHash);
            messageDigest.reset();
            messageDigest.update(stringToHash.getBytes());

            byte[] hashBytes = messageDigest.digest();
            hash = new String(hashBytes, StandardCharsets.UTF_8);

            //System.out.println("THREAD " + Thread.currentThread().getId() + ": " + hash);
            start += 0.000000000000000001;
            //System.out.println(i);
            //System.out.println(hash.length());
        } while(!hash.substring(0, difficulty).equals(this.ruledPattern));

        this.hash = hash;

        //System.out.println(length);
        System.out.println("HASH: " + this.hash);



        return 0;
    }


    private boolean isValidSHA512Hash(String hash) {
        if (hash.length() != 64) {
            System.out.println("lenght");
            return false;
        }

        for (int i = 0; i < hash.length(); i++) {
            char c = hash.charAt(i);
            if ((c < '0' || c > '9') && (c < 'a' || c > 'f') && (c < 'A' || c > 'F')) {
                System.out.println("char");
                return false;
            }
        }

        return true;
    }



    //getter for Blockchain
    public String getRuledPattern(){return this.ruledPattern;}
    public String getHash(){
        return this.hash;
    }

    public double getValue(){ return this.value;}

    public String getData() {
        return this.data;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public String getUser() {
        return this.user;
    }
}
