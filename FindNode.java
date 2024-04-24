package dht;

public class FindNode extends Message {

    //unique field for FindNode
    private String targetUid;

    //constructor
    public FindNode(String type, String srcAddr, int srcPort, String targetUid) {
        super(type, srcAddr, srcPort);
        this.targetUid = targetUid;

    }

    //getter and setter
    public String getTargetUid() {
        return targetUid;
    }

    public void setTargetUid(String targetUid) {
        this.targetUid = targetUid;
    }
    
    
}
