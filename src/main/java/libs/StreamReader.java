package libs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class StreamReader implements Runnable {
    private InputStream ipStream;
    private Consumer<String> consumer;

    public StreamReader(InputStream ipStream, Consumer<String> consumer) {
        this.ipStream = ipStream;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        new BufferedReader(new InputStreamReader(ipStream)).lines().forEach(consumer);
    }
}
