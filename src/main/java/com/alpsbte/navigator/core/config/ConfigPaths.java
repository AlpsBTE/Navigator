package com.alpsbte.navigator.core.config;

public class ConfigPaths {

    // Servers
    private static final String SERVERS = "servers.";

    private static final String SERVERS_PLOT = SERVERS + "plot.";
    public static final String SERVERS_PLOT_IP = SERVERS_PLOT + "IP";
    public static final String SERVERS_PLOT_PORT = SERVERS_PLOT + "port";
    public static final String SERVERS_PLOT_ENABLE_JOIN_MESSAGE = SERVERS_PLOT + "enableJoinMessage";
    public static final String SERVERS_PLOT_JOIN_MESSAGE = SERVERS_PLOT + "joinMessage";

    private static final String SERVERS_TERRA = SERVERS + "terra.";
    public static final String SERVERS_TERRA_IP = SERVERS_TERRA + "IP";
    public static final String SERVERS_TERRA_PORT = SERVERS_TERRA + "port";

    private static final String SERVERS_EVENT = SERVERS + "event.";
    public static final String SERVERS_EVENT_IP = SERVERS_EVENT + "IP";
    public static final String SERVERS_EVENT_PORT = SERVERS_EVENT + "port";
    public static final String SERVERS_EVENT_CURRENT_EVENT = SERVERS_EVENT + "currentEvent";
    public static final String SERVERS_EVENT_VISIBLE = SERVERS_EVENT + "visible";
    public static final String SERVERS_EVENT_JOINABLE = SERVERS_EVENT + "joinable";

    private static final String SERVERS_EVENT_TYPE = SERVERS_EVENT + "type.";
    public static final String SERVERS_EVENT_TYPE_MATERIAL = SERVERS_EVENT_TYPE + "material";
    public static final String SERVERS_EVENT_TYPE_TITLE = SERVERS_EVENT_TYPE + "title";
    public static final String SERVERS_EVENT_TYPE_DESCRIPTION = SERVERS_EVENT_TYPE + "description";
    public static final String SERVERS_EVENT_TYPE_START_DATE = SERVERS_EVENT_TYPE + "startDate";
    public static final String SERVERS_EVENT_TYPE_END_DATE = SERVERS_EVENT_TYPE + "endDate";

    // Holograms
    public static final String HOLOGRAMS = "holograms.";
    public static final String HOLOGRAMS_ENABLED = ".enabled";
    public static final String HOLOGRAMS_X = ".x";
    public static final String HOLOGRAMS_Y = ".y";
    public static final String HOLOGRAMS_Z = ".z";

    // Config
    public static final String CONFIG_VERSION = "config-version";
}
