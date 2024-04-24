package dht;

import java.io.InvalidObjectException;

import merrimackutil.json.JSONSerializable;
import merrimackutil.json.types.JSONObject;
import merrimackutil.json.types.JSONType;

public class Host implements JSONSerializable{
 
    private String addr;    //IP address of the port
    private int port;   //port number of host
    private String uid;  //Base64 UID


    //gets the host 
    public Host(JSONObject obj) throws InvalidObjectException{
        deserialize(obj);
    }

    public Host(String senderHost, int senderPort) {
    }

    //gets address
    public String getAddress(){
        return addr;
    }

    //gets port
    public int getPort(){
        return port;
    }

    public String getUid(){
        return uid;
    }

    //changes JSON message to different data type
    @Override
    public void deserialize(JSONType arg0) throws InvalidObjectException {
        JSONObject entry;
        if (arg0 instanceof JSONObject) {
            entry = (JSONObject) arg0;

            if (!entry.containsKey("address"))
                throw new InvalidObjectException("Host needs an address address.");
            else
                addr = entry.getString("address");

            if (!entry.containsKey("port"))
                throw new InvalidObjectException("Host  needs a port.");
            else
                port = entry.getInt("port");

            if(!entry.containsKey("uid"))
                throw new InvalidObjectException("Host needs a uid");
            else
                uid = entry.getString("uid");

            if (entry.size() > 2)
                throw new InvalidObjectException("Superflous fields");

        } else
            throw new InvalidObjectException(
                    "Host Entry -- recieved array, expected Object.");
    }

    //serialize to encoded JSON
    @Override
    public String serialize() {
        return toJSONType().getFormattedJSON();
    }


    //makes object to JSON
    @Override
    public JSONType toJSONType() {
        
        
        JSONObject object = new JSONObject();

        //sets the characteristics of the JSON object
        object.put("address", addr);
        object.put("port", port);
        object.put("uid", uid);

        //returns the object
        return object;
    }

    
}
