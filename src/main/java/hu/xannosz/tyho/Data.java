package hu.xannosz.tyho;

import hu.xannosz.microtools.pack.Douplet;
import lombok.Getter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Data {
    @Getter
    private Map<String, String> userPasswords = new HashMap<>();
    @Getter
    private Map<String, Douplet<String, Date>> tokenUsersExpires = new HashMap<>();
    @Getter
    private Map<String, Set<String>> userGroups = new HashMap<>();
    @Getter
    private Map<String, Set<String>> groupPrivileges = new HashMap<>();
    @Getter
    private Map<String, Set<String>> userPrivileges = new HashMap<>();
}
