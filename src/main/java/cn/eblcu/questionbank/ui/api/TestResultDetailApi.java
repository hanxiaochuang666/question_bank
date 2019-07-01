package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.domain.service.ITestResultDetailService;
import cn.eblcu.questionbank.infrastructure.util.SupperTokenUtils;
import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.StatusCodeEnum;
import cn.eblcu.questionbank.ui.model.TestPaperAnswerViewModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/testResultDetail")
@Api(tags = "学生作业详情操作API")
public class TestResultDetailApi {
    private Logger logger= LoggerFactory.getLogger(TestResultDetailApi.class);

    @Resource(name="testResultDetailService")
    private ITestResultDetailService testResultDetailService;

    @RequestMapping(value = "/selectTestPaperInfo", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "学生作业/测试查询",notes = "学生作业/测试查询",httpMethod = "GET")
    //optType 0 开始答题；1继续答题；2：查看详情
    public BaseModle selectTestPaperInfo(HttpServletRequest HttpServletRequest, @RequestParam int testPaperId, @RequestParam int optType)throws Exception{
        int userId = SupperTokenUtils.getUserByToken(HttpServletRequest);
        if(userId==-1){
            logger.info("无效的用户，请注册或登录");
            return BaseModle.getFailData(StatusCodeEnum.NO_AUTHORITY.getCode(),"没有登录");
        }
        return BaseModle.getSuccessData(testResultDetailService.selectTestPaperInfo(userId,testPaperId,optType));
    }

    @RequestMapping(value = "/saveTestResultDetailInfo", method = RequestMethod.PUT ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "学生提交作业/测试",notes = "学生提交作业/测试",httpMethod = "PUT")
    public BaseModle saveTestResultDetailInfo(HttpServletRequest HttpServletRequest, @RequestBody TestPaperAnswerViewModel testPaperAnswerViewModel)throws Exception{
        int userId = SupperTokenUtils.getUserByToken(HttpServletRequest);
        if(userId==-1){
            logger.info("无效的用户，请注册或登录");
            return BaseModle.getFailData(StatusCodeEnum.NO_AUTHORITY.getCode(),"没有登录");
        }

        return testResultDetailService.saveTestResultDetailInfo(userId, testPaperAnswerViewModel);
    }
}
