package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.domain.service.IMongoFileOpt;
import cn.eblcu.questionbank.ui.model.BaseModle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/mongo")
@Api(tags = "MongoDB文件处理API")
public class MongoFileApi {


    @Autowired
    private IMongoFileOpt mongoFileOpt;

    // 音频文件上传 返回上传到mongdb的filed
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ApiOperation(value = "文件上传")
    public BaseModle uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        return BaseModle.getSuccessData(mongoFileOpt.uploadFile(file));
    }

    // 音频文件下载
    @RequestMapping(value = "/downLoadFile", method = RequestMethod.GET)
    public void downLoadFile(@RequestParam(value = "id") String id,HttpServletResponse response) throws Exception {
        mongoFileOpt.playFile(id, response);
    }

    // 音频文件删除
    @RequestMapping(value = "/deleteFile", method = RequestMethod.DELETE)
    public BaseModle deleteFile(@RequestParam(value = "id") String id) throws Exception{
        mongoFileOpt.deleteFile(id);
        return BaseModle.getSuccessData();
    }
}
