package Handlers;

import DAO.DataAccessException;
import Result.EventIDResult;
import Services.ServeEventID;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Locale;

public class EventIDHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                String getInfo = exchange.getRequestURI().toString();
                String[] separateValues = getInfo.split("/");
                String eventID = separateValues[2];
                String authtoken = reqHeaders.getFirst("Authorization");

                Gson gson = new Gson();

                ServeEventID service = new ServeEventID();
                EventIDResult result = service.passToDAO(eventID, authtoken);

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
