import org.json.JSONObject;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Data{

    private Blockchain blockchain;
    private List<Block> chain;

    public Data(Blockchain chain){
        this.blockchain = chain;
        this.chain = blockchain.getChain();
    }

    public Data(){
        try {
            this.blockchain = new Blockchain();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void exportChain(String path) throws Exception{

        JSONObject jsonObject = new JSONObject();

        for(int i = 0; i < this.chain.size(); i++){
            Block block = this.chain.get(i);

            JSONObject blockJSON = new JSONObject();

            blockJSON.put("User", block.getUser());
            blockJSON.put("Timestamp", block.getTimestamp());
            blockJSON.put("Timezone", block.getTimezone());
            blockJSON.put("Data", block.getData());
            blockJSON.put("Value", Double.toString(block.getValue()));
            blockJSON.put("Difficulty", block.getDifficulty());
            blockJSON.put("Hash", block.getHash());
            System.out.println("DATA HASH: " + block.getHash());

            jsonObject.put("Block " + i, blockJSON);
        }

        FileWriter fileWriter = new FileWriter(path + ".json");
        fileWriter.write(jsonObject.toString());
        fileWriter.close();

    }

    public Blockchain importChain(String path) throws Exception{

        try{
            String jsonContent = new String(Files.readAllBytes(Paths.get(path)));

            JSONObject jsonObject = new JSONObject(jsonContent);

            int i = 0;

            while(true){

                if (jsonObject.has("Block " + i)) {

                    JSONObject blockObject = jsonObject.getJSONObject("Block " + i);

                    String User = blockObject.getString("User");
                    String Data = blockObject.getString("Data");
                    double Value = Double.parseDouble(blockObject.getString("Value"));
                    String Hash = blockObject.getString("Hash");
                    String Timezone = blockObject.getString("Timezone");
                    String Timestamp = blockObject.getString("Timestamp");

                    System.out.println("Extracted Hash: " + Hash);

                    BlockchainStatus status = new BlockchainStatus();
                    Block b = new Block(Hash, Data, User,Value, Timezone, Timestamp, status);

                    this.blockchain.addBlock(b);

                    i++;
                }

                break;
            }

            return this.blockchain;

        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}