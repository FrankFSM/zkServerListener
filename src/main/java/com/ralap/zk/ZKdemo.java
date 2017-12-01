package com.ralap.zk;

import java.io.IOException;
import java.util.List;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ralap on 2017/11/30.
 */
public class ZKdemo {

    private static final String CONNCETSTRING = "192.168.33.2:2181,192.168.33.3:2181,192.168.33.4:2181";
    private static final int TIMEOUT = 2000;
    private ZooKeeper zooKeeper = null;


    @Before
    public void init() throws IOException {
        zooKeeper = new ZooKeeper(CONNCETSTRING, TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType() + "-----" + watchedEvent.getPath());
            }
        });
    }

    @Test
    public void add() throws Exception {

        String s = zooKeeper
                .create("/idea", "idea".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(s);
    }

    @Test
    public void getChildren() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/", true);
        System.out.println(children);
    }
}
