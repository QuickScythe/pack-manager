package top.offsetmonkey538.githubresourcepackmanager.config;

import blue.endless.jankson.Comment;
import top.offsetmonkey538.monkeylib538.config.Config;

import static top.offsetmonkey538.githubresourcepackmanager.GithubResourcepackManager.MOD_ID;

public class ModConfig extends Config {
    public int serverPort = 8080;
    public String serverIp = "0.0.0.0";
    public String webhookPath = "/webhook";
    public String githubRef = "refs/heads/master";
    public String resourcepackUrl = null;
    public String downloadUrl = null;
    public String githubUrl = null;
    public boolean isPrivate = false;
    public String githubUsername = null;

    public String downloadUser = null;

    @Comment("PLEASE DON'T SHARE THIS WITH ANYONE EVER")
    public String githubToken = null;
    public String downloadPassword = null;

    @Override
    protected String getName() {
        return MOD_ID;
    }
}
