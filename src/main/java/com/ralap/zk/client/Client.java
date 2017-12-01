package com.ralap.zk.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by ralap on 2017/12/1.
 */
public class Client {

    private static final String CONNCETSTRING = "192.168.33.2:2181,192.168.33.3:2181,192.168.33.4:2181";
    private static final int TIMEOUT = 2000;
    private ZooKeeper zooKeeper;
    private volatile List<String> serverList;


    /***
     * 获取连接
     * @throws IOException
     */
    public void getConnction() throws IOException {
        zooKeeper = new ZooKeeper(CONNCETSTRING, TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    getServerList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void getServerList() throws Exception {
        List<String> children = zooKeeper.getChildren("/servers", true);
        List<String> servers = new ArrayList<String>();
        for (String child : children) {
            byte[] data = zooKeeper.getData("/servers/" + child, false, null);
            servers.add(new String(data));
        }
        serverList = servers;
        System.out.println(serverList);

    }


    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.getConnction();
        client.getServerList();
        client.work();
    }

    public void work() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }
}
