package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.client.CheckToken;
import cn.eblcu.questionbank.domain.service.IQuestionService;
import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.QuestionModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CheckToken
@RestController
@RequestMapping(value = "/question")
@Slf4j
public class QuestionApi {

    @Autowired
    private IQuestionService questionService;

    @RequestMapping(value = "/insertQuestion",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "试题录入接口")
    public BaseModle insertQuestion(HttpServletRequest httpServletRequest,@RequestBody QuestionModel questionModel) throws Exception{

        questionService.insertQuestion(questionModel,httpServletRequest);
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
    public BaseModle deleteQuestion(@RequestParam int questionId) throws Exception{

        questionService.deleteByPrimaryKey(questionId);
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
                                             @ApiParam(value = "一级目录id") @RequestParam int categoryOne,
                                             @ApiParam(value = "二级目录id") @RequestParam int categoryTwo,
                                             @ApiParam(value = "课程id") @RequestParam int courseId,
                                             @ApiParam(value = "试题类型") @RequestParam(required = false) int questionType) throws Exception{

        Map<String,Object> map = new HashMap<>();
        map.put("categoryOne", categoryOne);
        map.put("categoryTwo", categoryTwo);
        map.put("courseId", courseId);
        map.put("questionType", questionType);
        return BaseModle.getSuccessData(questionService.selectQuestionList(httpServletRequest,map));
    }

    @RequestMapping(value = "/selectQuestionListByName",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据名称模糊查询试题列表")
    public BaseModle selectQuestionListByName(HttpServletRequest httpServletRequest,
                                              @ApiParam(value = "一级目录id") @RequestParam int categoryOne,
                                              @ApiParam(value = "二级目录id") @RequestParam int categoryTwo,
                                              @ApiParam(value = "课程id") @RequestParam int courseId,
                                              @ApiParam(value = "试题类型") @RequestParam(required = false) int questionType,
                                              @ApiParam(value = "试题名称") @RequestParam String questionBody) throws Exception{

        Map<String,Object> map = new HashMap<>();
        map.put("categoryOne", categoryOne);
        map.put("categoryTwo", categoryTwo);
        map.put("courseId", courseId);
        map.put("questionType", questionType);
        map.put("questionBody", questionBody);
        return BaseModle.getSuccessData(questionService.selectQuestionListByName(httpServletRequest,map));
    }
}
