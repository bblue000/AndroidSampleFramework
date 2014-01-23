package org.ixming.io.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.ixming.io.IOConstants;
import org.ixming.utils.NumberUtils;
import org.ixming.utils.StringUtils;

public class FileUtils {

	private FileUtils() { /* no instance */ }
	
	/**
	 * 删除指定的文件
	 * @param file 指定的文件对象
	 * @param deleteRoot 如果该文件是文件夹，是否删除该文件夹
	 * @return 如果最终指定的文件不再存在，则返回TRUE
	 */
	public static boolean deleteFile(File file, boolean deleteRoot) {
		if (null == file || !file.exists()) {
			return true;
		}
		if (file.isDirectory()) {
			boolean flag = true;
			File[] childFile = file.listFiles();
			if (null != childFile && childFile.length > 0) {
				for (File cFile : childFile) {
					flag &= deleteFile(cFile, deleteRoot);
				}
			}
			if (deleteRoot) {
				flag &= file.delete();
			}
			return flag;
		} else {
			return file.delete();
		}
	}
	/**
	 * 删除指定的文件
	 * @param filePath 指定的文件路径
	 * @param deleteRoot 如果该文件是文件夹，是否删除该文件夹
	 * @return 如果最终指定的文件不再存在，则返回TRUE
	 */
	public static boolean deleteFile(String filePath, boolean deleteRoot) {
		if (StringUtils.isEmpty(filePath)) {
			return true;
		}
		return deleteFile(new File(filePath), deleteRoot);
	}
	
	
	/**
	 * 计算指定文件/文件夹的大小
	 */
	public static long caculateFileSize(File file) {
		if (null == file || !file.exists()) {
			return 0L;
		}
		if (file.isDirectory()) {
			long size = 0L;
			File[] childFile = file.listFiles();
			if (null != childFile && childFile.length > 0) {
				for (File cFile : childFile) {
					size += caculateFileSize(cFile);
				}
			}
			return size;
		} else {
			return file.length();
		}
	}
	
	/**
	 * 计算指定文件/文件夹的大小
	 */
	public static long caculateFileSize(String filePath) {
		return caculateFileSize(new File(filePath));
	}
	
	
	/**
	 * 以M为单位
	 * @added 1.0
	 */
	public static String calFileSizeString(double bytes) {
		if (0D >= bytes) {
			bytes = 0D;
		}
		return NumberUtils.formatByUnit(bytes, 1024D, 900D, 2, "B", "KB", "M", "G", "T");
	}
	
	
	/**
	 * copy a file from srcFile to destFile, return true if succeed, return
	 * false if fail
	 */
    public static boolean copyToFile(File srcFile, File destFile) {
        try {
            InputStream in = new FileInputStream(srcFile);
            try {
            	return copyToFile(in, destFile);
            } finally  {
                in.close();
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Copy data from a source stream to destFile.
     * Return true if succeed, return false if failed.
     */
    public static boolean copyToFile(InputStream inputStream, File destFile) {
        try {
            if (destFile.exists()) {
                destFile.delete();
            }
            FileOutputStream out = new FileOutputStream(destFile);
            try {
                byte[] buffer = new byte[IOConstants.FILE_OUTPUT_BUFFER_SIZE];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) >= 0) {
                    out.write(buffer, 0, bytesRead);
                }
            } finally {
                out.flush();
                try {
                    out.getFD().sync();
                } catch (IOException e) {
                }
                out.close();
            }
            return true;
        } catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Copy data from a source stream to destFile, and then delete the source file.
     * Return true if succeed, return false if failed.
     */
    public static boolean cutToFile(File srcFile, File destFile) {
    	return copyToFile(srcFile, destFile) && deleteFile(srcFile, true);
    }
    
    /**
     * Read a text file into a String, optionally limiting the length.
     * @param file to read (will not seek, so things like /proc files are OK)
     * @param max length (positive for head, negative of tail, 0 for no limit)
     * @param ellipsis to add of the file was truncated (can be null)
     * @return the contents of the file, possibly truncated
     * @throws IOException if something goes wrong reading the file
     */
    public static String readTextFile(File file, int max, String ellipsis) throws IOException {
    	try {
	        InputStream input = new FileInputStream(file);
	        try {
	            long size = file.length();
	            if (max > 0 || (size > 0 && max == 0)) {  // "head" mode: read the first N bytes
	                if (size > 0 && (max == 0 || size < max)) max = (int) size;
	                byte[] data = new byte[max + 1];
	                int length = input.read(data);
	                if (length <= 0) return "";
	                if (length <= max) return new String(data, 0, length);
	                if (ellipsis == null) return new String(data, 0, max);
	                return new String(data, 0, max) + ellipsis;
	            } else if (max < 0) {  // "tail" mode: keep the last N
	                int len;
	                boolean rolled = false;
	                byte[] last = null, data = null;
	                do {
	                    if (last != null) rolled = true;
	                    byte[] tmp = last; last = data; data = tmp;
	                    if (data == null) data = new byte[-max];
	                    len = input.read(data);
	                } while (len == data.length);
	
	                if (last == null && len <= 0) return "";
	                if (last == null) return new String(data, 0, len);
	                if (len > 0) {
	                    rolled = true;
	                    System.arraycopy(last, len, last, 0, last.length - len);
	                    System.arraycopy(data, 0, last, last.length - len, len);
	                }
	                if (ellipsis == null || !rolled) return new String(last);
	                return ellipsis + new String(last);
	            } else {  // "cat" mode: size unknown, read it all in streaming fashion
	                ByteArrayOutputStream contents = new ByteArrayOutputStream();
	                int len;
	                byte[] data = new byte[1024];
	                do {
	                    len = input.read(data);
	                    if (len > 0) contents.write(data, 0, len);
	                } while (len == data.length);
	                return contents.toString();
	            }
	        } finally {
	            input.close();
	        }
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
    }

	/**
	 * Writes string to file. Basically same as "echo -n $string > $filename"
	 * @param filename
	 * @param string
	 * @return return true if succeed, return false if fail
	 */
    public static boolean stringToFile(String filename, String string) {
        try {
        	FileWriter out = new FileWriter(filename);
        	try {
        		out.write(string);
			} finally {
				out.close();
			}
            return true;
        } catch (Exception e) {
        	e.printStackTrace();
        	return false;
        }
    }
}
