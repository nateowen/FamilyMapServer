package Handlers;

import DAO.DataAccessException;
import Request.RegisterRequest;
import Result.EventIDResult;
import Result.EventResult;
import Result.RegisterResult;
import Services.ServeEvent;
import Services.ServeEventID;
import Services.ServePerson;
import Services.ServeRegister;
import com.google.gson.Gson;
import com.sun.jdi.request.EventRequest;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Locale;

public class EventHandler extends Handler {

    // TODO: GET

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                String authToken = reqHeaders.getFirst("Authorization");

                Gson gson = new Gson();

                ServeEvent service = new ServeEvent();
                EventResult result = service.passToDAO(authToken);

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
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
