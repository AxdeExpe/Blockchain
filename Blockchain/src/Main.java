// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        Blockchain chain = new Blockchain();
        int ret = chain.checkBlockchain();

        Block first = new Block("asbjkd", "first", 0.5);
        first.mineBlock(chain.getPreviousBlock());
        chain.addBlock(first);
        //chain.addBlock(first);
        //chain.addBlock(first);

        chain.checkBlockchain();


        Block second = new Block("328r79", "second", 1.0);
        second.mineBlock(chain.getPreviousBlock());
        chain.addBlock(second);

        chain.checkBlockchain();

    }
}