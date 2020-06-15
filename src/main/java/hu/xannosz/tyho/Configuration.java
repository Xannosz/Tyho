package hu.xannosz.tyho;

import lombok.Getter;

public class Configuration {
    @Getter
    private int port = 8000;
    @Getter
    private long expireTime = 1000 * 60 * 2; // Two minutes
    @Getter
    private long deletionTime = 1000 * 60 * 60 * 24 * 2; // Two days
}
