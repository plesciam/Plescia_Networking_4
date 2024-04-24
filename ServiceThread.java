package dht;

import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

    public class ServiceThread<in> implements Runnable {
    
        private int port;
        private String uid;
        private RoutingTable routingTable;
        
        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

       
        public RoutingTable getRoutingTable() {
            return routingTable;
        }

        public void setRoutingTable(RoutingTable routingTable) {
            this.routingTable = routingTable;
        }

        private KeyStore keyValueStore;
        private ServiceThread<in>.DistributedHashTable keyStore;
        private Socket clientSocket;
    

    public KeyStore getKeyValueStore() {
        return keyValueStore;
    }

    public void setKeyValueStore(KeyStore keyValueStore) {
        this.keyValueStore = keyValueStore;
    }

    public ServiceThread(String address, int port, String uid, RoutingTable routingTable, KeyStore keyValueStore) {

        this.port = port; 
        this.uid = uid; 
        this.routingTable = routingTable;
        this.keyValueStore = keyValueStore;
    }
  @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
                while(true) {
                    Socket ClientSocket = serverSocket.accept(); 
                    handleConnection(ClientSocket);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
            }  
        }
    
    private void handleConnection(Socket clienSocket) {
        
       // Socket clientSocket;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clienSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clienSocket.getOutputStream(), true);
            
                // Read message type
                String messageType = in.readLine();

                // Process message based on message type
                switch (messageType) {
                    case "put":
                    // put operation 
                    String key = in.readLine();
                    String value = in.readLine();
                       // DistributedHashTable kvStore;
                        keyStore.put(key, value);
                
                
                    // Update routing table with senders host nextLine information
                
            
               updateRoutingTable(clientSocket);
                // Send response 
                out.println("Put operation completed successfully");
                break; 
                case "get":
                // Implement get operation
                key = in.readLine();
                String result = KeyValueStore.get(key);
                if (result != null ) {
                    // send value if found
                    out.println(result);
                } else {
                    // Send "not found" message"
                    out.println("Key not found");
                }
                // Update routing table with senders host information

                updateRoutingTable(clientSocket);
                break;

                // add other message types as needed
            }
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
        }
     }
        private void updateRoutingTable(Socket clientSocket) {
            // Update routing table with sender's host information
            String senderHost = clientSocket.getInetAddress().getHostAddress();
            int senderPort = clientSocket.getPort();
            Host sender = new Host(senderHost, senderPort);
            routingTable.addHostsToTable(sender);
    }
    
    public class DistributedHashTable {
    
        private RoutingTable routingTable;
        private ServiceThread<in>.DistributedHashTable kvStore;
    
    
     public DistributedHashTable(String bootStrapAdress, int bootStrapPort, String uid) {
 
     // Initialize Routing Table and key-value store 
 
     // addb bootStrapNode to the routing table
 
     // FindNode operation
 
     }

     public void put(String key, String value) {
     //store key-value pair in local key-value store
     kvStore.put(key, value);
     // issue "statement-store" message to KClosestPeers
     List<Host> ClosestPeers = routingTable.getKClosest(key, 3);
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

            throw new UnsupportedOperationException("Unimplemented method 'put'");

            }
        }  
    }

     public String get(String key) {
     // Check local key-value store 
     // return value or declare key as not found 
     String result = KeyValueStore.get(key);
     if(result != null) {
        return result;
     
     }
     // Other operations as specified 
      // Check KClosesest neighbors
      List<Host> closestPeers = routingTable.getKClosest(key, 3);
      for (Host peer: closestPeers) 
      {
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
    }
    // key not found
    return null;
}
     }
// other operation as specified
}
    