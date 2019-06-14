package cn.eblcu.questionbank.ui.config;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @desc
 * @Author：hanchuang
 * @Version 1.0
 * @Date：add on 17:20 2019/6/10
 */
@Configuration
@Component
public class GridConfig {


    @Autowired
    private MongoDbFactory mongoDbFactory;
    @Autowired
    private GridFSBucket gridFSBucket;

    public GridFsResource convertGridFSFile2Resource(GridFSFile gridFsFile) {
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFsFile.getObjectId());
        return new GridFsResource(gridFsFile, gridFSDownloadStream);
    }

    @Bean
    public GridFSBucket getGridFSBuckets() {
        MongoDatabase db = mongoDbFactory.getDb();
        return GridFSBuckets.create(db);
    }

}
