package com.github.ucchyocean.slack;

import com.github.ucchyocean.bjm.BungeeJapanizeMessenger;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.api.scheduler.TaskScheduler;

import java.io.BufferedOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yokmama on 16/06/15.
 */
public class SlackPoster implements ScheduledTask, Runnable {

    private final String message;
    private final String name;
    private final String iconUrl;
    private final boolean useMarkdown;
    private BungeeJapanizeMessenger plugin;

    public SlackPoster(BungeeJapanizeMessenger plugin, String message, String name, String iconUrl, boolean useMarkdown){
        this.plugin = plugin;
        this.message = message;
        this.name = name;
        this.useMarkdown = useMarkdown;
        this.iconUrl = iconUrl;
    }


    @Override
    public int getId() {
        return hashCode();
    }

    @Override
    public Plugin getOwner() {
        return plugin;
    }

    @Override
    public Runnable getTask() {
        return this;
    }

    @Override
    public void cancel() {

    }

    @Override
    public void run() {
        JsonObject json = new JsonObject();
        json.addProperty("text", message);
        json.addProperty("username", name);
        json.addProperty("icon_url", iconUrl);;
        json.addProperty("mrkdwn", useMarkdown);

        try {
            HttpURLConnection webhookConnection = (HttpURLConnection) new URL(plugin.getWebhookUrl()).openConnection();
            webhookConnection.setRequestMethod("POST");
            webhookConnection.setDoOutput(true);
            try (BufferedOutputStream bufOut = new BufferedOutputStream(webhookConnection.getOutputStream())) {
                String jsonStr = "payload=" + json.toString();
                bufOut.write(jsonStr.getBytes("utf8"));
                bufOut.flush();
                bufOut.close();
            }
            int serverResponseCode = webhookConnection.getResponseCode();
            webhookConnection.disconnect();
            webhookConnection = null;
        } catch (Exception ignored) {
        }
    }
}
