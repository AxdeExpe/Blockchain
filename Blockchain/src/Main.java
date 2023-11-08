// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        Blockchain chain = new Blockchain();
         //int ret = chain.checkBlockchain();

        Block first = new Block("asbjkd", "first", 0.5);
        first.calcHash(chain.getPreviousBlock());
        //chain.addBlock(first);
        //chain.addBlock(first);
        //chain.addBlock(first);

        chain.checkBlockchain();

    }
}