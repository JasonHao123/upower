package jason.app.weixin.social.api.command;

import jason.app.weixin.social.api.constant.CommandType;

import java.io.Serializable;

public interface ICommand extends Serializable{
	public CommandType getType();
	public void execute();
	public void setReceiver(ICommandReceiver receiver);
}
