package com.teligen.integration.transformer;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;

public class FileMessageToJobRequest {
	
	private static final Logger LOGGER = Logger.getLogger(FileMessageToJobRequest.class);

	private Job job;

	private String fileParameterName;

	public void setFileParameterName(String fileParameterName) {
		this.fileParameterName = fileParameterName;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@Transformer
	public JobLaunchRequest toRequest(Message<File> message) {
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addString(fileParameterName, message.getPayload().getAbsolutePath());
		return new JobLaunchRequest(job, jobParametersBuilder.toJobParameters());
	}
}
