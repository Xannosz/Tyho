package hu.xannosz.tyho;

import java.util.*;

public class Db {

    private static final long EXPIRE_TIME = 1000 * 60 * 2; // Two minutes
    private static final long DELETION_TIME = 1000 * 60 * 60 * 24 * 2; // Two days
    private Map<String, String> userPasswords = new HashMap<>();
    private Map<String, String> tokenUsers = new HashMap<>();
    private Map<String, Date> tokenExpires = new HashMap<>();
    private Map<String, Set<String>> userGroups = new HashMap<>();
    private Map<String, Set<String>> groupPrivileges = new HashMap<>();
    private Map<String, Set<String>> userPrivileges = new HashMap<>();

    public void getData() {

    }

    public String getToken(String uName, String pwd) {
        if (userPasswords.containsKey(uName) && userPasswords.get(uName).equals(pwd)) {
            String token = createToken();
            tokenUsers.put(token, uName);
            tokenExpires.put(token, new Date());
            return token;
        }
        return null;
    }

    public String modify(String uName, String oPwd, String nPwd, String nPwdA) {
        if (nPwd.equals(nPwdA) && userPasswords.containsKey(uName) && userPasswords.get(uName).equals(oPwd)) {
            userPasswords.put(uName, nPwd);
            return "success";
        }
        return "fail";
    }

    public String registration(String uName, String pwd, String pwdA) {
        if (pwd.equals(pwdA) && !userPasswords.containsKey(uName)) {
            userPasswords.put(uName, pwd);
            return "success";
        }
        return "fail";
    }


    public String getAccess(String token, String access) {
        String user = tokenUsers.get(token);
        if (user == null) {
            return "tokenNotExist";
        }
        if (tokenExpires.get(token).getTime() + EXPIRE_TIME < (new Date()).getTime()) {
            return "tokenExpired";
        }
        tokenExpires.put(token, new Date());
        if (!getPrivileges(user).contains(access)) {
            return "accessDecided";
        }
        return "accessGranted";
    }

    public void clean() {
        Set<String> tokens = new HashSet<>();
        for (Map.Entry<String, Date> token : tokenExpires.entrySet()) {
            if (token.getValue().getTime() + DELETION_TIME < (new Date()).getTime()) {
                tokens.add(token.getKey());
            }
        }
        for (String token : tokens) {
            tokenExpires.remove(token);
            tokenUsers.remove(token);
        }
    }

    private Set<String> getPrivileges(String user) {
        Set<String> result = new HashSet<>();
        Set<String> groups = userGroups.get(user);
        result.addAll(userPrivileges.get(user));
        for (String group : groups) {
            result.addAll(groupPrivileges.get(group));
        }
        return result;
    }

    private String createToken() {
        return UUID.randomUUID().toString();
    }

    public void writeData() {
        System.out.println("Users: " + userPasswords);
    }
}
