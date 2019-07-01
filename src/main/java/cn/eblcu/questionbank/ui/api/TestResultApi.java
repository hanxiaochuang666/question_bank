package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.domain.service.ITestResultService;
import cn.eblcu.questionbank.infrastructure.util.StringUtils;
import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.StatusCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/testResult")
@Api(tags = "学生作业操作API")
public class TestResultApi {
    private Logger logger= LoggerFactory.getLogger(TestResultApi.class);

    @Resource(name="testResultService")
    private ITestResultService testResultService;


    @RequestMapping(value = "/syncTestPaper", method = RequestMethod.PUT ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "同步创建学生试卷",notes = "同步创建学生试卷",httpMethod = "PUT")
    public BaseModle syncTestPaper(@RequestParam Integer userId,@RequestParam Integer courseId,@RequestParam Integer studentId,@RequestParam String studentName){
        return testResultService.syncTestPaper(userId,courseId,studentId,studentName);
    }

    @RequestMapping(value = "/addTestCallBack", method = RequestMethod.PUT ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "添加作业/测试回调",notes = "添加作业/测试回调",httpMethod = "PUT")
    public BaseModle addTestCallBack(@RequestParam Integer courseId, @RequestParam Integer testPaperId){
        return testResultService.addTestCallBack(courseId,testPaperId);
    }

    @RequestMapping(value = "/getStudentCourse", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "学生作业/测试列表查询",notes = "学生作业/测试列表查询",httpMethod = "GET")
    public BaseModle getStudentCourse(@RequestParam Integer useType,@RequestParam Integer studentId,@RequestParam(required = false) Integer courseId)throws Exception{
        return BaseModle.getSuccessData(testResultService.getStudentCourseLst(useType,studentId,courseId));
    }
}
