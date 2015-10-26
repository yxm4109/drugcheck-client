package net.ucopy.drugcheck;

import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import com.db4o.cs.Db4oClientServer;

import net.ucopy.drugcheck.exception.ConnectionTimeoutException;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by meituan on 15/10/26.
 * net.ucopy.drugcheck
 */
public enum Db4oHelper {

    getInstance;

    private int maxConn = 15;

    private ConcurrentLinkedQueue<ObjectContainer> connectionQueue;

    private ObjectServer server;

    {
        server = Db4oClientServer.openServer(Db4oClientServer
                .newServerConfiguration(), "database.yap", 0);
        connectionQueue = new ConcurrentLinkedQueue<>();

    }

    /**
     * get connection
     *
     * @return ObjectContainer
     * @throws ConnectionTimeoutException
     * @throws InterruptedException
     */
    public synchronized ObjectContainer getConnection() {

        ObjectContainer objectContainer = connectionQueue.poll();

        if (null == objectContainer) {
            if (connectionQueue.size() < maxConn) {
                objectContainer = server.openClient();
            }
        }

        return objectContainer;
    }

    /**
     * release connection
     *
     * @return ObjectContainer
     * @throws InterruptedException
     */
    public synchronized void releaseConnection(ObjectContainer objectContainer) {
        connectionQueue.offer(objectContainer);
    }

    void close() {
        server.close();
    }

}
