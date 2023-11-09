import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.TimeZone;

public class Block implements Runnable{

    private final String data;
    private final String timestamp;
    private final String timezone;
    private final String user;
    private final double value;
    private String hash;

    //difficulty for generating the hash
    private final int difficulty = 1;

    private Block previousBlock;
    private double start;
    private final double end;
    private BlockchainStatus blockchainStatus;

    public Block(Block previousBlock, double start, double end, String data, String user, double value, BlockchainStatus blockchainStatus){
        this.data = data;
        this.timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        TimeZone tz = TimeZone.getDefault();
        this.timezone = tz.getID();
        //System.out.println(timestamp + " " + timezone);
        this.user = user;
        this.value = value;
        this.hash = null;

        this.previousBlock = previousBlock;
        this.start = start;
        this.end = end;
        this.blockchainStatus = blockchainStatus;
    }

    public void run(){
        if (this.mineBlock() == 1 || blockchainStatus.isHashFound()) {
            System.out.println("No block found!");
        } else {
            blockchainStatus.setHashFound(true);
            System.out.println("Hash found by thread: " + Thread.currentThread().getId());
        }
    }

    private int mineBlock(){

        String previousHash = null;
        if(previousBlock != null){
            previousHash = previousBlock.getHash();
        }

        System.out.println("PREVIOUS HASH: " + previousHash);

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        String substring = "0";
        for(int i = 1; i < difficulty; i++){
            substring += i;
        }

        //System.out.println(substring);

        String hash;
        String randomString;
        int length = 0;

        //TODO was wenn es keinen hash im Intervall des index gibt?

        do{
            if (this.blockchainStatus.isHashFound() || start > end) {
                return 1;
            }

            String formattedStart = String.format("%.16f", start);
            randomString = Long.toHexString(Double.doubleToLongBits(Math.random()));
            String stringToHash = data + timezone + timestamp + user + Double.toString(value) + previousHash + formattedStart + randomString;

            //System.out.println(stringToHash);
            messageDigest.reset();
            messageDigest.update(stringToHash.getBytes());

            byte[] hashBytes = messageDigest.digest();
            hash = new String(hashBytes, StandardCharsets.UTF_8);

            System.out.println("THREAD " + Thread.currentThread().getId() + ": " + hash);
            start += 0.000000000000000001;
            //System.out.println(i);
            //System.out.println(hash.length());
        } while(!hash.substring(0, difficulty).equals(substring));

        //System.out.println(length);
        System.out.println("HASH: " + hash);

        this.hash = hash;

        return 0;
    }

    //getter for Blockchain
    public String getHash(){
        return this.hash;
    }
}
