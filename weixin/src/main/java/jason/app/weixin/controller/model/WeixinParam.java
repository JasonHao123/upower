// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WeixinParam.java

package jason.app.weixin.controller.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xml")
//@XmlAccessorType(XmlAccessType.FIELD)
public class WeixinParam
{

    public WeixinParam()
    {
    }


//	@XmlElement(name="FromUserName")
	private String FromUserName;
//	@XmlElement(name="ToUserName")
    private String ToUserName;
//	@XmlElement(name="CreateTime")
    private String CreateTime;
//	@XmlElement(name="MsgType")
    private String MsgType;
//	@XmlElement(name="Content")
    private String Content;
//	@XmlElement(name="MsgId")
    private String MsgId;
//	@XmlElement(name="Event")
    private String Event;
//	@XmlElement(name="EventKey")
    private String EventKey;
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
    
    
}
