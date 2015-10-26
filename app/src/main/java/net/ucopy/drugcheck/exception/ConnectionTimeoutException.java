package net.ucopy.drugcheck.exception;

/**
 * Created by meituan on 15/10/26.
 * net.ucopy.drugcheck.exception
 */

public class ConnectionTimeoutException extends Exception {

    public ConnectionTimeoutException() {
    }

    public ConnectionTimeoutException(String s) {
        super(s);
    }
}
