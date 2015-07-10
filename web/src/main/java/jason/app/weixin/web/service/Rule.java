package jason.app.weixin.web.service;


public  abstract class Rule implements IRule{

	@Override
	public int compareTo(IRule rule) {
		// TODO Auto-generated method stub
		return this.getOrder().compareTo(rule.getOrder());
	}
}
