package cn.eblcu.questionbank.infrastructure.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Slf4j
public class FileUtils {

    @Value("${file.imagePath}")
    private static String fileUrl;

    @Value("${localUrl}")
    private static String localUrl;

    public static String upLoadFile(MultipartFile file, String fileName) throws Exception {

        String filePath = fileUrl + fileName;
        File targetFile = new File(filePath);
        //判断文件父目录是否存在
        if (null != targetFile.getParentFile() && !targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        String url = "";
        try {
            //上传文件
            file.transferTo(targetFile);
            new FileInputStream(targetFile);
            log.info("文件绝对路径==============" + filePath + "\n");
//            url="http://域名/项目名/images/"+fileName;//正式项目
//            url = "http://localhost:8080/images/"+fileName;//本地运行项目
            url = localUrl + "/images/" + fileName;//本地运行项目
            log.info("文件对外暴露的虚拟路径=============" + url + "\n");
        } catch (IOException e) {
            throw new Exception(e);
        }
        return url;
    }

    /* TXT文件读取*/
    public static String readTxt(String filePath)throws Exception{
        File filename = new File(filePath);
        InputStreamReader reader = new InputStreamReader(
                new FileInputStream(filename)); // 建立一个输入流对象reader
        BufferedReader br = new BufferedReader(reader);
        StringBuilder res=new StringBuilder();
        String line=br.readLine();
        while(null!=line){
            res.append(line);
            line = br.readLine();
        }
        return res.toString();
    }

    /**
     * @Author 焦冬冬
     * @Description  文件下载
     * @Date 15:24 2019/3/21
     * @Param
     * @return
     **/
    public static void downLoad(HttpServletResponse response, String filePath, String fileName){
        File file = new File(filePath + fileName);
        if(file.exists()){
            response.setContentType("application/force-download");
            //支持中文名称
            try {
                fileName = URLEncoder.encode(fileName,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println(fileName);
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                try {
                    bis.close();
                    fis.close();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
}
