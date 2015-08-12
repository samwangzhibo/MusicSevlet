package org.music.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadBMP extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 response.setContentType("text/html; charset=UTF-8");
		 
	        // print query string or body parameter
	        // response.getWriter().print(new String(request.getParameter("qmsg").getBytes("ISO-8859-1"), "GBK"));


	      /*  SmartUpload smartUpload = new SmartUpload();

	        try {
	            smartUpload.initialize(this.getServletConfig(), request, response);
	            smartUpload.upload();
	            com.jspsmart.upload.File smartFile = smartUpload.getFiles().getFile(0);
	            if (!smartFile.isMissing()) {
	                String saveFileName = request.getSession().getServletContext().getRealPath("/")+"/data/" + smartFile.getFileName();
	                smartFile.saveAs(saveFileName, smartUpload.SAVE_PHYSICAL);

	                // print multipart parameter
	                response.getWriter().print(" ok: " + saveFileName);
	                //+ ", msg: " + smartUpload.getRequest().getParameter("msg"));
	            } else {
	                response.getWriter().print("missing...");
	            }
	        } catch (Exception e) {
	            response.getWriter().print(e);
	        }*/
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String Pic_Name=request.getParameter("username");
		System.out.println("随意了啊");
		final long MAX_SIZE = 30 * 1024 * 1024;// 设置上传文件最大为 30M  
		// 允许上传的文件格式的列表  
		final String[] allowedExt = new String[] { "jpg", "jpeg", "gif", "txt",  
				"doc", "docx", "mp3", "wma", "m4a","png" };  
		response.setContentType("text/html");  
		// 设置字符编码为UTF-8, 这样支持汉字显示  
		response.setCharacterEncoding("UTF-8");  

		// 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload  
		DiskFileItemFactory dfif = new DiskFileItemFactory();  
		dfif.setSizeThreshold(4096);// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘  
		dfif.setRepository(new File(request.getSession().getServletContext().getRealPath("/")  
				+ "ImagesUploadTemp"));// 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录  

		// 用以上工厂实例化上传组件  
		ServletFileUpload sfu = new ServletFileUpload(dfif);  
		// 设置最大上传尺寸  
		sfu.setSizeMax(MAX_SIZE);  

		PrintWriter out = response.getWriter();  
		// 从request得到 所有 上传域的列表  
		List fileList = null;  
		try {  
			fileList = sfu.parseRequest(request);  
		} catch (FileUploadException e) {// 处理文件尺寸过大异常  
			if (e instanceof SizeLimitExceededException) {  
				out.println("文件尺寸超过规定大小:" + MAX_SIZE + "字节<p />");  
				out.println("<a href=\"upload.html\" target=\"_top\">返回</a>");  
				return;  
			}  
			e.printStackTrace();  
		}  
		// 没有文件上传  
		if (fileList == null || fileList.size() == 0) {  
			out.println("请选择上传文件<p />");  
			out.println("<a href=\"upload.html\" target=\"_top\">返回</a>");  
			return;  
		}  
		// 得到所有上传的文件  
		Iterator fileItr = fileList.iterator();  
		// 循环处理所有文件  
		while (fileItr.hasNext()) {  
			FileItem fileItem = null;  
			String path = null;  
			long size = 0;  
			// 得到当前文件  
			fileItem = (FileItem) fileItr.next();  
			// 忽略简单form字段而不是上传域的文件域(<input type="text" />等)  
			if (fileItem == null || fileItem.isFormField()) {  
				continue;  
			}  
			// 得到文件的完整路径  
			path = fileItem.getName();     
			// 得到文件的大小  
			size = fileItem.getSize();  
			if ("".equals(path) || size == 0) {  
				out.println("请选择上传文件<p />");  
				out.println("<a href=\"upload.html\" target=\"_top\">返回</a>");  
				return;  
			}  

			// 得到去除路径的文件名  
			String t_name = path.substring(path.lastIndexOf("\\") + 1);  
			// 得到文件的扩展名(无扩展名时将得到全名)  
			String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);  
			// 拒绝接受规定文件格式之外的文件类型  
			int allowFlag = 0;  
			int allowedExtCount = allowedExt.length;  
			for (; allowFlag < allowedExtCount; allowFlag++) {  
				if (allowedExt[allowFlag].equals(t_ext))  
					break;  
			}  
			if (allowFlag == allowedExtCount) {  
				out.println("请上传以下类型的文件<p />");  
				for (allowFlag = 0; allowFlag < allowedExtCount; allowFlag++)  
					out.println("*." + allowedExt[allowFlag]  
							+ "&nbsp;&nbsp;&nbsp;");  
				out.println("<p /><a href=\"upload.html\" target=\"_top\">返回</a>");  
				return;  
			}  

			long now = System.currentTimeMillis();  
			// 根据系统时间生成上传后保存的文件名  
			String prefix = String.valueOf(now);  
			// 保存的最终文件完整路径,保存在web根目录下的ImagesUploaded目录下  
			String u_name = request.getSession().getServletContext().getRealPath("/") + "ImagesUploaded/"  
					+Pic_Name+".jpg";  
			System.out.println("最终的路径"+u_name);
			try {  
				// 保存文件  
				fileItem.write(new File(u_name));  
				out.println("文件上传成功. 已保存为: " + prefix + "." + t_ext  
						+ " &nbsp;&nbsp;文件大小: " + size + "字节<p />");  
				out.println("<a href=\"upload.html\" target=\"_top\">继续上传</a>");  
			} catch (Exception e) {  
				e.printStackTrace();  
			}  

		}
	}

}