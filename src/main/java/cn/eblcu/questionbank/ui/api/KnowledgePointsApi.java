package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.domain.service.IKnowledgePointsService;
import cn.eblcu.questionbank.infrastructure.util.SupperTokenUtils;
import cn.eblcu.questionbank.ui.exception.BusinessException;
import cn.eblcu.questionbank.ui.model.BaseModle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/knowledge")
@Api(tags = "知识点管理API",description = "包含：\n" +
        "1、课程目录导入\n" +
        "2、查询课程目录结构\n" +
        "3、修改课程目录\n" +
        "4、添加课程目录结构\n" +
        "5、删除课程目录结构\n")
public class KnowledgePointsApi {
    @Autowired
    private IKnowledgePointsService pointsService;

    @RequestMapping(value = "/importExcel", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "课程目录导入",notes = "课程目录导入",httpMethod = "POST")
    public BaseModle importExcel(@ApiParam(value = "file文件") @RequestParam(value = "file") MultipartFile file,
                                 @ApiParam(value = "一级目录") @RequestParam int categoryOne,
                                 @ApiParam(value = "二级目录") @RequestParam int categoryTwo,
                                 @ApiParam(value = "课程id") @RequestParam int courseId,
                                 @ApiParam(value = "课程名称") @RequestParam String courseName,
                                 @ApiParam(value = "机构id") @RequestParam int orgId,
                                 HttpServletRequest request) throws Exception {

        int userId = SupperTokenUtils.getUserByToken(request);
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("categoryOne", categoryOne);
        paraMap.put("categoryTwo", categoryTwo);
        paraMap.put("courseId", courseId);
        paraMap.put("courseName", courseName);
        paraMap.put("orgId", orgId);
        paraMap.put("createUserId", userId);
        return pointsService.importKnowledgePoints(file,paraMap);
    }

    @RequestMapping(value = "/getKnowledgePoints", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "查询课程目录结构",notes = "查询课程目录结构")
    public BaseModle getKnowledgePoints(@ApiParam(value = "课程id") @RequestParam int courseId) throws BusinessException {

        return pointsService.getKnowledgePoints(courseId);
    }

    @RequestMapping(value = "/editKnowledgePoints", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "修改课程目录结构",notes = "修改课程目录结构")
    public BaseModle editKnowledgePoints(@ApiParam(value = "知识点主键id") @RequestParam int pointDetailId,
                                         @ApiParam(value = "修改后名称") @RequestParam String name) throws BusinessException {

        return pointsService.editKnowledgePoints(pointDetailId,name);
    }

    @RequestMapping(value = "/addKnowledgePoints", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "添加课程目录结构",notes = "添加课程目录结构")
    public BaseModle addKnowledgePoints(@ApiParam(value = "课程id") @RequestParam int courseId,
                                        @ApiParam(value = "父节点id") @RequestParam int parentId,
                                        @ApiParam(value = "章节名称") @RequestParam String name) throws BusinessException {

        return pointsService.addKnowledgePoints(courseId, parentId,name);
    }

    @RequestMapping(value = "/deleteKnowledgePoints", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "删除课程目录结构",notes = "删除课程目录结构")
    public BaseModle deleteKnowledgePoints(@ApiParam(value = "知识点主键id") @RequestParam int pointDetailId) throws BusinessException {

        return pointsService.deleteKnowledgePoints(pointDetailId);
    }
}
