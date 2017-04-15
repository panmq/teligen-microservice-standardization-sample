package com.teligen.integration.file.filters.comparator;

import java.io.File;
import java.util.Comparator;

import org.apache.log4j.Logger;

public class FileCreationTimeComparator implements Comparator<File> {
	
	private static final Logger LOGGER = Logger.getLogger(FileCreationTimeComparator.class);

	@Override
	public int compare(File file1, File file2) {
		return Long.valueOf(file2.lastModified()).compareTo(Long.valueOf(file1.lastModified()));
	}

}
