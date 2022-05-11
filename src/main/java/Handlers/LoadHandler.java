package Handlers;

import DAO.DataAccessException;
import Request.LoadRequest;
import Request.RegisterRequest;
import Result.LoadResult;
import Result.RegisterResult;
import Services.ServeLoad;
import Services.ServeRegister;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler extends Handler {
    // TODO: POST

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            InputStream reqBody = exchange.getRequestBody();
            String reqData = readString(reqBody);
            //System.out.println(reqData);

            // TODO: Register a User based on the request data
            Gson gson = new Gson();
            LoadRequest request = gson.fromJson(reqData, LoadRequest.class);

            ServeLoad service = new ServeLoad();
            LoadResult result = service.passToDAO(request);

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
        catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR,0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
