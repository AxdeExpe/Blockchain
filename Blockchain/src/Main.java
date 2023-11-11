// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        int cores = Runtime.getRuntime().availableProcessors();
        Thread[] t = new Thread[cores];

        Blockchain chain = new Blockchain();

        //Block first = new Block(chain.getPreviousBlock(),0.0, Double.MAX_VALUE, "asbjkd", "first", 0.5);
        //first.mineBlock(chain.getPreviousBlock());
/*
        double fraction = Double.MAX_VALUE / cores;

        Block first = null;

        for(int i = 0; i < cores; i++){
            double start = i * fraction;
            double end = (i+1) * fraction;
            first = new Block(chain.getPreviousBlock(),start, end, "asbjkd", "first", 0.5);
            t[i] = new Thread(first);
            t[i].start();
        }

*/

       // chain.addBlock(first);
        //chain.addBlock(first);
        //chain.addBlock(first);

/*
        Block second = new Block("328r79", "second", 1.0);
        second.mineBlock(chain.getPreviousBlock());
        chain.addBlock(second);

        Block third = new Block("fsf", "third", 9.0);
        third.mineBlock(chain.getPreviousBlock());
        chain.addBlock(third);
*/
    }
}