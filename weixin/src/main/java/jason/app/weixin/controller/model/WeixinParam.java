// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WeixinParam.java

package jason.app.weixin.controller.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xml")
public class WeixinParam
{

    public WeixinParam()
    {
    }

    public String getEvent()
    {
        return event;
    }

    public void setEvent(String event)
    {
        this.event = event;
    }

    public String getFromUserName()
    {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName)
    {
        this.fromUserName = fromUserName;
    }

    public String getToUserName()
    {
        return toUserName;
    }

    public void setToUserName(String toUserName)
    {
        this.toUserName = toUserName;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getMsgType()
    {
        return msgType;
    }

    public void setMsgType(String msgType)
    {
        this.msgType = msgType;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getMsgId()
    {
        return msgId;
    }

    public void setMsgId(String msgId)
    {
        this.msgId = msgId;
    }

    public String toString()
    {
        return (new StringBuilder()).append("from ").append(fromUserName).append(" msgId ").append(msgId).append(" msgType ").append(msgType).append(" content ").append(content).toString();
    }
    

    public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	@XmlElement(name="FromUserName")
	private String fromUserName;
	@XmlElement(name="ToUserName")
    private String toUserName;
	@XmlElement(name="CreateTime")
    private String createTime;
	@XmlElement(name="MsgType")
    private String msgType;
	@XmlElement(name="Content")
    private String content;
	@XmlElement(name="MsgId")
    private String msgId;
	@XmlElement(name="Event")
    private String event;
	@XmlElement(name="EventKey")
    private String eventKey;
}
