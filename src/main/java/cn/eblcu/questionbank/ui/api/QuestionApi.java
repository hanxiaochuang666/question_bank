package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.client.CheckToken;
import cn.eblcu.questionbank.domain.service.IQuestionService;
import cn.eblcu.questionbank.infrastructure.util.SupperTokenUtils;
import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.QueryQuestionModel;
import cn.eblcu.questionbank.ui.model.QuestionModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CheckToken
@RestController
@RequestMapping(value = "/question")
@Slf4j
@Api(tags = "试题管理API")
public class QuestionApi {

    @Autowired
    private IQuestionService questionService;

    @RequestMapping(value = "/insertQuestion",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "试题录入接口")
    public BaseModle insertQuestion(HttpServletRequest httpServletRequest,@RequestBody QuestionModel questionModel) throws Exception{

        questionService.insertQuestion(questionModel,httpServletRequest);
        return BaseModle.getSuccessData();
    }

    @RequestMapping(value = "/importQuestion",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "试题批量导入接口")
    public BaseModle importQuestion(@ApiParam(value = "试题导入模板") @RequestParam(value = "file") MultipartFile file,
                                    @ApiParam(value = "一级目录") @RequestParam int categoryOne,
                                    @ApiParam(value = "二级目录") @RequestParam int categoryTwo,
                                    @ApiParam(value = "课程id") @RequestParam int courseId,
                                    @ApiParam(value = "机构id") @RequestParam int orgId,
                                    HttpServletRequest request) throws Exception{


        int userId = SupperTokenUtils.getUserByToken(request);
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("categoryOne", categoryOne);
        paraMap.put("categoryTwo", categoryTwo);
        paraMap.put("courseId", courseId);
        paraMap.put("orgId", orgId);
        paraMap.put("createUserId", userId);
        questionService.importQuestion(paraMap,file, request);
        return BaseModle.getSuccessData();
    }

    @RequestMapping(value = "/editQuestion",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "试题编辑接口")
    public BaseModle editQuestion(HttpServletRequest httpServletRequest,@RequestBody QuestionModel questionModel) throws Exception{

        questionService.editQuestion(questionModel,httpServletRequest);
        return BaseModle.getSuccessData();
    }

    @RequestMapping(value = "/deleteQuestion",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "试题删除接口")
    public BaseModle deleteQuestion(@ApiParam(value = "试题主键id") @RequestParam int questionId) throws Exception{

        questionService.deleteByPrimaryKey(questionId);
        return BaseModle.getSuccessData();
    }

    @RequestMapping(value = "/deleteQuestionList",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "试题批量删除接口")
    public BaseModle deleteQuestionList(@ApiParam(value = "试题主键id字符串，多个以;分割") @RequestParam String questionIdStr) throws Exception{

        String [] ids = questionIdStr.split(";");
        for (String id : ids) {
            questionService.deleteByPrimaryKey(Integer.parseInt(id));
        }
        return BaseModle.getSuccessData();
    }

    @RequestMapping(value = "/selectQuestionListCount",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "查询试题列表总数")
    public BaseModle selectQuestionListCount(HttpServletRequest httpServletRequest,
                                             @ApiParam(value = "一级目录id") @RequestParam int categoryOne,
                                             @ApiParam(value = "二级目录id") @RequestParam int categoryTwo,
                                             @ApiParam(value = "课程id") @RequestParam int courseId) throws Exception{

        Map<String,Object> map = new HashMap<>();
        map.put("categoryOne", categoryOne);
        map.put("categoryTwo", categoryTwo);
        map.put("courseId", courseId);
        return BaseModle.getSuccessData(questionService.selectQuestionListCount(httpServletRequest,map));
    }

    @RequestMapping(value = "/selectQuestionList",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "查询试题列表")
    public BaseModle selectQuestionList(HttpServletRequest httpServletRequest,
                                        @RequestBody @Valid QueryQuestionModel model) throws Exception{

        Map<String,Object> map = new HashMap<>();
        map.put("categoryOne", model.getCategoryOne());
        map.put("categoryTwo", model.getCategoryTwo());
        map.put("courseId", model.getCourseId());
        map.put("questionType", model.getQuestionType());
        map.put("questionBody", model.getQuestionBody());
        map.put("difficultyLevel", model.getDifficultyLevel());
        map.put("points", model.getKnowledgePoints());
        return BaseModle.getSuccessData(questionService.selectQuestionList(httpServletRequest,map));
    }



}
