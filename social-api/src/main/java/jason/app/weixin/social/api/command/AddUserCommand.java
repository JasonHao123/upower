package jason.app.weixin.social.api.command;

import jason.app.weixin.social.api.constant.CommandType;


public class AddUserCommand implements ICommand{

	private ICommandReceiver receiver;
	
	private Long userId;
	
	private String nickname;
	
	private Integer age;
	
	private Long category1;
	
	private Long category2;
	
	private String[] locations;
	
	private String[] hobbys;

	public String[] getLocations() {
		return locations;
	}

	public void setLocations(String[] locations) {
		this.locations = locations;
	}

	public String[] getHobbys() {
		return hobbys;
	}

	public void setHobbys(String[] hobbys) {
		this.hobbys = hobbys;
	}

	public Long getCategory1() {
		return category1;
	}

	public void setCategory1(Long category1) {
		this.category1 = category1;
	}

	public Long getCategory2() {
		return category2;
	}

	public void setCategory2(Long category2) {
		this.category2 = category2;
	}

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
		receiver.createUser(userId,nickname,age,category1,category2,locations,hobbys);
	}

	@Override
	public void setReceiver(ICommandReceiver receiver) {
		// TODO Auto-generated method stub
		this.receiver = receiver;
	}

}
