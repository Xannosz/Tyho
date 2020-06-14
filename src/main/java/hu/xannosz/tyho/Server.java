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
    public void createServer(int port) {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new MainHandler());
            server.createContext("/css", new CSSHandler());
            server.createContext("/rest", new MainHandler());
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
            if (page.equals("login")) {
                response = new Douplet<>(200, String.format(Constatns.LOGIN, map.get("css")));
            } else if (page.equals("registration")) {
                response = new Douplet<>(200, String.format(Constatns.REGISTRATION, map.get("css")));
            } else if (page.equals("modify")) {
                response = new Douplet<>(200, String.format(Constatns.MODIFY, map.get("css")));
            } else if (page.equals("execute")) {
                response = new Douplet<>(200, "Map: "+ map);
            } else {
                response = new Douplet<>(404, page + " page not found.");
            }
            byte[] responseSyntax = response.getSecond().getBytes(Constatns.ENCODING);
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
                IOUtils.copy(requestBody, writer, Constatns.ENCODING);
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
                return URLDecoder.decode(input, Constatns.ENCODING);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return input;
            }
        }
    }

    public class CSSHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String[] tags = t.getRequestURI().getPath().split("/");
            String id = tags[tags.length - 1];
            Douplet<Integer, String> response;
            String theme = ThemeHandler.getTheme(id);
            if (theme != null) {
                response = new Douplet<>(200, theme);
            } else {
                response = new Douplet<>(404, id + " theme not found.");
            }
            byte[] responseSyntax = response.getSecond().getBytes(Constatns.ENCODING);
            t.sendResponseHeaders(response.getFirst(), responseSyntax.length);
            OutputStream os = t.getResponseBody();
            os.write(responseSyntax);
            os.flush();
            os.close();
        }
    }
}
