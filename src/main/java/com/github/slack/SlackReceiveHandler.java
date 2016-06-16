package com.github.ucchyocean.slack;

import com.github.ucchyocean.bjm.BungeeJapanizeMessenger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SlackReceiveHandler extends AbstractHandler {
    private BungeeJapanizeMessenger plugin;

    public SlackReceiveHandler(BungeeJapanizeMessenger plugin){
        this.plugin = plugin;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getMethod().equals("POST")) {
            String[] texts = request.getParameter("text").split(":", 2);
            if(texts.length>1) {
                String channel_name = request.getParameter("channel_name");
                String user_name = request.getParameter("user_name");
                String token = request.getParameter("token");
                String userName = texts[0];
                String text = texts[1].trim();

                if(userName.equals("mc") && token.equals(plugin.getSlackToken())) {
                        System.out.print("slack plugin name:"+plugin.getDescription().getName());
                    plugin.onSlackMessage(token, channel_name, user_name, text);
                }else{
                    System.out.println("slack outgoing bad token:"+token);
                }
            }else{
                System.out.println("ignored message:"+request.getParameter("text"));
            }
        }
    }
}
