package Handlers;

import DAO.DataAccessException;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;
import Services.ServeLogin;
import Services.ServeRegister;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Locale;

public class LoginHandler extends Handler {

    // TODO: POST

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);

                // TODO: Login a User based on the request data
                Gson gson = new Gson();
                LoginRequest request = gson.fromJson(reqData, LoginRequest.class);

                ServeLogin service = new ServeLogin();
                LoginResult result = service.passToDAO(request);

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
