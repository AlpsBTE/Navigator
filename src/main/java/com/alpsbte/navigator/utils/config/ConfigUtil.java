package com.alpsbte.navigator.utils.config;

import com.alpsbte.alpslib.io.config.ConfigNotImplementedException;
import com.alpsbte.alpslib.io.config.ConfigurationUtil;

import java.nio.file.Paths;

public class ConfigUtil {
    private static ConfigurationUtil configUtilInstance;

    public static void init() throws ConfigNotImplementedException {
        if (configUtilInstance != null) return;
        configUtilInstance = new ConfigurationUtil(new ConfigurationUtil.ConfigFile[]{
                new ConfigurationUtil.ConfigFile(Paths.get("config.yml"), 1.1, true)
        });
    }

    public static ConfigurationUtil getInstance() {
        return configUtilInstance;
    }
}
