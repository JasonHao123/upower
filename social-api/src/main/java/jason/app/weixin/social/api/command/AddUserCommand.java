package jason.app.weixin.social.api.command;

import jason.app.weixin.social.api.constant.CommandType;


public class AddUserCommand implements ICommand{

	private ICommandReceiver receiver;
	
	private Long userId;
	
	private String nickname;
	
	private Integer age;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public ICommandReceiver getReceiver() {
		return receiver;
	}

	@Override
	public CommandType getType() {
		// TODO Auto-generated method stub
		return CommandType.ADD_USER;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		receiver.createUser(userId,nickname,age);
	}

	@Override
	public void setReceiver(ICommandReceiver receiver) {
		// TODO Auto-generated method stub
		this.receiver = receiver;
	}

}
