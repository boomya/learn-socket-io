package org.boom.socketio.server.message;

/**
 * Created by jiangshan on 14/8/31.
 */
public class One2OneActionVO extends BaseActionVO {
    public One2OneActionVO(){

    }
    private String targetUserName = null;
    private String message = null;

    public One2OneActionVO(String targetUserName, String message) {
        this.targetUserName = targetUserName;
        this.message = message;
    }

    public One2OneActionVO(int action, String userName, String targetUserName, String message) {
        super(action, userName);
        this.targetUserName = targetUserName;
        this.message = message;
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
