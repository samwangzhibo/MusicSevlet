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
		System.out.println("�����˰�");
		final long MAX_SIZE = 30 * 1024 * 1024;// �����ϴ��ļ����Ϊ 30M  
		// �����ϴ����ļ���ʽ���б�  
		final String[] allowedExt = new String[] { "jpg", "jpeg", "gif", "txt",  
				"doc", "docx", "mp3", "wma", "m4a","png" };  
		response.setContentType("text/html");  
		// �����ַ�����ΪUTF-8, ����֧�ֺ�����ʾ  
		response.setCharacterEncoding("UTF-8");  

		// ʵ����һ��Ӳ���ļ�����,���������ϴ����ServletFileUpload  
		DiskFileItemFactory dfif = new DiskFileItemFactory();  
		dfif.setSizeThreshold(4096);// �����ϴ��ļ�ʱ������ʱ����ļ����ڴ��С,������4K.���ڵĲ��ֽ���ʱ����Ӳ��  
		dfif.setRepository(new File(request.getSession().getServletContext().getRealPath("/")  
				+ "ImagesUploadTemp"));// ���ô����ʱ�ļ���Ŀ¼,web��Ŀ¼�µ�ImagesUploadTempĿ¼  

		// �����Ϲ���ʵ�����ϴ����  
		ServletFileUpload sfu = new ServletFileUpload(dfif);  
		// ��������ϴ��ߴ�  
		sfu.setSizeMax(MAX_SIZE);  

		PrintWriter out = response.getWriter();  
		// ��request�õ� ���� �ϴ�����б�  
		List fileList = null;  
		try {  
			fileList = sfu.parseRequest(request);  
		} catch (FileUploadException e) {// �����ļ��ߴ�����쳣  
			if (e instanceof SizeLimitExceededException) {  
				out.println("�ļ��ߴ糬���涨��С:" + MAX_SIZE + "�ֽ�<p />");  
				out.println("<a href=\"upload.html\" target=\"_top\">����</a>");  
				return;  
			}  
			e.printStackTrace();  
		}  
		// û���ļ��ϴ�  
		if (fileList == null || fileList.size() == 0) {  
			out.println("��ѡ���ϴ��ļ�<p />");  
			out.println("<a href=\"upload.html\" target=\"_top\">����</a>");  
			return;  
		}  
		// �õ������ϴ����ļ�  
		Iterator fileItr = fileList.iterator();  
		// ѭ�����������ļ�  
		while (fileItr.hasNext()) {  
			FileItem fileItem = null;  
			String path = null;  
			long size = 0;  
			// �õ���ǰ�ļ�  
			fileItem = (FileItem) fileItr.next();  
			// ���Լ�form�ֶζ������ϴ�����ļ���(<input type="text" />��)  
			if (fileItem == null || fileItem.isFormField()) {  
				continue;  
			}  
			// �õ��ļ�������·��  
			path = fileItem.getName();     
			// �õ��ļ��Ĵ�С  
			size = fileItem.getSize();  
			if ("".equals(path) || size == 0) {  
				out.println("��ѡ���ϴ��ļ�<p />");  
				out.println("<a href=\"upload.html\" target=\"_top\">����</a>");  
				return;  
			}  

			// �õ�ȥ��·�����ļ���  
			String t_name = path.substring(path.lastIndexOf("\\") + 1);  
			// �õ��ļ�����չ��(����չ��ʱ���õ�ȫ��)  
			String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);  
			// �ܾ����ܹ涨�ļ���ʽ֮����ļ�����  
			int allowFlag = 0;  
			int allowedExtCount = allowedExt.length;  
			for (; allowFlag < allowedExtCount; allowFlag++) {  
				if (allowedExt[allowFlag].equals(t_ext))  
					break;  
			}  
			if (allowFlag == allowedExtCount) {  
				out.println("���ϴ��������͵��ļ�<p />");  
				for (allowFlag = 0; allowFlag < allowedExtCount; allowFlag++)  
					out.println("*." + allowedExt[allowFlag]  
							+ "&nbsp;&nbsp;&nbsp;");  
				out.println("<p /><a href=\"upload.html\" target=\"_top\">����</a>");  
				return;  
			}  

			long now = System.currentTimeMillis();  
			// ����ϵͳʱ�������ϴ��󱣴���ļ���  
			String prefix = String.valueOf(now);  
			// ����������ļ�����·��,������web��Ŀ¼�µ�ImagesUploadedĿ¼��  
			String u_name = request.getSession().getServletContext().getRealPath("/") + "ImagesUploaded/"  
					+Pic_Name+".jpg";  
			System.out.println("���յ�·��"+u_name);
			try {  
				// �����ļ�  
				fileItem.write(new File(u_name));  
				out.println("�ļ��ϴ��ɹ�. �ѱ���Ϊ: " + prefix + "." + t_ext  
						+ " &nbsp;&nbsp;�ļ���С: " + size + "�ֽ�<p />");  
				out.println("<a href=\"upload.html\" target=\"_top\">�����ϴ�</a>");  
			} catch (Exception e) {  
				e.printStackTrace();  
			}  

		}
	}

}