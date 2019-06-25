package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.ui.model.BaseModle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testResult")
@Api(tags = "试卷操作API")
public class TestResultApi {
    private Logger logger= LoggerFactory.getLogger(TestResultApi.class);

    @RequestMapping(value = "/syncTestPaper", method = RequestMethod.PUT ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "同步创建学生试卷",notes = "同步创建学生试卷",httpMethod = "PUT")
    public BaseModle syncTestPaper(@RequestParam Integer courseId,@RequestParam Integer studentId,@RequestParam String studentName){
        return BaseModle.getSuccessData();
    }
}
