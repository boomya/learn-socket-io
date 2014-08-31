package org.boom.socketio.server.message;

/**
 * Created by jiangshan on 14/8/31.
 */
public class BaseActionVO {
    private int action = 0;
    private String userName = null;

    public BaseActionVO(){}
    public BaseActionVO(int action, String userName) {
        this.action = action;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
