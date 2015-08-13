package jason.app.weixin.social.model;

import java.io.Serializable;

public class SocialMessage implements Serializable{
private String touser;
private String msgtype;
private Text text;
public String getTouser() {
	return touser;
}
public void setTouser(String touser) {
	this.touser = touser;
}
public String getMsgtype() {
	return msgtype;
}
public void setMsgtype(String msgtype) {
	this.msgtype = msgtype;
}
public Text getText() {
	return text;
}
public void setText(Text text) {
	this.text = text;
}


}
