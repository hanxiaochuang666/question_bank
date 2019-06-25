package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.client.CheckToken;
import cn.eblcu.questionbank.domain.service.ITestPaperQuestionService;
import cn.eblcu.questionbank.persistence.entity.dto.TestPaperQuestion;
import cn.eblcu.questionbank.ui.model.BaseModle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//@CheckToken
@RestController
@RequestMapping("/testPaperQuestion")
@Api(tags = "组卷API")
public class TestPaperQuestionApi {

    @Resource(name="testPaperQuestionService")
    private ITestPaperQuestionService testPaperQuestionService;

    @RequestMapping(value = "/list", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "组卷试题列表查询",notes = "组卷试题列表查询",httpMethod = "GET")
    public BaseModle list(@RequestParam int testPaperId)throws Exception{
        return BaseModle.getSuccessData(testPaperQuestionService.queryTestPaper(testPaperId));
    }


    @RequestMapping(value = "/saveTestPaperQuestion", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "保存试卷试题",notes = "保存试卷试题",httpMethod = "POST")
    public BaseModle saveTestPaperQuestion(@RequestBody List<TestPaperQuestion> testPaperQuestionLst)throws Exception{
        return testPaperQuestionService.saveTestPaperQuestion(testPaperQuestionLst);
    }
}
