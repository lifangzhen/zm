package com.lun.mlm.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class FTPUtils {

    private  FTPClient ftp;


    public FTPUtils(String addr, int port, String username, String password) throws IOException {
        ftp = new FTPClient();
        ftp.connect(addr,port);
        boolean login = ftp.login(username,password);
        System.out.println("登录成功："+login);
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        int reply;
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
        }
    }
    /**
     *
     * @param path 上传到ftp服务器哪个路径下
     * @return
     * @throws Exception
     */
    public void connect(String path) throws Exception {
        ftp.changeWorkingDirectory(path);
    }

    public boolean makeDirectory(String path) throws IOException {
        return ftp.makeDirectory(path);
    }

    public String upload(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
        FileInputStream input = (FileInputStream) multipartFile.getInputStream();
        String newFileName = IDGenerator.nextId()+"."+prefix;
        ftp.setControlEncoding("UTF-8");
        ftp.enterLocalPassiveMode();
        ftp.enterRemotePassiveMode();
        boolean result = ftp.storeFile(new String(newFileName.getBytes("UTF-8"),"iso-8859-1"), input);
        System.out.println("上传结果为:"+result);
        input.close();
        return newFileName;
    }

    /**
     *
     * @param file 上传的文件或文件夹
     * @throws Exception
     */
    public void upload(File file) throws Exception{
        if(file.isDirectory()){
            ftp.makeDirectory(file.getName());
            ftp.changeWorkingDirectory(file.getName());
            String[] files = file.list();
            for (int i = 0; i < files.length; i++) {
                File file1 = new File(file.getPath()+"\\"+files[i] );
                if(file1.isDirectory()){
                    upload(file1);
                    ftp.changeToParentDirectory();
                }else{
                    File file2 = new File(file.getPath()+"\\"+files[i]);
                    FileInputStream input = new FileInputStream(file2);
                    ftp.storeFile(file.getName(), input);
                    input.close();
                }
            }
        }else{
            File file2 = new File(file.getPath());
            FileInputStream input = new FileInputStream(file2);
            ftp.setControlEncoding("UTF-8");
            ftp.enterLocalPassiveMode();
            boolean result = ftp.storeFile(new String(file.getName().getBytes("UTF-8"),"iso-8859-1"), input);
            input.close();
        }
    }


    public void destroy() throws IOException {
        if(ftp != null){
            ftp.disconnect();
            ftp = null;
        }
    }


        public static void main(String[] args) throws Exception{
            FTPUtils t = new FTPUtils("39.105.95.181", 21, "ftptest", "ftptest");
            boolean isDirectory = t.makeDirectory("/var/www/html");
            System.out.println("创建目录是否成功： ======================" + isDirectory);
            t.connect("/var/www/html/");
            File file = new File("D:\\desk.png");
            t.upload(file);

//            FTPClient ftpClient = new FTPClient();
//            ftpClient.connect("39.105.95.181",21);
//            Boolean login = ftpClient.login("ftptest","ftptest");
//            System.out.println("登录："+login);
//
//            ftpClient.enterLocalPassiveMode();
//
//            File file = new File("D://desk.png");
//            FileInputStream inputStream = new FileInputStream(file);
//
//            Boolean store = ftpClient.storeFile("/var/www/html/desk.png", inputStream);
//            System.out.println("存储:"+store);


    }
}
