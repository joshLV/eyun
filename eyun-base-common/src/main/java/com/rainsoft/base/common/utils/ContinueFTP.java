package com.rainsoft.base.common.utils;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 支持断点续传的FTP实用类
 *
 * @version 0.1 实现基本断点上传下载
 * @version 0.2 实现上传下载进度汇报
 * @version 0.3 实现中文目录创建及中文文件创建，添加对于中文的支持
 */
public class ContinueFTP{

	private static final Logger logger = LoggerFactory.getLogger(ContinueFTP.class);


	public FTPClient ftpClient;

	private static ContinueFTP instance;

	/**单列模式 只new一次 以后随时拿随时用**/
	public static synchronized ContinueFTP getInstance() {
		if (instance == null) {
			instance = new ContinueFTP().define();
		}
		return instance;
	}

	/**文件服务器域名**/
	private String domainName;
	/**ftp文件服务器IP**/
	private String serverIP;
	/**ftp文件服务器端口**/
	private Integer port;
	/**ftp文件服务器用户名**/
	private String ftpUserName;
	/**ftp文件服务器密码**/
	private String ftpPwd;

	/**
	 * 读取配置文件 获取图片ftp配置
	 * @return
	 */
	public ContinueFTP define() {
		this.setServerIP(SystemConfigUtil.getValue("ftpUrl"));
		this.setFtpUserName(SystemConfigUtil.getValue("ftpUser"));
		this.setFtpPwd(SystemConfigUtil.getValue("ftpPwd"));
		this.setPort(SystemConfigUtil.getIntValue("ftpPort"));
		this.setDomainName(SystemConfigUtil.getValue("ftpPicUrl"));
		return this;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getFtpUserName() {
		return ftpUserName;
	}

	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}

	public String getFtpPwd() {
		return ftpPwd;
	}

	public void setFtpPwd(String ftpPwd) {
		this.ftpPwd = ftpPwd;
	}
	// 枚举类UploadStatus代码
	public enum UploadStatus {
		Create_Directory_Fail,     // 远程服务器相应目录创建失败
		Create_Directory_Success,  // 远程服务器闯将目录成功
		Upload_New_File_Success,   // 上传新文件成功
		Upload_New_File_Failed,    // 上传新文件失败
		File_Exits,                // 文件已经存在
		Remote_Bigger_Local,       // 远程文件大于本地文件
		Upload_From_Break_Success, // 断点续传成功
		Upload_From_Break_Failed,  // 断点续传失败
		Delete_Remote_Faild,       // 删除远程文件失败
		Upload_File_BStop;         //上传取消
	}

