package Handlers;

import DAO.DataAccessException;
import Result.FillResult;
import Services.ServeFill;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Locale;

public class FillHandler extends Handler {
    // TODO: POST

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("post")) {
                String getInfo = exchange.getRequestURI().toString();
                String[] separateValues = getInfo.split("/");
                String username = separateValues[2];
                int generations;
                if (separateValues.length > 3) {
                    generations = Integer.parseInt(separateValues[3]);
                }
                else {
                    generations = 4;
                }

                Gson gson = new Gson();

                ServeFill service = new ServeFill();
                FillResult result = service.passToDAO(username, generations);

                if (result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
//                OutputStream resBody = exchange.getResponseBody();
//                writeString(gson.toJson(result), resBody);
//                resBody.close();
                OutputStreamWriter resBody = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, resBody);
                resBody.close();
            }
        }
        catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
