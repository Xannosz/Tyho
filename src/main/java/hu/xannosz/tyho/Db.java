package hu.xannosz.tyho;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hu.xannosz.microtools.pack.Douplet;
import lombok.Getter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Db {

    @Getter
    private Configuration configuration = new Configuration();
    private Data data;

    public void getData() {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        try {
            JsonObject dbObject = (JsonObject) parser.parse(FileUtils.readFileToString(new File(Constants.DB_FILE)));
            data = gson.fromJson(dbObject, Data.class);
        } catch (Exception e) {
            e.printStackTrace();
            data = new Data();
        }

        try {
            JsonObject confObject = (JsonObject) parser.parse(FileUtils.readFileToString(new File(Constants.DB_FILE)));
            configuration = gson.fromJson(confObject, Configuration.class);
        } catch (Exception e) {
            e.printStackTrace();
            configuration = new Configuration();
        }
    }

    public String getToken(String uName, String pwd) {
        if (data.getUserPasswords().containsKey(uName) && data.getUserPasswords().get(uName).equals(pwd)) {
            String token = createToken();
            data.getTokenUsersExpires().put(token, new Douplet<>(uName, new Date()));
            return token;
        }
        return null;
    }

    public String modify(String uName, String oPwd, String nPwd, String nPwdA) {
        if (nPwd.equals(nPwdA) && data.getUserPasswords().containsKey(uName) && data.getUserPasswords().get(uName).equals(oPwd)) {
            data.getUserPasswords().put(uName, nPwd);
            return Constants.SUCCESS;
        }
        return Constants.FAILED;
    }

    public String registration(String uName, String pwd, String pwdA) {
        if (pwd.equals(pwdA) && !data.getUserPasswords().containsKey(uName)) {
            data.getUserPasswords().put(uName, pwd);
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
        return UUID.randomUUID().toString();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void writeData() {
        clean();
        Gson gson = new Gson();

        try {
            new File(Constants.DATA_DIR).mkdir();
            new File(Constants.DB_FILE).createNewFile();
            new File(Constants.CONFIGURATION_FILE).createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileUtils.writeStringToFile(new File(Constants.DB_FILE), gson.toJson(data));
            FileUtils.writeStringToFile(new File(Constants.CONFIGURATION_FILE), gson.toJson(configuration));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
