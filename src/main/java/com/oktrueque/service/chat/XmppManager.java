package com.oktrueque.service.chat;


import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Facundo on 7/17/2017.
 */
@Component
public class XmppManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmppManager.class);
    @Value("${openfire.server}")
    private String server;
    @Value("${openfire.port}")
    private String port;
    private static final int packetReplyTimeout = 500;

    private ConnectionConfiguration config;
    private XMPPConnection connection;

    private ChatManager chatManager;
    private AccountManager accountManager;


    public XmppManager() {
    }

    public void init() {
        SmackConfiguration.setPacketReplyTimeout(packetReplyTimeout);
        config = new ConnectionConfiguration(server, Integer.parseInt(port));
        config.setSASLAuthenticationEnabled(true);
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        connection = new XMPPConnection(config);
        try {
            connection.connect();
            LOGGER.info("Conexion exitosa al servidor Openfire");
        }
        catch (XMPPException e){
            LOGGER.error("Error to connect with server Openfire", e);
        }

    }

    public void createUser(String username, String password, String name, String email){
        Map<String,String> dates = new LinkedHashMap<>();
        dates.put("name",name);
        dates.put("email",email);
        accountManager = connection.getAccountManager();
        try {
            accountManager.createAccount(username, password,dates);
            LOGGER.info("Nuevo usuario de openFire, user:" + username);
            disconnect();
        } catch (XMPPException e) {
            LOGGER.error("Error to create new user", e);
        }
    }


    public void loginUser(String username, String password){
        try {
            if (connection!=null && connection.isConnected()) {
                connection.login(username, password);
                chatManager= connection.getChatManager();
                LOGGER.info("User login in openFire success");
            }
        } catch (XMPPException e) {
            LOGGER.error("Error to try login user", e);
        }
    }

    public void setStatus(boolean available, String status) {
        Presence.Type type = available? Presence.Type.available: Presence.Type.unavailable;
        Presence presence = new Presence(type);
        presence.setStatus(status);
        connection.sendPacket(presence);
    }

    public void disconnect() {
        if (connection!=null && connection.isConnected()) {
            connection.disconnect();
            LOGGER.info("Logout to openfire success");

        }
    }

    public void sendMessage(String message, String userNameTo)  {

        String userTo = userNameTo + "@" + connection.getServiceName();

        Chat chat = chatManager.createChat(userTo, new MessageListener() {
            @Override
            public void processMessage(Chat chat, Message message) {
                Message outMsg = new Message(message.getBody());
                try {
                    LOGGER.info("Receiving msj from " + userNameTo + " msj: " + outMsg);
                    chat.sendMessage(outMsg);
                } catch (XMPPException e) {
                    //Error
                }
            }
        });
        try {
            chat.sendMessage(new Message(message,Message.Type.chat));
            LOGGER.info("Send msj to " + userNameTo + " msj: " + message);
        } catch (XMPPException e) {
            LOGGER.error("Error to try send message", e);
        }
    }


    public void createEntry(String user, String name, String userToLogin) {
        try{
            init();
            loginUser(userToLogin,userToLogin+"pass");
            Roster roster = connection.getRoster();
            roster.createEntry(user, name, null);
            disconnect();
        }catch (Exception e){
            LOGGER.error("Error to try create Entry", e);
        }
    }

    public List<RosterEntry> getChatsRegistered(){
        List<RosterEntry> rostersEntries = new ArrayList<>();
        Roster roster = connection.getRoster();
        roster.getEntries().forEach(rostersEntries :: add);
        return rostersEntries;
    }

    //  PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class));
//        PacketCollector collector = connection.createPacketCollector(filter);
}