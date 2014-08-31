package org.boom.socketio.server;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.boom.socketio.server.message.MessageVO;
import org.boom.socketio.server.message.One2OneActionVO;
import org.boom.socketio.server.message.RoomActionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by jiangshan on 14/8/31.
 */
public class ChatLauncher {

    private final static Logger log = LoggerFactory.getLogger(ChatLauncher.class);

    private final static ConcurrentMap<String, UUID> userNameUUIDMap = new ConcurrentHashMap<String, UUID>();
    private final static ConcurrentMap<UUID, String> uuidUserNameMap = new ConcurrentHashMap<UUID, String>();

    public static void main(String[] args) throws InterruptedException {
        Configuration config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(9092);

        final SocketIOServer server = new SocketIOServer(config);
        final SocketIONamespace chatNamespace = server.addNamespace("/chat");

        chatNamespace.addConnectListener(new ConnectListener() {

            @Override
            public void onConnect(SocketIOClient client) {
                //当连接建立好后,会进入默认的房间/chat
                log.info("=========>onConnect");
//                client.joinRoom("lobby");
            }
        });

        chatNamespace.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                log.info("=========>onDisconnect");
                String userName = uuidUserNameMap.remove(client.getSessionId());
                if(userName != null){
                    userNameUUIDMap.remove(userName);
                    MessageVO data = new MessageVO();
                    data.setUserName(userName);
                    data.setMessage("leave.");
                    chatNamespace.getBroadcastOperations().sendEvent("chatevent", data);
                }
            }
        });
        chatNamespace.addEventListener("registEvent", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                // broadcast messages to all clients
                log.info("=========>registEvent");
                userNameUUIDMap.putIfAbsent(data, client.getSessionId());
                uuidUserNameMap.putIfAbsent(client.getSessionId(), data);
                MessageVO messageVO = new MessageVO();
                messageVO.setUserName(data);
                messageVO.setMessage("regist.");
                for(String roomName:client.getAllRooms()) {
                    server.getRoomOperations(roomName).sendEvent("chatevent", messageVO);
                }
            }
        });
        chatNamespace.addEventListener("chatevent", MessageVO.class, new DataListener<MessageVO>() {
            @Override
            public void onData(SocketIOClient client, MessageVO data, AckRequest ackRequest) {
                // broadcast messages to all clients
                log.info("=========>chatevent");
                for(String roomName:client.getAllRooms()) {
                    log.info("=========>chatevent-->" + roomName);
                    server.getRoomOperations(roomName).sendEvent("chatevent", data);
                }
//                chatNamespace.getBroadcastOperations().sendEvent("chatevent", data);
            }
        });
        chatNamespace.addEventListener("roomEvent", RoomActionVO.class, new DataListener<RoomActionVO>() {
            @Override
            public void onData(SocketIOClient client, RoomActionVO data, AckRequest ackSender) throws Exception {
                for(String roomName:client.getAllRooms()) {
                    client.leaveRoom(roomName);
                }
                client.joinRoom(data.getRoomName());
                MessageVO messageVO = new MessageVO();
                messageVO.setUserName(data.getUserName());
                messageVO.setMessage("add room:" + data.getRoomName());
                server.getRoomOperations(data.getRoomName()).sendEvent("chatevent", messageVO);
            }
        });

        chatNamespace.addEventListener("one2oneEvent", One2OneActionVO.class, new DataListener<One2OneActionVO>() {
            @Override
            public void onData(SocketIOClient client, One2OneActionVO data, AckRequest ackSender) throws Exception {
                UUID sessionId = userNameUUIDMap.get(data.getTargetUserName());
                MessageVO messageVO = new MessageVO();
                messageVO.setUserName(data.getUserName() + " to you");
                messageVO.setMessage(data.getMessage());
                chatNamespace.getClient(sessionId).sendEvent("chatevent", messageVO);

                MessageVO messageVO2 = new MessageVO();
                messageVO2.setUserName("you to " + data.getTargetUserName());
                messageVO2.setMessage(data.getMessage());
                client.sendEvent("chatevent", messageVO2);
            }
        });


        server.start();
    }
}
