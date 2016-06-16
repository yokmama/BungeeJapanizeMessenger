package com.github.ucchyocean.slack;

import com.github.ucchyocean.bjm.BungeeJapanizeMessenger;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import org.eclipse.jetty.server.Server;

import java.util.logging.Level;

public class SlackReceiveServer implements ScheduledTask, Runnable {

    BungeeJapanizeMessenger plugin;
    Server server = null;
    int port;

    public SlackReceiveServer(BungeeJapanizeMessenger plugin, int port) {
        this.plugin = plugin;
        this.port = port;
    }

    @Override
    public void run() {
        if(server == null) {
            server = new Server(port);
            server.setHandler(new SlackReceiveHandler(plugin));
            try {
                server.start();
                server.join();
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Error in starting SlackMC server: " + e);
            }
        }
    }

    public void stop(){
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server = null;
        }

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
        stop();
    }
}
