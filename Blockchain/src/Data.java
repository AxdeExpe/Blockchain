import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class Data{

    private Blockchain blockchain;
    private List<Block> chain;

    public Data(Blockchain chain){

        this.blockchain = chain;
        this.chain = blockchain.getChain();
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

        // Verwenden eines Objekts vom Typ `FileWriter`, um den Inhalt des JSON-Objekts in eine Datei zu schreiben
        FileWriter fileWriter = new FileWriter(path + ".json");
        fileWriter.write(jsonObject.toString());
        fileWriter.close();

    }

    public void importChain(String path) throws Exception{

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(fileReader);

        JSONObject blockObject;
        int i = 0;

        while(true){

            if (jsonObject.has("Block " + i)) {
                i++;
                continue;
            }

            blockObject = jsonObject.getJSONObject("Block " + i);
            break;
        }

        String hash = blockObject.getString("Hash");

        System.out.println(hash);
    }

}