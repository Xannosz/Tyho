package hu.xannosz.tyho;

public class Constatns {
    public static final String ENCODING = "UTF-8";
    public static final String LOGIN = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/%s\">\n" +
            "</head>\n" +
            "<body>\n" +
            "<div>\n" +
            "<form action=\"/execute\" method=\"post\">\n" +
            "  <label for=\"uname\">User name:</label><br>\n" +
            "  <input type=\"text\" id=\"uname\" name=\"uname\"><br>\n" +
            "  <label for=\"pwd\">Password:</label><br>\n" +
            "  <input type=\"password\" id=\"pwd\" name=\"pwd\"><br><br>\n" +
            "  <input type=\"submit\" value=\"Submit\">\n" +
            "  <input type=\"submit\" value=\"Cancel\">\n" +
            "  <input type=\"hidden\" name=\"success\" value=\"%s\">\n" +
            "  <input type=\"hidden\" name=\"failed\" value=\"%s\">\n" +
            "  <input type=\"hidden\" name=\"type\" value=\"login\">\n" +
            "</form> \n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
    public static final String REGISTRATION = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/%s\">\n" +
            "</head>\n" +
            "<body>\n" +
            "<div>\n" +
            "<form action=\"/execute\" method=\"post\">\n" +
            "  <label for=\"uname\">User name:</label><br>\n" +
            "  <input type=\"text\" id=\"uname\" name=\"uname\"><br>\n" +
            "  <label for=\"pwd\">Password:</label><br>\n" +
            "  <input type=\"password\" id=\"pwd\" name=\"pwd\"><br>\n" +
            "    <label for=\"pwda\">Password again:</label><br>\n" +
            "  <input type=\"password\" id=\"pwda\" name=\"pwda\"><br><br>\n" +
            "  <input type=\"submit\" value=\"Submit\">\n" +
            "  <input type=\"submit\" value=\"Cancel\">\n" +
            "  <input type=\"hidden\" name=\"success\" value=\"%s\">\n" +
            "  <input type=\"hidden\" name=\"failed\" value=\"%s\">\n" +
            "  <input type=\"hidden\" name=\"type\" value=\"registration\">\n" +
            "</form> \n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
    public static final String MODIFY = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/%s\">\n" +
            "</head>\n" +
            "<body>\n" +
            "<div>\n" +
            "<form action=\"/execute\" method=\"post\">\n" +
            "  <label for=\"uname\">User name:</label><br>\n" +
            "  <input type=\"text\" id=\"uname\" name=\"uname\"><br>\n" +
            "  <label for=\"opwd\">Old Password:</label><br>\n" +
            "  <input type=\"password\" id=\"opwd\" name=\"opwd\"><br>\n" +
            "  <label for=\"npwd\">New Password:</label><br>\n" +
            "  <input type=\"password\" id=\"npwd\" name=\"npwd\"><br>\n" +
            "  <label for=\"npwda\">New Password again:</label><br>\n" +
            "  <input type=\"password\" id=\"npwda\" name=\"npwda\"><br><br>\n" +
            "  <input type=\"submit\" value=\"Submit\">\n" +
            "  <input type=\"submit\" value=\"Cancel\">\n" +
            "  <input type=\"hidden\" name=\"success\" value=\"%s\">\n" +
            "  <input type=\"hidden\" name=\"failed\" value=\"%s\">\n" +
            "  <input type=\"hidden\" name=\"type\" value=\"modify\">\n" +
            "</form> \n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
    public static final String REDIRECT = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div>\n" +
            "<form name=\"inputForm\" action=\"%s\" method=\"post\">\n" +
            "    <input type=\"hidden\" name=\"token\" value=\"%s\" />\n" +
            "    <input type=\"hidden\" name=\"message\" value=\"%s\" />\n" +
            "</form>"+
            "<script type=\"text/javascript\">\n" +
            "    document.inputForm.submit();\n" +
            "</script>"+
            "</div>\n" +
            "</body>\n" +
            "</html>";
}
