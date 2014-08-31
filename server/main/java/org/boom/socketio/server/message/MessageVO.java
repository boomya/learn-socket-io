package org.boom.socketio.server.message;

/**
 * Created by jiangshan on 14/8/31.
 */
public class MessageVO {
    private String userName;
    private String targetUserName;
    private String message;

    public MessageVO(){}

    public MessageVO(String userName, String message) {
        new MessageVO(userName, null, message);
    }

    public MessageVO(String userName, String targetUserName, String message) {
        super();
        this.userName = userName;
        this.message = message;
        this.targetUserName = targetUserName;
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