	// 枚举类DownloadStatus代码
	public enum DownloadStatus {
		Remote_File_Noexist,         // 远程文件不存在
		Local_Bigger_Remote,         // 本地文件大于远程文件
		Download_From_Break_Success, // 断点下载文件成功
		Download_From_Break_Failed,  // 断点下载文件失败
		Download_New_Success,        // 全新下载文件成功
		Download_New_Failed;         // 全新下载文件失败
	}
	// 枚举类文件状态代码
	public enum FiledeleteStatus {
		File_Delete_Noexist,         //远程文件不存在
		File_Delete_Success,        // 文件删除成功
		File_Delete_Failed;         // 文件删除失败
	}
	/**限制外界不能再去new它**/
	private ContinueFTP() {
		ftpClient = new FTPClient();
		// 设置将过程中使用到的命令输出到控制台
		this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

	}
	/**
	 * 连接到FTP服务器
	 *
	 * @param hostname
	 *            主机名
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 是否连接成功
	 * @throws java.io.IOException
	 */
	public boolean connect(String hostname, int port, String username,String password) throws IOException {
		ftpClient.connect(hostname, port);
		ftpClient.setControlEncoding("GBK");
		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			if (ftpClient.login(username, password)) {
				return true;
			}
		}
		disconnect();
		return false;
	}


	/**
	 * 上传文件到FTP服务器，支持断点续传
	 *
	 * @param local
	 *            本地文件名称，绝对路径
	 * @param remote
	 *            远程文件路径，使用/home/directory1/subdirectory/file.ext或是
	 *            http://www.guihua.org /subdirectory/file.ext
	 * @return 上传结果
	 * @throws java.io.IOException
	 */
	public UploadStatus upload(String local, String remote) throws IOException {
		// 设置PassiveMode传输
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制流的方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.setControlEncoding("GBK");
		UploadStatus result;
		// 对远程目录的处理
		String remoteFileName = remote;
		if (remote.contains("/")) {
			remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
			// 创建服务器远程目录结构，创建失败直接返回
			if (CreateDirecroty(remote, ftpClient) == UploadStatus.Create_Directory_Fail) {
				return UploadStatus.Create_Directory_Fail;
			}
		}

		// 检查远程是否存在文件
		ftpClient.setControlEncoding("GBK");
		FTPFile[] files = ftpClient.listFiles(new String(remoteFileName.getBytes("GBK"), "iso-8859-1"));
		if (files.length == 1) {
			long remoteSize = files[0].getSize();
			File f = new File(local);
			long localSize = f.length();
			if (remoteSize == localSize) {
				return UploadStatus.File_Exits;
			} else if (remoteSize > localSize) {
				return UploadStatus.Remote_Bigger_Local;
			}

			// 尝试移动文件内读取指针,实现断点续传
			result = uploadFile(remoteFileName, f, ftpClient, remoteSize);

			// 如果断点续传没有成功，则删除服务器上文件，重新上传
			if (result == UploadStatus.Upload_From_Break_Failed) {
				if (!ftpClient.deleteFile(remoteFileName)) {
					return UploadStatus.Delete_Remote_Faild;
				}
				result = uploadFile(remoteFileName, f, ftpClient,0);
			};
		} else {
			result = uploadFile(remoteFileName, new File(local), ftpClient, 0);
		}
		return result;
	}
	/**
	 * 上传文件到服务器,新上传和断点续传
	 *
	 * @param remoteFile
	 *            远程文件名，在上传之前已经将服务器工作目录做了改变
	 * @param localFile
	 *            本地文件 File句柄，绝对路径
	 * @param ftpClient
	 *            FTPClient 引用
	 * @return
	 * @throws java.io.IOException
	 */
	public UploadStatus uploadFile(String remoteFile,File localFile,FTPClient ftpClient, long remoteSize) throws IOException {
		UploadStatus status;

		// 显示进度的上传
		long step = localFile.length() / 100;
		long process = 0;
		long localreadbytes = 0L;
		RandomAccessFile raf = new RandomAccessFile(localFile, "r");
		OutputStream out = ftpClient.appendFileStream(new String(remoteFile.getBytes("GBK"), "iso-8859-1"));
		// 断点续传
		if (remoteSize > 0) {
			ftpClient.setRestartOffset(remoteSize);
			process = remoteSize / step;
			raf.seek(remoteSize);
			localreadbytes = remoteSize;
		}
		byte[] bytes = new byte[1024];
		int c;
		while ((c = raf.read(bytes)) != -1) {
			out.write(bytes, 0, c);
			localreadbytes += c;
			if (localreadbytes / step != process) {
				process = localreadbytes / step;
				//logger.info("上传进度"+process);
			}
		}
		out.flush();
		raf.close();
		out.close();
		boolean result = ftpClient.completePendingCommand();
		if (remoteSize > 0) {
			status = result ? UploadStatus.Upload_From_Break_Success : UploadStatus.Upload_From_Break_Failed;
		} else {
			status = result ? UploadStatus.Upload_New_File_Success : UploadStatus.Upload_New_File_Failed;
		}
		return status;
	}


	/**
	 * 断开与远程服务器的连接
	 * @throws java.io.IOException
	 *
	 * @throws java.io.IOException
	 */
	public void disconnect() throws IOException{
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	/**
	 * 递归创建远程服务器目录
	 *
	 * @param remote
	 *            远程服务器文件绝对路径
	 * @param ftpClient
	 *            FTPClient 对象
	 * @return 目录创建是否成功
	 * @throws java.io.IOException
	 */
	public UploadStatus CreateDirecroty(String remote, FTPClient ftpClient)throws IOException {
		UploadStatus status = UploadStatus.Create_Directory_Success;
		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		if (!directory.equalsIgnoreCase("/")
				&& !ftpClient.changeWorkingDirectory(new String(directory
						.getBytes("GBK"), "iso-8859-1"))) {
			// 如果远程目录不存在，则递归创建远程服务器目录
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = new String(remote.substring(start, end)
						.getBytes("GBK"), "iso-8859-1");
				if (!ftpClient.changeWorkingDirectory(subDirectory)) {
					if (ftpClient.makeDirectory(subDirectory)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						logger.error("创建目录失败");
						return UploadStatus.Create_Directory_Fail;
					}
				}
				start = end + 1;
				end = directory.indexOf("/", start);
				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return status;
	}

	public UploadStatus CreateDirecroty(String small)throws IOException {
		UploadStatus status = UploadStatus.Create_Directory_Success;
		String subDirectory = new String(small.getBytes("GBK"), "iso-8859-1");
		if (!ftpClient.changeWorkingDirectory(subDirectory)) {
			if (ftpClient.makeDirectory(subDirectory)) {
				ftpClient.changeWorkingDirectory(subDirectory);
			} else {
				logger.error("创建目录失败");
				return UploadStatus.Create_Directory_Fail;
			}
		}
		return status;
	}
	/**
	 * 转换文件大小
	 * @param fileS
	 * @return
	 */
	public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
	/**
	 * 从FTP服务器删除文件
	 *
	 * @param remote
	 *         远程文件路径
	 * @return
	 * @throws java.io.IOException
	 * @throws java.io.UnsupportedEncodingException
	 * @throws InterruptedException
	 * @throws InterruptedException
	 */
	public FiledeleteStatus deleteFlie(String remote) throws UnsupportedEncodingException, IOException{
		// 检查远程文件是否存在
		FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("GBK"), "iso-8859-1"));
			if (files.length != 1) {
				logger.error("远程文件不存在");
				return FiledeleteStatus.File_Delete_Noexist;
			}
			if(ftpClient.deleteFile(new String(remote.getBytes("GBK"), "iso-8859-1"))){
				logger.info("远程文件删除成功");
				return FiledeleteStatus.File_Delete_Success;
			}
		logger.error("远程文件 删除失败");
		return FiledeleteStatus.File_Delete_Failed;
	}

    /**
     * 批量删除ftp文件
     * @param paths 文件url
     * @throws IOException
     */
	public void deleteFlie(List<String> paths) throws IOException {
		/**获取ftp服务器路径及其缩略图的路径**/
		paths = getLinuxPath(paths);
		/**开始批量删除**/
		for (String s : paths) {
			try {
				boolean b = ftpClient.deleteFile(new String(s.getBytes("gb2312"), "iso-8859-1"));
				if (b) {
					logger.info("远程文件删除成功:" + s);
				} else {
					logger.info("文件不存在或者文件已删除:" + s);
				}
			} catch (IOException e) {
				logger.error("IO异常--->远程文件 删除失败" + s);
			}
		}
	}

	/**
	 * 将图片url转成linux的真实路径 并且加入缩略图的路径
	 * @param paths 图片url 例如：域名/img/union/10012439/201512/11/1449822887915ydfdf9.jpg
	 * @return 图片linux的真实路径和缩略图的路径
	 */
	public List<String> getLinuxPath(List<String> paths){
		List<String> linuxPath = new ArrayList<String>();
		/**原图片路径**/
		String realPath ="";
		/**缩略图路径**/
		String smallPath = "";
		for (String s : paths) {
			realPath = SystemConfigUtil.getValue("linuxPath") + s.replace(SystemConfigUtil.getValue("ftpPicUrl"), "");
			int i = realPath.lastIndexOf("/");
			smallPath = realPath.substring(0, i) + "/small" + realPath.substring(i);
			linuxPath.add(realPath);
			linuxPath.add(smallPath);
		}
		return linuxPath;
	}



	/**
	 * 从远处ftp服务器下载文件
	 * @param ftpFileName
	 * @param locaFileName
	 * @param response
	 * @return
	 * @throws java.io.IOException
	 */
	public DownloadStatus downFile(String ftpFileName, String locaFileName, HttpServletResponse response) throws IOException {
		boolean isSuccessed = false;
		int endIndex = ftpFileName.lastIndexOf("/");
		//文件目录
		String fileDirectory = ftpFileName.substring(0, endIndex);
		//服务器文件名
		String fileName = ftpFileName.substring(endIndex + 1);
		if(StringUtil.isEmpty(locaFileName)){
			//获取下载文件名称
			locaFileName = fileName;
		}
		// 设置PassiveMode传输
		ftpClient.enterLocalPassiveMode();
		//转移到FTP服务器目录
		ftpClient.changeWorkingDirectory(fileDirectory);
		// 设置以二进制流的方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		//获取当前目录下所有文件
        FTPFile[] fs = ftpClient.listFiles();
        for(FTPFile ff:fs){
            if(ff.getName().equals(fileName)){
            	 //定义缓冲流
                BufferedOutputStream bufos = null;
                try{
	        		//设置要显示在保存窗口的文件名，如果文件名中有中文的话，则要设置字符集，否则会出现乱码。另外，要写上文件后缀名
	                locaFileName = new String(locaFileName.getBytes("GB2312"),"ISO-8859-1");
	                //locaFileName=java.net.URLEncoder.encode(locaFileName,"ISO-8859-1");
	            	response.setContentType("application/x-msdownload");
	            	//该步是最关键的一步，使用setHeader()方法弹出"是否要保存"的对话框，打引号的部分都是固定的值，不要改变
	                response.setHeader("Content-disposition","attachment;filename=" + locaFileName);
	                //获得一个 ServletOutputStream(向客户端发送二进制数据的输出流)对象
	                bufos = new BufferedOutputStream(response.getOutputStream());
	                isSuccessed = ftpClient.retrieveFile(ff.getName(), bufos);
                }finally{
	            	 if(bufos != null){
	            		 bufos.close();
	            	 }
                }
                //退出循环
                break;
            }
        }

        if(isSuccessed){
        	return DownloadStatus.Download_New_Success;
        }else{
        	return DownloadStatus.Download_New_Failed;
        }
	}
}