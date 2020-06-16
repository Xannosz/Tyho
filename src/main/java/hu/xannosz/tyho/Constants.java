package hu.xannosz.tyho;

public class Constants {
    public static final String ENCODING = "UTF-8";

    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    public static final String TOKEN = "token";
    public static final String ACCESS = "access";
    public static final String CSS = "css";
    public static final String TYPE = "type";

    public static final String DATA_DIR = "data";
    public static final String DB_FILE = "data/db.json";
    public static final String CONFIGURATION_FILE = "data/configuration.json";

    public static final String TOKEN_NOT_EXISTS = "Token Not Exists";
    public static final String TOKEN_EXPIRED = "Token Expired";
    public static final String ACCESS_DENIED = "Access Denied";
    public static final String ACCESS_GRANTED = "Access Granted";

    public static final String LOGIN_PATH = "login";
    public static final String REGISTRATION_PATH = "registration";
    public static final String MODIFY_PATH = "modify";
    public static final String EXECUTE_PATH = "execute";
    public static final String VALIDATE_PATH = "validate";

    public static final String U_NAME = "uName";
    public static final String PWD = "pwd";
    public static final String PWD_A = "pwdA";
    public static final String O_PWD = "oPwd";
    public static final String N_PWD = "nPwd";
    public static final String N_PWD_A = "nPwdA";

    private static final String HTML_HEAD = "<!DOCTYPE html><html><head>" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/%s\">" +
            "</head><body><div>" +
            "<form action=\"/" + EXECUTE_PATH + "\" method=\"post\">";
    private static final String HTML_FOOT = "</form></div></body></html>";

    public static final String LOGIN = HTML_HEAD +
            "  <label for=\"" + U_NAME + "\">User name:</label><br>\n" +
            "  <input type=\"text\" id=\"" + U_NAME + "\" name=\"" + U_NAME + "\"><br>\n" +
            "  <label for=\"" + PWD + "\">Password:</label><br>\n" +
            "  <input type=\"password\" id=\"" + PWD + "\" name=\"" + PWD + "\"><br><br>\n" +
            "  <input type=\"submit\" value=\"Submit\">\n" +
            "  <input type=\"submit\" value=\"Cancel\">\n" +
            "  <input type=\"hidden\" name=\"" + SUCCESS + "\" value=\"%s\">\n" +
            "  <input type=\"hidden\" name=\"" + FAILED + "\" value=\"%s\">\n" +
            "  <input type=\"hidden\" name=\"" + TYPE + "\" value=\"" + LOGIN_PATH + "\">" + HTML_FOOT;
    public static final String REGISTRATION = HTML_HEAD +
            "  <label for=\"" + U_NAME + "\">User name:</label><br>\n" +
            "  <input type=\"text\" id=\"" + U_NAME + "\" name=\"" + U_NAME + "\"><br>\n" +
            "  <label for=\"" + PWD + "\">Password:</label><br>\n" +
            "  <input type=\"password\" id=\"" + PWD + "\" name=\"" + PWD + "\"><br>\n" +
            "  <label for=\"" + PWD_A + "\">Password again:</label><br>\n" +
            "  <input type=\"password\" id=\"" + PWD_A + "\" name=\"" + PWD_A + "\"><br><br>\n" +
            "  <input type=\"submit\" value=\"Submit\">\n" +
            "  <input type=\"submit\" value=\"Cancel\">\n" +
            "  <input type=\"hidden\" name=\"" + SUCCESS + "\" value=\"%s\">\n" +
            "  <input type=\"hidden\" name=\"" + FAILED + "\" value=\"%s\">\n" +
            "  <input type=\"hidden\" name=\"" + TYPE + "\" value=\"" + REGISTRATION_PATH + "\">\n" +
            HTML_FOOT;
    public static final String MODIFY = HTML_HEAD +
            "  <label for=\"" + U_NAME + "\">User name:</label><br>\n" +
            "  <input type=\"text\" id=\"" + U_NAME + "\" name=\"" + U_NAME + "\"><br>\n" +
            "  <label for=\"" + O_PWD + "\">Old Password:</label><br>\n" +
            "  <input type=\"password\" id=\"" + O_PWD + "\" name=\"" + O_PWD + "\"><br>\n" +
            "  <label for=\"" + N_PWD + "\">New Password:</label><br>\n" +
            "  <input type=\"password\" id=\"" + N_PWD + "\" name=\"" + N_PWD + "\"><br>\n" +
            "  <label for=\"" + N_PWD_A + "\">New Password again:</label><br>\n" +
            "  <input type=\"password\" id=\"" + N_PWD_A + "\" name=\"" + N_PWD_A + "\"><br><br>\n" +
            "  <input type=\"submit\" value=\"Submit\">\n" +
            "  <input type=\"submit\" value=\"Cancel\">\n" +
            "  <input type=\"hidden\" name=\"" + SUCCESS + "\" value=\"%s\">\n" +
            "  <input type=\"hidden\" name=\"" + FAILED + "\" value=\"%s\">\n" +
            "  <input type=\"hidden\" name=\"" + TYPE + "\" value=\"" + MODIFY_PATH + "\">\n" +
            HTML_FOOT;
    public static final String REDIRECT = "<!DOCTYPE html><html><head></head><body><div>\n" +
            "<form name=\"inputForm\" action=\"%s\" method=\"post\">\n" +
            "    <input type=\"hidden\" name=\"token\" value=\"%s\" />\n" +
            "    <input type=\"hidden\" name=\"message\" value=\"%s\" />\n" +
            "</form>" +
            "<script type=\"text/javascript\">\n" +
            "    document.inputForm.submit();\n" +
            "</script>" +
            "</div></body></html>";
}
