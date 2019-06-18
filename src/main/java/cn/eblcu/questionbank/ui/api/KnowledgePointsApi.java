package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.domain.service.IKnowledgePointsService;
import cn.eblcu.questionbank.ui.exception.BusinessException;
import cn.eblcu.questionbank.ui.model.BaseModle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/knowledge")
@Api(tags = "知识点管理API")
public class KnowledgePointsApi {
    @Autowired
    private IKnowledgePointsService pointsService;

    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ApiOperation(value = "课程目录导入",notes = "课程目录导入",httpMethod = "POST")
    public BaseModle importExcel(@ApiParam(value = "file文件") @RequestParam(value = "file") MultipartFile file,
                                 @RequestParam int categoryOne,
                                 @RequestParam int categoryTwo,
                                 @RequestParam int courseId,
                                 @RequestParam String courseName,
                                 @RequestParam int orgId,
                                 @RequestParam int createUserId) throws BusinessException {

        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("categoryOne", categoryOne);
        paraMap.put("categoryTwo", categoryTwo);
        paraMap.put("courseId", courseId);
        paraMap.put("courseName", courseName);
        paraMap.put("orgId", orgId);
        paraMap.put("createUserId", createUserId);
        return pointsService.importKnowledgePoints(file,paraMap);
    }

    @RequestMapping(value = "/getKnowledgePoints", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "查询课程目录结构",notes = "查询课程目录结构")
    public BaseModle getKnowledgePoints(@RequestParam int courseId) throws BusinessException {

        return pointsService.getKnowledgePoints(courseId);
    }
}
