package dht;

public class Value extends Message{

    //unique attributes of Value
    private String key;
    private String value;

    //constructor
    public Value(String type, String srcAddr, int srcPort, String key, String value) {
        super(type, srcAddr, srcPort);
        this.key = key;
        this.value = value;
    }

    //getters and setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    
    
}
