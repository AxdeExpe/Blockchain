import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private List<Block> chain;
    private BlockchainStatus blockchainStatus;

    public Blockchain(){
        this.chain = new ArrayList<>();
        this.blockchainStatus = new BlockchainStatus();
        this.generateGenesisBlock();
    }

    private void generateGenesisBlock(){
        int cores = Runtime.getRuntime().availableProcessors();
        Thread[] t = new Thread[cores];

        //Block first = new Block(chain.getPreviousBlock(),0.0, Double.MAX_VALUE, "asbjkd", "first", 0.5);
        //first.mineBlock(chain.getPreviousBlock());

        double fraction = Double.MAX_VALUE / cores;

        Block genesis = null;

        List<Block> genesisList = new ArrayList<>();

        for(int i = 1; i <= cores; i++){
            // --> [0, 1/x-0] -> (1/x, 1/x-1] -> (1/x-2, 1/x-3] -> ...
            // --> start: [0, fraction] -> (1 * fraction, 2 * fraction] -> (2 * fraction, 3 * fraction] -> ...
            double start = ((i-1) * fraction) + i-1;
            double end = i * fraction;

            genesisList.add(new Block(null,start, end, "Hello World!", "Genesis", 0.5, this.blockchainStatus));
            t[i-1] = new Thread(genesisList.get(i-1));
            t[i-1].start();
        }

        for(int i = 0; i < cores; i++){
            try {
                t[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        for(int i = 0; i < genesisList.size(); i++){
            String hash = genesisList.get(i).getHash();
            if(hash != null && !genesisList.isEmpty() && hash.substring(0, genesisList.get(0).getDifficulty()).equals(genesisList.get(0).getRuledPattern())){
                genesis = genesisList.get(i);
                break;
            }
        }

        this.addBlock(genesis);
    }

    public void addBlock(Block block){
        this.chain.add(block);
    }

    public Block getPreviousBlock(){
        return this.chain.get(this.chain.size()-1);
    }


    //getter for chain

    public List<Block> getChain() {
        return this.chain;
    }
}
