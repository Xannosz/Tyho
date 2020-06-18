package hu.xannosz.tyho;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hu.xannosz.microtools.pack.Douplet;
import lombok.Getter;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

public class Db {

    @Getter
    private Configuration configuration = new Configuration();
    private Data data;

    public void getData() {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        try {
            JsonObject confObject = (JsonObject) parser.parse(FileUtils.readFileToString(new File(Constants.CONFIGURATION_FILE)));
            configuration = gson.fromJson(confObject, Configuration.class);
        } catch (Exception e) {
            e.printStackTrace();
            configuration = new Configuration();
        }

        try {
            if (configuration.isUseExternalDb()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(new URL(configuration.getExternalDbQueryURL()).openStream()));
                StringBuilder json = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    json.append(inputLine);
                in.close();
                JsonObject dbObject = (JsonObject) parser.parse(json.toString());
                data = gson.fromJson(dbObject, Data.class);
            } else {
                JsonObject dbObject = (JsonObject) parser.parse(FileUtils.readFileToString(new File(Constants.DB_FILE)));
                data = gson.fromJson(dbObject, Data.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            data = new Data();
        }
    }

    public String getToken(String uName, String pwd) {
        if (verifyPassword(uName, pwd)) {
            String token = createToken();
            data.getTokenUsersExpires().put(token, new Douplet<>(uName, new Date()));
            return token;
        }
        return null;
    }

    public String modify(String uName, String oPwd, String nPwd, String nPwdA) {
        if (nPwd.equals(nPwdA) && verifyPassword(uName, oPwd)) {
            String hashed = encodePassword(nPwd);
            if (hashed == null) {
                return Constants.FAILED;
            }
            data.getUserPasswords().put(uName, hashed);
            return Constants.SUCCESS;
        }
        return Constants.FAILED;
    }

    public String registration(String uName, String pwd, String pwdA) {
        if (pwd.equals(pwdA) && !data.getUserPasswords().containsKey(uName)) {
            String hashed = encodePassword(pwd);
            if (hashed == null) {
                return Constants.FAILED;
            }
            data.getUserPasswords().put(uName, hashed);
            if (configuration.isUseDefaultGroup()) {
                data.getUserGroups().put(uName, Collections.singleton(configuration.getDefaultGroupName()));
            }
            return Constants.SUCCESS;
        }
        return Constants.FAILED;
    }


    public String getAccess(String token, String access) {
        Douplet<String, Date> user = data.getTokenUsersExpires().get(token);
        if (user == null) {
            return Constants.TOKEN_NOT_EXISTS;
        }
        if (user.getSecond().getTime() + configuration.getExpireTime() < (new Date()).getTime()) {
            return Constants.TOKEN_EXPIRED;
        }
        data.getTokenUsersExpires().put(token, new Douplet<>(user.getFirst(), new Date()));
        if (!getPrivileges(user.getFirst()).contains(access)) {
            return Constants.ACCESS_DENIED;
        }
        return Constants.ACCESS_GRANTED;
    }

    public void clean() {
        Set<String> tokens = new HashSet<>();
        for (Map.Entry<String, Douplet<String, Date>> token : data.getTokenUsersExpires().entrySet()) {
            if (token.getValue().getSecond().getTime() + configuration.getDeletionTime() < (new Date()).getTime()) {
                tokens.add(token.getKey());
            }
        }
        for (String token : tokens) {
            data.getTokenUsersExpires().remove(token);
        }
    }

    private Set<String> getPrivileges(String user) {
        Set<String> result = new HashSet<>();
        Set<String> groups = data.getUserGroups().get(user);
        if (groups != null) {
            for (String group : groups) {
                Set<String> privileges = data.getGroupPrivileges().get(group);
                if (privileges != null) {
                    result.addAll(privileges);
                }
            }
        }
        Set<String> privileges = data.getUserPrivileges().get(user);
        if (privileges != null) {
            result.addAll(privileges);
        }
        return result;
    }

    private String createToken() {
        StringBuilder builder = new StringBuilder();
        builder.append(UUID.randomUUID());
        for (int i = 0; i < configuration.getTokenLength(); i++) {
            builder.append("-");
            builder.append(UUID.randomUUID());
        }
        return builder.toString();
    }

    private boolean verifyPassword(String user, String password) {
        try {
            return data.getUserPasswords().containsKey(user) && Password.check(password, data.getUserPasswords().get(user));
        } catch (Exception e) {
            return false;
        }
    }

    private String encodePassword(String password) {
        try {
            return Password.getSaltedHash(password);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void writeData() {
        clean();
        Gson gson = new Gson();

        try {
            new File(Constants.DATA_DIR).mkdir();
            if (!configuration.isUseExternalDb()) {
                new File(Constants.DB_FILE).createNewFile();
            }
            new File(Constants.CONFIGURATION_FILE).createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileUtils.writeStringToFile(new File(Constants.CONFIGURATION_FILE), gson.toJson(configuration));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (configuration.isUseExternalDb()) {
                URLConnection connection = new URL(configuration.getExternalDbSetURL()).openConnection();
                connection.setDoOutput(true);

                OutputStreamWriter out = new OutputStreamWriter(
                        connection.getOutputStream());
                out.write(URLEncoder.encode(gson.toJson(data), Constants.ENCODING));
                out.close();
            } else {
                FileUtils.writeStringToFile(new File(Constants.DB_FILE), gson.toJson(data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
