package Handlers;

import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public abstract class Handler implements HttpHandler {
    // This function reads in the JSON String Request Body and turns it into a normal Java string
    // so that the Server Classes can read the request.
    public String readString(InputStream input) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(input);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    // This function takes the Response Body and writes it to the Server
    public void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
