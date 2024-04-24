package dht;

public class FindValue extends Message{

    //unique field for FindValue
    private String targetUid;

    //constructor
    public FindValue(String type, String srcAddr, int srcPort, String targetUid) {
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
