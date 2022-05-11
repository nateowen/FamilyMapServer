package Handlers;

import DAO.DataAccessException;
import Request.RegisterRequest;
import Result.ClearResult;
import Result.RegisterResult;
import Services.ServeClear;
import Services.ServeRegister;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Locale;

public class ClearHandler extends Handler {

    // TODO: POST

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("post")) {
                // TODO: Clear database
                Gson gson = new Gson();

                ServeClear service = new ServeClear();
                ClearResult result = service.clearDatabase();

                if (result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                OutputStream resBody = exchange.getResponseBody();
                writeString(gson.toJson(result), resBody);
                resBody.close();
            }
        }
        catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR,0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
