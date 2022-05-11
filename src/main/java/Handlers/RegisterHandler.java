package Handlers;

import DAO.DataAccessException;
import Request.RegisterRequest;
import Result.RegisterResult;
import Services.ServeRegister;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Locale;

public class RegisterHandler extends Handler {
    // TODO: POST

    /*
    * 1. Go through handler class and call the Request Class to create the String Request
    * 2. Pass the Request into the Services Class to connect to the Database
    * 3. Add the User to the Database
    * 4. Then create the Result String in the Result class and pass that back to the Handler using Services
    * 5. Use writeString function to write the Response to the Server
    * */

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                //System.out.println(reqData);

                // TODO: Register a User based on the request data
                Gson gson = new Gson();
                RegisterRequest request = gson.fromJson(reqData, RegisterRequest.class);

                ServeRegister service = new ServeRegister();
                RegisterResult result = service.passToDAO(request);

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