package dht;

public class Node extends Message{

    //unique attribute for Node
    private Host[] hosts;

    //constructor
    public Node(String type, String srcAddr, int srcPort, Host[] hosts) {
        super(type, srcAddr, srcPort);
        this.hosts = hosts;
    }

    //getter and setter
    public Host[] getHosts() {
        return hosts;
    }

    public void setHosts(Host[] hosts) {
        this.hosts = hosts;
    }

    
    
}
