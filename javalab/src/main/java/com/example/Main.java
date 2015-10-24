package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        String filePath="D:/workspace/android_workspace/AuthCodeProvider/src/testdata/2.jpg";

        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("127.0.0.1",8889));
            OutputStream outputStream =socket.getOutputStream();

            byte[] bytes = getContent(filePath);
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static byte[] getContent(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }
        fi.close();
        return buffer;
    }

}


