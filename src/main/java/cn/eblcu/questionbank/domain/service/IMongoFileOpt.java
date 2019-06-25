package cn.eblcu.questionbank.domain.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface IMongoFileOpt {
    
    /**
     * @Author 焦冬冬
     * @Description 前端上传文件，保存到mongodb,并在关系型数据库保存fileId
     * @Date 16:33 2019/6/10
     * @Param 
     * @return 
     **/
    String uploadFile(MultipartFile multiportFile)throws Exception;
    
    /**
     * @Author 焦冬冬
     * @Description 根据fileId播放mongodb中的文件
     * @Date 16:38 2019/6/10
     * @Param 
     * @return 
     **/
    void playFile(String fileId, HttpServletResponse response)throws Exception;

    boolean deleteFile(String fileId)throws Exception;
}
