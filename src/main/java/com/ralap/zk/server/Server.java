package com.ralap.zk.server;

import java.io.IOException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;

/**
 * Created by ralap on 2017/12/1.
 */
public class Server {

    private static final String CONNCETSTRING = "192.168.33.2:2181,192.168.33.3:2181,192.168.33.4:2181";
    private static final int TIMEOUT = 2000;
    private ZooKeeper zooKeeper;


    /***
     * 获取连接
     * @throws IOException
     */
    public void getConnction() throws IOException {
        zooKeeper = new ZooKeeper(CONNCETSTRING, TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType() + "-----" + watchedEvent.getPath());
            }
        });
    }


    public static void main(String[] args) throws Exception {

        Server server = new Server();

        server.getConnction();

        server.upLine(args[0]);

        System.out.println(args[0] + "Server UpLine......");
        server.work();

    }

    private void upLine(String arg) throws Exception {
        zooKeeper.create("/servers/" + arg, arg.getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
    }


    public void work() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }
}
