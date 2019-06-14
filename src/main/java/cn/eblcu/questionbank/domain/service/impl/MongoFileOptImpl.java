package cn.eblcu.questionbank.domain.service.impl;

import cn.eblcu.questionbank.domain.service.IMongoFileOpt;
import cn.eblcu.questionbank.ui.config.GridConfig;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @ClassName MongoFileOptImpl
 * @Author 焦冬冬
 * @Date 2019/6/10 16:39
 **/
@Service("mongoFileOptService")
@Slf4j
public class MongoFileOptImpl implements IMongoFileOpt {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridConfig gridConfig;

    @Override
    public boolean uploadFile(MultipartFile multiportFile)throws Exception{
        //1.存储文件
        String originalFilename = multiportFile.getOriginalFilename();
        log.info("上传文件名："+originalFilename);
        InputStream ins = multiportFile.getInputStream();
        String contentType = multiportFile.getContentType();
        ObjectId fileId = gridFsTemplate.store(ins, originalFilename,contentType);
        log.info("fileId="+fileId.toString());
        //TODO 2.将fileId维护到关系型数据库
        return false;
    }

    @Override
    public void playFile(String fileId, HttpServletResponse response)throws Exception {
//        //1.从mongodb中读取文件流
//        Query query = Query.query(Criteria.where("_id").is(fileId));
//        GridFSFile gridFSFile = gridFsTemplate.findOne(query);
//        GridFSDownloadStream in = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
//        GridFsResource resource = new GridFsResource(gridFSFile,in);
//        InputStream inputStream = resource.getInputStream();
//        //2.写入到response
//        response.addHeader("Accept-Ranges", "bytes");
//        response.addHeader("Content-Type", "audio/mpeg;charset=UTF-8");
//        OutputStream os = response.getOutputStream();
//        byte[] b = new byte[1024];
//        int len=inputStream.read(b);
//        int count=0;
//        while(len!=-1){
//            os.write(b,0,len);
//            count+=len;
//            len=inputStream.read(b);
//        }
//        response.addHeader("Content-Length", count + "");
//        os.close();
//        inputStream.close();

        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(fileId)));
        GridFsResource fsResource = gridConfig.convertGridFSFile2Resource(gridFSFile);
        IOUtils.copy(fsResource.getInputStream(),response.getOutputStream());
    }

    @Override
    public boolean deleteFile(String fileId) throws Exception {
        gridFsTemplate.delete(new Query().addCriteria(Criteria.where("_id").is(fileId)));
        return true;
    }
}
