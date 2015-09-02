package jason.app.weixin.neo4j.job;

import jason.app.weixin.common.service.IWeixinService;
import jason.app.weixin.neo4j.job.quartz.Job;
import jason.app.weixin.neo4j.job.quartz.Trigger;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//@Component
//@Job("testJob")
//@Trigger(name="helloTrigger",interval=7000000L)
public class WeixinAccessTokenJob extends QuartzJobBean {  
	private static Logger logger = LoggerFactory.getLogger(WeixinAccessTokenJob.class);

	 
    @Autowired
    private IWeixinService weixinService;
  
	
    @Override  
    @Transactional
    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {  
        logger.info("quartz cluster job on node  .");  
        weixinService.refreshAccessToken();
    }  
}  
