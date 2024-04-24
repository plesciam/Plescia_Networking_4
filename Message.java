package dht;

public class Message {
    
    private String type;
    private String srcAddr;
    private int srcPort;

    //constructor for message
    public Message(String type, String srcAddr, int srcPort){
        this.type = type;
        this.srcAddr = srcAddr;
        this.srcPort = srcPort;
    }

    //getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrcAddr() {
        return srcAddr;
    }

    public void setSrcAddr(String srcAddr) {
        this.srcAddr = srcAddr;
    }

    public int getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(int srcPort) {
        this.srcPort = srcPort;
    }

    
}
