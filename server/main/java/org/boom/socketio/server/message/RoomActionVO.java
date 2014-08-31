package org.boom.socketio.server.message;

/**
 * Created by jiangshan on 14/8/31.
 */
//public class RoomActionVO extends BaseActionVO {
//    public String roomName = null;
//
//    public RoomActionVO(int action, String userName, String roomName) {
//        this.setAction(action);
//        this.setUserName(userName);
//        this.roomName = roomName;
//    }
//
//    public String getRoomName() {
//        return roomName;
//    }
//
//    public void setRoomName(String roomName) {
//        this.roomName = roomName;
//    }
//}
public class RoomActionVO extends BaseActionVO{
//    private int action = 0;
//    private String userName = null;
    private String roomName = null;

    public RoomActionVO() {
        super();
    }

    public RoomActionVO(int action, String userName, String roomName) {
//        this.action = action;
//        this.userName = userName;
        super(action, userName);
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

//    public int getAction() {
//        return action;
//    }
//
//    public void setAction(int action) {
//        this.action = action;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
}
