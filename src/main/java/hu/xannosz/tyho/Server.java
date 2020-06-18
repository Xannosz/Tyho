package hu.xannosz.tyho;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import hu.xannosz.microtools.pack.Douplet;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private Db db = new Db();

    public void createServer(int port) {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new MainHandler());
            server.createContext("/css", new CSSHandler());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class MainHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String[] tags = t.getRequestURI().getPath().split("/");
            String page = tags[tags.length - 1];
            Map<String, String> map = getRequestMap(t.getRequestBody());
            Douplet<Integer, String> response;
            if (page.equals(Constants.LOGIN_PATH)) {
                response = new Douplet<>(200, String.format(Constants.LOGIN, map.get(Constants.CSS), map.get(Constants.SUCCESS), map.get(Constants.FAILED)));
            } else if (page.equals(Constants.REGISTRATION_PATH)) {
                db.getData();
                if (db.getConfiguration().isEnableRegistration()) {
                    response = new Douplet<>(200, String.format(Constants.REGISTRATION, map.get(Constants.CSS), map.get(Constants.SUCCESS), map.get(Constants.FAILED)));
                } else {
                    response = new Douplet<>(404, page + " page not found.");
                }
            } else if (page.equals(Constants.MODIFY_PATH)) {
                response = new Douplet<>(200, String.format(Constants.MODIFY, map.get(Constants.CSS), map.get(Constants.SUCCESS), map.get(Constants.FAILED)));
            } else if (page.equals(Constants.EXECUTE_PATH)) {
                response = new Douplet<>(200, execute(map));
            } else if (page.equals("null")) {
                response = new Douplet<>(200, "Map: " + map);
            } else if (page.equals(Constants.VALIDATE_PATH)) {
                db.getData();
                Map<String, String> input = getRequestMap(new ByteArrayInputStream(t.getRequestURI().getQuery().getBytes(Constants.ENCODING)));
                response = new Douplet<>(200, db.getAccess(input.get(Constants.TOKEN), input.get(Constants.ACCESS)));
            } else {
                response = new Douplet<>(404, page + " page not found.");
            }
            byte[] responseSyntax = response.getSecond().getBytes(Constants.ENCODING);
            t.sendResponseHeaders(response.getFirst(), responseSyntax.length);
            OutputStream os = t.getResponseBody();
            os.write(responseSyntax);
            os.flush();
            os.close();
        }

        private Map<String, String> getRequestMap(InputStream requestBody) {
            StringWriter writer = new StringWriter();
            Map<String, String> result = new HashMap<>();

            try {
                IOUtils.copy(requestBody, writer, Constants.ENCODING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (String field : writer.toString().split("&")) {
                String[] fields = field.split("=");
                if (fields.length == 1) {
                    result.put(characterRecombinator(fields[0]), "");
                } else if (fields.length == 2) {
                    result.put(characterRecombinator(fields[0]), characterRecombinator(fields[1]));
                }
            }

            return result;
        }

        private String characterRecombinator(String input) {
            try {
                return URLDecoder.decode(input, Constants.ENCODING);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return input;
            }
        }
    }

    private String execute(Map<String, String> map) {
        db.getData();
        String token = null;
        String message = null;
        if (map.get(Constants.TYPE).equals(Constants.LOGIN_PATH)) {
            token = db.getToken(map.get(Constants.U_NAME), map.get(Constants.PWD));
        } else if (map.get(Constants.TYPE).equals(Constants.REGISTRATION_PATH) && db.getConfiguration().isEnableRegistration()) {
            if (db.registration(map.get(Constants.U_NAME), map.get(Constants.PWD), map.get(Constants.PWD_A)).equals(Constants.SUCCESS)) {
                token = db.getToken(map.get(Constants.U_NAME), map.get(Constants.PWD));
            } else {
                message = "Registration failed";
            }
        } else if (map.get(Constants.TYPE).equals(Constants.MODIFY_PATH)) {
            if (db.modify(map.get(Constants.U_NAME), map.get(Constants.O_PWD), map.get(Constants.N_PWD), map.get(Constants.N_PWD_A)).equals(Constants.SUCCESS)) {
                token = db.getToken(map.get(Constants.U_NAME), map.get(Constants.N_PWD));
            } else {
                message = "Password Modification failed";
            }
        }
        db.writeData();
        return String.format(db.getConfiguration().isEnableTokenInCookie() ? Constants.REDIRECT_WITH_COOKIE : Constants.REDIRECT_WITHOUT_COOKIE
                , token == null ? map.get(Constants.FAILED) : map.get(Constants.SUCCESS), token, message, token);
    }

    public class CSSHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String[] tags = t.getRequestURI().getPath().split("/");
            String id = tags[tags.length - 1];
            Douplet<Integer, String> response;
            String theme = ThemeHandler.getTheme(db.getConfiguration().isEnableCSSSetting() ? id : "null");
            if (theme != null) {
                response = new Douplet<>(200, theme);
            } else {
                response = new Douplet<>(404, id + " theme not found.");
            }
            byte[] responseSyntax = response.getSecond().getBytes(Constants.ENCODING);
            t.sendResponseHeaders(response.getFirst(), responseSyntax.length);
            OutputStream os = t.getResponseBody();
            os.write(responseSyntax);
            os.flush();
            os.close();
        }
    }
}
