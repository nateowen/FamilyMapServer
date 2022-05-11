package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.util.Locale;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("get")) {
                String filePath = "web" + exchange.getRequestURI().toString();
                if (filePath.equals("web/")) {
                    filePath = "web/index.html";
                }
                File file = new File(filePath);
                if (file.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream fileOut = exchange.getResponseBody();
                    Files.copy(file.toPath(), fileOut);
                    fileOut.close();
                    success = true;
                }
            }
            NotFound(exchange, success);
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    static void NotFound(HttpExchange exchange, boolean success) throws IOException {
        if (!success) {
            File file = new File("web/HTML/404.html");
            if (file.exists()) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                OutputStream fileOut = exchange.getResponseBody();
                Files.copy(file.toPath(), fileOut);
                fileOut.close();
            }
        }
    }
}
