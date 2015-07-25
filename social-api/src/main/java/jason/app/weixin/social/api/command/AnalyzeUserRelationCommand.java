package jason.app.weixin.social.api.command;

import jason.app.weixin.social.api.constant.CommandType;

public class AnalyzeUserRelationCommand implements ICommand{
	private ICommandReceiver receiver;
	private Long userId;
	private Integer extensiveLevel; 


	public Integer getExtensiveLevel() {
		return extensiveLevel;
	}

	public void setExtensiveLevel(Integer extensiveLevel) {
		this.extensiveLevel = extensiveLevel;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public CommandType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		receiver.analyzeUserRelationDistance(userId,extensiveLevel);
	}

	@Override
	public void setReceiver(ICommandReceiver receiver) {
		// TODO Auto-generated method stub
		this.receiver = receiver;
	}

}
