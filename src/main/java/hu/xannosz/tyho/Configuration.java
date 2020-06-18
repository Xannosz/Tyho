package hu.xannosz.tyho;

import lombok.Getter;

public class Configuration {
    @Getter
    private int port = 8000;
    @Getter
    private long expireTime = 1000 * 60 * 10; // Ten minutes
    @Getter
    private long deletionTime = 1000 * 60 * 60 * 24 * 2; // Two days
    @Getter
    private String defaultGroupName = "default";
    @Getter
    private boolean useDefaultGroup = true;
    @Getter
    private boolean enableRegistration = true;
    @Getter
    private boolean enableCSSSetting = true;
    @Getter
    private int tokenLength = 8;  // In 128 bit (if it is 4 it make 512 bit length token)
    @Getter
    private int deleteLogFilesAfterDays = 10;
    @Getter
    private int deleteLogFilesAfterSize = 10;  // It is in MiB
    @Getter
    private boolean enableTokenInCookie = true;
    @Getter
    private boolean useExternalDb = false;
    @Getter
    private String externalDbQueryURL = "";
    @Getter
    private String externalDbSetURL = "";
}
