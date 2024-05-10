// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
/*
        Blockchain chain = new Blockchain();
        chain.generateGenesisBlock();

       // System.out.println("HALO");

        Data data = new Data(chain);
        try {
            data.exportChain("chain");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
*/
        // Bestehende Blockchain einbinden und weitere Blöcke hinzufügen
        Data data2 = new Data();
        try {
            Blockchain chain2 = data2.importChain("chain.json");
            chain2.generateBlock("Test", "second", 2.1);

            Data export = new Data(chain2);
            export.exportChain("chain2");


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}