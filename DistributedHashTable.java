/*
 * Copyright (C) 2023 - 2024  Zachary A. Kissel 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License 
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package dht;

import java.io.PrintWriter;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
* This class performs all the DHT actions.
 * @param <bootStrapNodeAddress>
 * @param <bootStrapNodePort>
 */
public class DistributedHashTable 
{
    private Thread service;             // The thread that is service requests.
    private String uid;                 // The nodes uid.
     // private RoutingTable routes;        // The routing table for the node.
    private KeyValueStore kvStore;      // The key-value store for this node.
    private int port;                   // The port this node is accepting traffic on.
    private RoutingTable routingTable;
    //private KeyStore keyValueStore;
    // private String key;
    private String addr;                   // The address of this node.
    private String bootStrapNodeAddress; // The port of the bootstrapnaode
    private int bootStrapNodePort;
    private int numNodes;

    public int getPort() {
        return port;
    }


    public void setPort(int port) {
        this.port = port;
    }
    
    public String getBootStrapNodeAddress() {
        return bootStrapNodeAddress;
    }


    public void setBootStrapNodeAddress(String bootStrapNodeAddress) {
        this.bootStrapNodeAddress = bootStrapNodeAddress;
    }
    

    public int getBootStrapNodePort() {
        return bootStrapNodePort;
    }


    public void setBootStrapNodePort(int bootStrapNodePort) {
        this.bootStrapNodePort = bootStrapNodePort;
    }


    /**
     * Construct a new DHT node that listens for incomming traffic on the 
     * given port and bootstraps using the given bootstrap address and port. 
     * @param port the port for the service to listen on.
     * @param uid the UID of this node.
     * @param bootAddr the address of the bootstrap node.
     * @param bootPort the port of the bootsrap node.
     * @throws IOException 
     */
    public DistributedHashTable(int port, String addr, String uid, String bootAddr, int bootPort) throws IOException
    {
        this.port = port;
        this.addr = addr;
        this.uid = uid;
        this.bootStrapNodeAddress = bootAddr;
        this.bootStrapNodePort = bootPort;
        this.routingTable = new RoutingTable(bootPort);

        this.kvStore = new KeyValueStore();

        service = new Thread(() -> {
            // Implement service logic here



        });
        service.start();
    }

    
    /**
     * Put a key-value pair into the DHT.
     * @param key the key to place into the table. 
     * @param value the value to associate with the key in the table. 
     * @return {@code true} if the insertion was successful and {@code false} otherwise.
     */
    public boolean put(String key, String value)
    { 
      int hashcode = calculateHashCode(key);
      int responsibleNode = findResponsibleNode(hashcode);

      boolean success = storeKeyValuePair(responsibleNode, key, value);

        //store key-value pair in local key-value store
        kvStore.put(key, value);
        // issue "statement-store" message to KClosestPeers
        List<Host> ClosestPeers = this.routingTable.getKClosest(key, 3);
        for (Host peer: ClosestPeers) {
           // Send "statementstore message to each peer"
           // use TCP socket to connect to peer and send message \
           try (Socket socket = new Socket(peer.getAddress(), peer.getPort());
               PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
               ) {
                   out.println("statementstore");
                   out.println(key);
                   out.println(value);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }  

      return success;
    }

    private boolean storeKeyValuePair(int responsibleNode, String key, String value) {
        return responsibleNode >= 0;
        }


    /**
     * Get the value associated with the key from the DHT.
     * @param key the key to look for in the DHT.
     * @return the value associated with the key. A value of {@code null} is 
     * returned if the key is not found in the DHT.
     */
    public String get(String key)
    {

        int hashcode = calculateHashCode(key);
        int responsibleNode = findResponsibleNode(hashcode);
  
        retrieveValue(responsibleNode, key);
     // Check local key-value store 
     // return value or declare key as not found 
     String result = KeyValueStore.get(key);
     if(result != null) {
        return result;
     }
        List<Host> closestPeers = this.routingTable.getKClosest(key, 3);
        // Other operations as specified 
        // Check KClosesest neighbors
       
        for (Host peer: closestPeers) 
      
        // send "get" message to each peer
        // use TCP Socket to connect to peer and send message
        try (Socket socket = new Socket(peer.getAddress(), peer.getPort());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
             )  { 
                out.println("get");
                out.println(key);
                // receive respomse 
                String response = in.readLine();
                if(!response.equals("key not found")) {
                   // return response;
                }
            } catch (IOException e) {
            e.printStackTrace();
            }
            return null;
        
    }

    // key not found


    private int calculateHashCode(String key) {
       return key.hashCode();
    }


    private int findResponsibleNode(int hashcode) {
        return hashcode % numNodes;
    }


    private String retrieveValue(int responsibleNode, String key) {
    return null;    
    }


    public DistributedHashTable(String bootStrapNodeAddress, int bootStrapNodePort) {
        
    }

    /**
     * Pretty print the routing table to the screen.
     */
    public String getRoutes()
    {
        // implemented.
    //     return routes.getRoutes();
        return "";
    }

    /**
     * Get a pretty printable version of the kv store.
     */
    public String getKVStore()
    {
        String res = kvStore.toString();
        
        if (res.isEmpty())
            return "Empty";
        return res;
    }

    public String getService()
    {
       String s = service.toString();
        
        if (s.isEmpty())
            return "Empty";
        return s;
    }
    public String getUid()
    {
        String u = uid.toString();
        
        if (u.isEmpty())
            return "Empty";
        return u;
    }  
   
    public String getAddr()
    {
        String a = addr.toString();
        
        if (a.isEmpty())
            return "Empty";
        return a;
    }
}

