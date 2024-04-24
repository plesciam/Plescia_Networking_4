package dht;

import java.util.ArrayList;
import java.util.List;


public class RoutingTable {

 private List<Host> hosts;
int k;

    public RoutingTable(int k) {
        this.hosts = new ArrayList<>();
        this.k = k;
    }

    public synchronized void addHostsToTable(Host host)  {
        hosts.add(host);
    }
    public synchronized void addArrayOfHosts(List<Host> hostsList) {
     hosts.addAll(hostsList);
    
    }
    public synchronized List<Host> getKClosest(String uid, int i)  {
        return new ArrayList<>();
        }

    public synchronized String route() {
        
        return null; //does not return null just written to avoid error for now
    }
}

