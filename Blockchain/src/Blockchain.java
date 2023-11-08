import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Blockchain {

    List<Block> chain;

    public Blockchain(){
        this.chain = new ArrayList<>();
        this.generateGenesisBlock();
    }

    private void generateGenesisBlock(){
        Block genesis = new Block(null, "genesis", 0.0);
        genesis.calcHash(genesis);
        this.addBlock(genesis);
    }

    public void addBlock(Block block){
        this.chain.add(block);
    }

    public int checkBlockchain(){

        if(this.chain.toArray().length < 2){
            System.out.println("Can not check the Blockchain, length: " + this.chain.toArray().length);
            return 2;
        }

        for(int i = 1; i <= this.chain.toArray().length; i++){
            Block previousBlock = this.chain.get(i-1);
            String proofingHash = previousBlock.getHash();

            String calculatedHash = previousBlock.calcHash(previousBlock);

            if(!Objects.equals(proofingHash, calculatedHash)){
                System.out.println("BLOCK " + i + " is not valid to Block " + (i-1));
                System.out.println("Hash is invalid! Hash proof.: " + proofingHash + "; Hash calc.: " + calculatedHash);
                System.out.println(this.chain.get(i-1).getHash() + " " + this.chain.get(i).getHash());

                //TODO delete Block

                //return 1;
            }
        }
        return 0;
    }

    public Block getPreviousBlock(){
        return this.chain.get(this.chain.toArray().length - 1);
    }

    public int exportBlockchain(){
        

        return 0;
    }








    //It's for an existing Blockchain in JSON format
    public int importExistingChain(File chain){


        return 0;
    }

    private int readChain(){

        return 0;
    }





}
