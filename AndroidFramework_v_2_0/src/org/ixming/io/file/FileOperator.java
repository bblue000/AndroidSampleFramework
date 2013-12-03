package org.ixming.io.file;

import java.io.File;

/**
 * A file operator can do basic operations, such as
 * calculating sizes, deleting, ... more for updating
 * @author Yin Yong
 * @version 1.0
 */
public class FileOperator {

	public static FileOperator newInstance(String path) {
		return new FileOperator(path);
	}
	
	private File mFile;
	private FileOperator(String path) {
		if (null == path) {
			throw new NullPointerException("FileOperator<init> file path is null!");
		}
		mFile = new File(path);
	}
	
	public long getSize() {
		return FileUtils.caculateFileSize(mFile);
	}
	
	/**
	 * delete the specific file
	 * @param deleteRoot delete root file if the specific file is a directory
	 * @return true if the specific file no longer exits, otherwise false
	 */
	public boolean delete(boolean deleteRoot) {
		return FileUtils.deleteFile(mFile, deleteRoot);
	}
	
	/**
	 * copy a file from srcFile to destFile, return true if succeed, return
	 * false if fail
	 */
	public boolean copyTo(File destFile) {
		return FileUtils.copyToFile(mFile, destFile);
	}
	
	/**
	 * copy a file from srcFile to destFile, return true if succeed, return
	 * false if fail
	 */
	public boolean cutToFile(File destFile) {
		return FileUtils.cutToFile(mFile, destFile);
	}
}
