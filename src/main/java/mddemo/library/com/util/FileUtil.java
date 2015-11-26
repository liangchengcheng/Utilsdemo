package mddemo.library.com.util;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年11月14日19:23:53
 * Description: 对文件进行操作
 */

public class FileUtil {

	/**
	 * 对文件进行复制
	 * @param srcFilepath 原始文件所在的位置
	 * @param dstFilepath 要复制到的位置
	 * @return 是否复制成功
	 */
	public static boolean fileCopy(String srcFilepath, String dstFilepath) {
		return fileCopy(new File(srcFilepath), new File(dstFilepath));
	}

	public static boolean fileCopy(File srcFile, File dstFile) {
		int length = 1048891;
		FileChannel inC = null;
		FileChannel outC = null;
		try {
			FileInputStream in = new FileInputStream(srcFile);
			FileOutputStream out = new FileOutputStream(dstFile);
			inC = in.getChannel();
			outC = out.getChannel();
			ByteBuffer b = null;
			while (inC.position() < inC.size()) {
				if ((inC.size() - inC.position()) < length) {
					length = (int) (inC.size() - inC.position());
				} else {
					length = 1048891;
				}
				b = ByteBuffer.allocateDirect(length);
				inC.read(b);
				b.flip();
				outC.write(b);
				outC.force(false);
			}
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (inC != null && inC.isOpen()) {
					inC.close();
				}
				if (outC != null && outC.isOpen()) {
					outC.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建文件夹
	 * @param path 所在的位置
	 * @return 是否创建成功
	 */
	public static boolean createFolder(String path) {
		if (!Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED))
			return false;
		File dir = new File(path) ;
		if (!dir.exists() && !dir.mkdirs()) {
			return false;
		}
		return true;
	}

	/**
	 * 删除文件夹
	 * @param dir 所在的位置
	 * @return 是否删除成功
	 */
	public static boolean deleteFileDir(File dir) {
		if (dir == null) {
            return true;
        }

		File[] fList = dir.listFiles();
        if (fList == null || fList.length <= 0) {
            return true;
        }

		for (File file : fList) {
            if (file == null) {
                continue;
            }

			if (file.isDirectory()) {
				if (!deleteFileDir(file)) {
					return false;
                }
			} else {
				if (!file.delete()) {
					return false;
                }
			}
		}
		
		return true;
	}

	/**
	 * 创建文件
	 * @param tempUrl 文件所在的位置
	 */
    public static void createTempFile(String tempUrl) {
        File tempFile = new File(tempUrl);
        if (!tempFile.exists()) {
            tempFile.mkdir();
        }
    }

	/**
	 * 对文件进行重命名的操作
	 * @param path  文件所在的根目录
	 * @param oldname 老文件
	 * @param newname 新文件
	 */
	public void renameFile(String path,String oldname,String newname){
		if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile=new File(path+"/"+oldname);
			File newfile=new File(path+"/"+newname);
			if(!oldfile.exists()){
				return;//重命名文件不存在
			}
			if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				System.out.println(newname+"已经存在！");
			else{
				oldfile.renameTo(newfile);
			}
		}else{
			System.out.println("新文件名和旧文件名相同...");
		}}


	public final static String FILE_EXTENSION_SEPARATOR = ".";

	private FileUtil() {
		throw new AssertionError();
	}

	/**
	 * 读取衣蛾文件
	 * @param filePath 文件所在的位置
	 * @param charsetName 编码类型
	 * @return 文件的内容
	 */
	public static StringBuilder readFile(String filePath, String charsetName) {
		File file = new File(filePath);
		StringBuilder fileContent = new StringBuilder("");
		if (file == null || !file.isFile()) {
			return null;
		}

		BufferedReader reader = null;
		try {
			InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
			reader = new BufferedReader(is);
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (!fileContent.toString().equals("")) {
					fileContent.append("\r\n");
				}
				fileContent.append(line);
			}
			reader.close();
			return fileContent;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 写入文件
	 * @param filePath 文件坐在的位置
	 * @param content 文件的内容
	 * @param append 是否是追加
	 * @return 是否成功
	 */
	public static boolean writeFile(String filePath, String content, boolean append) {
		if (StringUtils.isEmpty(content)) {
			return false;
		}

		FileWriter fileWriter = null;
		try {
			makeDirs(filePath);
			fileWriter = new FileWriter(filePath, append);
			fileWriter.write(content);
			fileWriter.close();
			return true;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 写入文件
	 * @param filePath 文件所在的目录
	 * @param contentList 文件的内容
	 * @param append 是否是追加
	 * @return 是否成功
	 */
	public static boolean writeFile(String filePath, List<String> contentList, boolean append) {
		if (ListUtils.isEmpty(contentList)) {
			return false;
		}

		FileWriter fileWriter = null;
		try {
			makeDirs(filePath);
			fileWriter = new FileWriter(filePath, append);
			int i = 0;
			for (String line : contentList) {
				if (i++ > 0) {
					fileWriter.write("\r\n");
				}
				fileWriter.write(line);
			}
			fileWriter.close();
			return true;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 在头部写入文件
	 * @param filePath 文件所在的目录
	 * @param content 文件的内容
	 * @return 是否成功
	 */
	public static boolean writeFile(String filePath, String content) {
		return writeFile(filePath, content, false);
	}

	/**
	 * 在头部写入文件
	 * @param filePath 文件所在的目录
	 * @param contentList 文件的内容
	 * @return 是否成功
	 */
	public static boolean writeFile(String filePath, List<String> contentList) {
		return writeFile(filePath, contentList, false);
	}

	/**
	 * 在文件的头部添加byte
	 * @param filePath  文件所在的目录
	 * @param stream 文件流
	 * @return 是否成功
	 */
	public static boolean writeFile(String filePath, InputStream stream) {
		return writeFile(filePath, stream, false);
	}

	/**
	 * 写入文件流
	 * @param filePath 文件所在的目录
	 * @param stream 文件流
	 * @param append 是否追加
	 * @return 是否添加成功
	 */
	public static boolean writeFile(String filePath, InputStream stream, boolean append) {
		return writeFile(filePath != null ? new File(filePath) : null, stream, append);
	}

	/**
	 * 在文件的开始的位置写inputstrean
	 * @param file 文件所在的位置
	 * @param stream inputstream
	 * @return 是否添加成功
	 */
	public static boolean writeFile(File file, InputStream stream) {
		return writeFile(file, stream, false);
	}

	/**
	 * write file
	 * @param file   the file to be opened for writing.
	 * @param stream the input stream
	 * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
	 * @return return true
	 * @throws RuntimeException if an error occurs while operator FileOutputStream
	 */
	public static boolean writeFile(File file, InputStream stream, boolean append) {
		OutputStream o = null;
		try {
			makeDirs(file.getAbsolutePath());
			o = new FileOutputStream(file, append);
			byte data[] = new byte[1024];
			int length = -1;
			while ((length = stream.read(data)) != -1) {
				o.write(data, 0, length);
			}
			o.flush();
			return true;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (o != null) {
				try {
					o.close();
					stream.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 移动文件
	 * @param sourceFilePath 文件的原始的位置
	 * @param destFilePath 文件要移到到的位置
	 */
	public static void moveFile(String sourceFilePath, String destFilePath) {
		if (TextUtils.isEmpty(sourceFilePath) || TextUtils.isEmpty(destFilePath)) {
			throw new RuntimeException("Both sourceFilePath and destFilePath cannot be null.");
		}
		moveFile(new File(sourceFilePath), new File(destFilePath));
	}

	/**
	 * 移动文件
	 * @param srcFile 原始的文件
	 * @param destFile 现在的文件
	 */
	public static void moveFile(File srcFile, File destFile) {
		boolean rename = srcFile.renameTo(destFile);
		if (!rename) {
			copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
			deleteFile(srcFile.getAbsolutePath());
		}
	}

	/**
	 * 对文件进行复制
	 * @param sourceFilePath 文件的原始的位置
	 * @param destFilePath 文件现在的位置
	 * @return 是否复制成功
	 */
	public static boolean copyFile(String sourceFilePath, String destFilePath) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(sourceFilePath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		}
		return writeFile(destFilePath, inputStream);
	}

	/**
	 * 读取一个文件到字符串集合
	 * @param filePath 文件所在的位置
	 * @param charsetName 编码
	 * @return 是否成功
	 */
	public static List<String> readFileToList(String filePath, String charsetName) {
		File file = new File(filePath);
		List<String> fileContent = new ArrayList<String>();
		if (file == null || !file.isFile()) {
			return null;
		}

		BufferedReader reader = null;
		try {
			InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
			reader = new BufferedReader(is);
			String line = null;
			while ((line = reader.readLine()) != null) {
				fileContent.add(line);
			}
			reader.close();
			return fileContent;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 获取一个文件的名字
	 * @param filePath  文件所在的位置
	 * @return 文件的名字
	 */
	public static String getFileNameWithoutExtension(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		int filePosi = filePath.lastIndexOf(File.separator);
		if (filePosi == -1) {
			return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
		}
		if (extenPosi == -1) {
			return filePath.substring(filePosi + 1);
		}
		return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
	}

	/**
	 /**
	 * 获取一个文件的名字
	 * @param filePath  文件所在的位置
	 * @return 文件的名字
	 */
	public static String getFileName(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int filePosi = filePath.lastIndexOf(File.separator);
		return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
	}

	/**
	 * 获取一个文件夹的名字
	 * @param filePath 文件夹所在的位置
	 * @return 文件夹的名字
	 */
	public static String getFolderName(String filePath) {

		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int filePosi = filePath.lastIndexOf(File.separator);
		return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
	}

	/**
	 * 得到文件的后缀
	 * @param filePath 文件所在的位置
	 * @return 文件的后缀
	 */
	public static String getFileExtension(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return filePath;
		}

		int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		int filePosi = filePath.lastIndexOf(File.separator);
		if (extenPosi == -1) {
			return "";
		}
		return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
	}

	/**
	 * 创建目录的文件名命名的这个文件,包括所需的完整的目录路径
	 * @param filePath 文件所在的位置
	 * @return 是否成功
	 */
	public static boolean makeDirs(String filePath) {
		String folderName = getFolderName(filePath);
		if (StringUtils.isEmpty(folderName)) {
			return false;
		}
		File folder = new File(folderName);
		return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
	}

	public static boolean makeFolders(String filePath) {
		return makeDirs(filePath);
	}

	public static File makeFile(String filePath) {
		File file = new File(filePath);
		if (!isFileExist(filePath)) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 判断文件是否存在
	 * @param filePath 文件所在位置
	 * @return 存在么
	 */
	public static boolean isFileExist(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return false;
		}

		File file = new File(filePath);
		return (file.exists() && file.isFile());
	}

	/**
	 *判断文件夹是否存在
	 * @param directoryPath 文件夹位置
	 * @return 存在么
	 */
	public static boolean isFolderExist(String directoryPath) {
		if (StringUtils.isBlank(directoryPath)) {
			return false;
		}

		File dire = new File(directoryPath);
		return (dire.exists() && dire.isDirectory());
	}

	/**
	 *删除文件
	 * @param path 文件所在位置
	 * @return 是否删除成功
	 */
	public static boolean deleteFile(String path) {
		if (StringUtils.isBlank(path)) {
			return true;
		}

		File file = new File(path);
		if (!file.exists()) {
			return true;
		}
		if (file.isFile()) {
			return file.delete();
		}
		if (!file.isDirectory()) {
			return false;
		}
		for (File f : file.listFiles()) {
			if (f.isFile()) {
				f.delete();
			} else if (f.isDirectory()) {
				deleteFile(f.getAbsolutePath());
			}
		}
		return file.delete();
	}

	/**
	 * 获取文件的长度
	 * @param path 文件所在的文职
	 * @return 文件的长度
	 */
	public static long getFileSize(String path) {
		if (StringUtils.isBlank(path)) {
			return -1;
		}

		File file = new File(path);
		return (file.exists() && file.isFile() ? file.length() : -1);
	}

	/**
	 * 获取文件的内容
	 * @param filePath  文件所在的位置
	 * @return 返回文件的内容
	 * @throws IOException io异常
	 */
	public byte[] getContent(String filePath) throws IOException {
		File file = new File(filePath);
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			System.out.println("file too big...");
			return null;
		}
		FileInputStream fi = new FileInputStream(file);
		byte[] buffer = new byte[(int) fileSize];
		int offset = 0;
		int numRead = 0;
		while (offset < buffer.length
				&& (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
			offset += numRead;
		}
		// 确保所有数据均被读取
		if (offset != buffer.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}
		fi.close();
		return buffer;
	}

	/**
	 * 转换成字节数组
	 * @param filename  文件的名字
	 * @return 字节数组
	 * @throws IOException io异常
	 */
	public static byte[] toByteArray(String filename) throws IOException {

		File f = new File(filename);
		if (!f.exists()) {
			throw new FileNotFoundException(filename);
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bos.close();
		}
	}

	//Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
	public static byte[] toByteArray3(String filename) throws IOException {

		FileChannel fc = null;
		try {
			fc = new RandomAccessFile(filename, "r").getChannel();
			MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,
					fc.size()).load();
			System.out.println(byteBuffer.isLoaded());
			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				// System.out.println("remain");
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 删除程序隐藏根目录日志
	public static void deleteCacheFile(String path) {
		File[] files = new File(path).listFiles();
		if (ArrayUtils.isEmpty(files)) return;
		for (File f :
				files) {
			if (f.isFile()) {
				if (f.getName().endsWith("txt") || f.getName().endsWith("wav")) {
					if (!f.getName().contains("URL")) {
						f.delete();
					}
				}
			} else {
				deleteCacheFile(f.getAbsolutePath());
			}
		}
	}
}
