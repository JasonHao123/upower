package jason.app.weixin.neo4j.job;

import jason.app.weixin.common.service.IWeixinService;

import java.util.Date;
import java.util.Map;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;

@Component
public class WeixinAccessTokenJob2 extends QuartzJobBean {  
	private static Logger logger = LoggerFactory.getLogger(WeixinAccessTokenJob2.class);
  
    @Autowired
    private IWeixinService weixinService;
  

  
    @Override 
    @Transactional
    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {  
        logger.info("synchronize token at "+new Date().toString());  

			   weixinService.refreshAccessToken();
	
    }  
}  
