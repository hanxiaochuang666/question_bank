package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.client.CheckToken;
import cn.eblcu.questionbank.domain.service.ITeacherCorrectService;
import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.TeacherPaperResultModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/teacherCorrect")
@Api(tags = "教师批改作业API")
@CheckToken
public class TeacherCorrectApi {

    @Autowired
    private ITeacherCorrectService correctService;

    @RequestMapping(value = "/getTestListByPaperId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取批改列表,包含作业和测试")
    public BaseModle getTestListByPaperId(@ApiParam(value = "要批改的试卷id字符串，多个用英文分号;分割开") @RequestParam String paperIds) throws Exception {

        return BaseModle.getSuccessData(correctService.getTestListByPaperId(paperIds));
    }

    @RequestMapping(value = "/getCorrectList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取批阅列表")
    public BaseModle getCorrectList(@ApiParam(value = "试卷id") @RequestParam String paperId,
                                    @ApiParam(value = "1:已批阅 0:未批阅") @RequestParam String type) throws Exception {
        return BaseModle.getSuccessData(correctService.getCorrectList(paperId,type));
    }

    @RequestMapping(value = "/getTestPaperInfo", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "教师评阅试卷查询",notes = "教师评阅试卷查询",httpMethod = "GET")
    public BaseModle getTestPaperInfo(@ApiParam(value = "试卷id") @RequestParam int testPaperId,
                                         @ApiParam(value = "学生id") @RequestParam int studentId)throws Exception{

        return BaseModle.getSuccessData(correctService.getTestPaperInfo(testPaperId,studentId));
    }

    @RequestMapping(value = "/saveTeacherCorrect", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "教师评阅试卷保存",notes = "教师评阅试卷保存",httpMethod = "POST")
    public BaseModle saveTeacherCorrect(@RequestBody TeacherPaperResultModel model)throws Exception{

        return BaseModle.getSuccessData(correctService.saveTeacherCorrect(model));
    }
}
