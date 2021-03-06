/*
 * Copyright 2011-2012 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.hadoop.mapreduce;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.JobDetailBean;

/**
 * Quartz JobDetail wrapper to schedule Hadoop Job. This class extends 
 * {@link org.springframework.scheduling.quartz.JobDetailBean} to provide more properties to JobDetail.
 * This class will be injected to {@link org.springframework.scheduling.quartz.CronTriggerBean} as property\
 *  "jobDetail".
 *  
 * @author Jarred Li
 * @since 1.0
 * @see org.springframework.scheduling.quartz.JobDetailBean
 * @see ScheduledJobBean
 */
public class HadoopJobDetailBean extends JobDetailBean implements BeanNameAware, ApplicationContextAware,
		InitializingBean {

	private static final long serialVersionUID = -220421481403040926L;

	private static final Log log = LogFactory.getLog(HadoopJobDetailBean.class);

	private Set<String> jobNames;

	private boolean waitForJobs = true;

	private ApplicationContext applicationContext;


	/**
	 * set more properties into JobDataMap used by <code>ScheduledJobBean</code>
	 */
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		super.setJobClass(ScheduledJobBean.class);

		getJobDataMap().put("jobNames", jobNames);
		getJobDataMap().put("waitForJobs", this.waitForJobs);
		getJobDataMap().put("applicatonContext", applicationContext);
		log.debug("after property set");
	}

	/**
	 * Sets the Jobs to run.
	 * 
	 * @param jobs The jobs to run.
	 */
	public void setJobNames(Set<String> names) {
		this.jobNames = names;
	}

	/**
	 * Indicates whether the runner should wait for the jobs to finish (the default) or not.
	 * 
	 * @param waitForJobs The waitForJobs to set.
	 */
	public void setWaitForJobs(boolean waitForJobs) {
		this.waitForJobs = waitForJobs;
	}

	/**
	 * set application context
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		super.setApplicationContext(applicationContext);
	}
	
	Set<String> getJobNames(){
		return this.jobNames;
	}
	
	boolean isWaitForJobs(){
		return this.waitForJobs;
	}
	
	ApplicationContext getApplicationContext(){
		return this.applicationContext;
	}

}
