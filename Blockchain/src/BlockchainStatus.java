public class BlockchainStatus {

    private volatile boolean hashFound = false;

    public boolean isHashFound() {
        return hashFound;
    }

    public void setHashFound(boolean hashFound) {
        this.hashFound = hashFound;
    }

}
