import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private final List<Block> chain;
    private final BlockchainStatus blockchainStatus;

    private final int cores = Runtime.getRuntime().availableProcessors();

    private final double fraction = Double.MAX_VALUE / cores;

    public Blockchain(){
        this.chain = new ArrayList<>();
        this.blockchainStatus = new BlockchainStatus();
    }

    public void generateGenesisBlock(){
        Thread[] t = new Thread[cores];

        double fraction = Double.MAX_VALUE / cores;

        Block genesis = null;
        List<Block> genesisList = new ArrayList<>();

        for(int i = 1; i <= cores; i++){
            // --> [0, 1/x-0] -> (1/x, 1/x-1] -> (1/x-2, 1/x-3] -> ...
            // --> start: [0, fraction] -> (1 * fraction, 2 * fraction] -> (2 * fraction, 3 * fraction] -> ...
            double start = ((i-1) * fraction) + i-1;
            double end = i * fraction;

            genesisList.add(new Block((Block) null,start, end, "Hello World!", "Genesis", 0.5, this.blockchainStatus));
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

    public void generateBlock(String data, String user, double value){
        Thread[] t = new Thread[cores];

        Block block = null;
        List<Block> BlockList = new ArrayList<>();

        for(int i = 0; i < cores; i++){
            // --> [0, 1/x-0] -> (1/x, 1/x-1] -> (1/x-2, 1/x-3] -> ...
            // --> start: [0, fraction] -> (1 * fraction, 2 * fraction] -> (2 * fraction, 3 * fraction] -> ...
            double start = ((i) * fraction) + i;
            double end = i+1 * fraction;

            System.out.println("HASH PREVIOS BLOCK" + this.getPreviousBlock().getHash());

            BlockList.add(new Block(this.getPreviousBlock(),start, end, data, user, value, this.blockchainStatus));
            t[i] = new Thread(BlockList.get(i));
            t[i].start();
        }

        for(int i = 0; i < cores; i++){
            try {
                t[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for(int i = 0; i < BlockList.size(); i++){
            String hash = BlockList.get(i).getHash();
            if(hash != null && !BlockList.isEmpty() && hash.substring(0, BlockList.get(0).getDifficulty()).equals(BlockList.get(0).getRuledPattern())){
                block = BlockList.get(i);
                break;
            }
        }

        if(block == null){
            System.out.println("Blockchain is invalid!");
            System.exit( -1);
        }

        this.addBlock(block);
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
