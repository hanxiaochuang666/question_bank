package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.domain.service.KnowledgePointsService;
import cn.eblcu.questionbank.ui.exception.BusinessException;
import cn.eblcu.questionbank.ui.model.BaseModle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/knowledge")
@Api(tags = "知识点管理API")
public class KnowledgePointsApi {

    @Autowired
    private KnowledgePointsService pointsService;

    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ApiOperation(value = "课程目录导入",notes = "课程目录导入",httpMethod = "POST")
    public BaseModle importExcel(@ApiParam(value = "file文件") @RequestParam(value = "file") MultipartFile file) throws BusinessException {

        return pointsService.importKnowledgePoints(file);
    }
    
}
