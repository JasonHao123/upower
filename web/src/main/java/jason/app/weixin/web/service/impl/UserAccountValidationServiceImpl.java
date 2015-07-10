package jason.app.weixin.web.service.impl;

import jason.app.weixin.web.service.IRule;
import jason.app.weixin.web.service.IUserAccountValidationService;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountValidationServiceImpl implements
		IUserAccountValidationService {

	@Autowired
	private List<IRule> rules;
	
	private boolean sorted = false;
	@Override
	public String validate() {
		if(!sorted) {
			Collections.sort(rules);
			sorted = true;
		}
		String result = null;
		for(IRule rule:rules) {
			result = rule.check();
			if(result!=null) {
				break;
			}
		}
		return result;
	}

}
