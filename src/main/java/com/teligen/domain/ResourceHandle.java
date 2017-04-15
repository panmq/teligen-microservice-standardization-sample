package com.teligen.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Profile;

@Profile("dispach")
@Entity
@Table(name = "TB_RESOURCE_HANDLE")
public class ResourceHandle {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "resource_type")
	private String resourceType;
	@Column(name = "file_path")
	private String filePath;
	@Column(name = "file_name")
	private String fileName;
	@Column(name = "file_suffix")
	private String fileSuffix;
	@Column(name = "file_size")
	private int fileSize;
	@Column(name = "discover_time")
	private Date discoverTime;
	@Column(name = "handle_time")
	private Date handleTime;
	@Column(name = "handler_name")
	private String handlerName;
	@Column(name = "complete_time")
	private Date completeTime;
	@Column(name = "total_success_line_num")
	private int totalSuccessLineNum;
	@Column(name = "issue_record_file_name")
	private String issueRecordFileName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public Date getDiscoverTime() {
		return discoverTime;
	}

	public void setDiscoverTime(Date discoverTime) {
		this.discoverTime = discoverTime;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public int getTotalSuccessLineNum() {
		return totalSuccessLineNum;
	}

	public void setTotalSuccessLineNum(int totalSuccessLineNum) {
		this.totalSuccessLineNum = totalSuccessLineNum;
	}

	public String getIssueRecordFileName() {
		return issueRecordFileName;
	}

	public void setIssueRecordFileName(String issueRecordFileName) {
		this.issueRecordFileName = issueRecordFileName;
	}

}
