package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.client.CheckToken;
import cn.eblcu.questionbank.domain.service.ITestPaperFormatService;
import cn.eblcu.questionbank.domain.service.ITestPaperService;
import cn.eblcu.questionbank.infrastructure.util.MapAndObjectUtils;
import cn.eblcu.questionbank.infrastructure.util.MapUtils;
import cn.eblcu.questionbank.infrastructure.util.StringUtils;
import cn.eblcu.questionbank.persistence.entity.dto.TestPaper;
import cn.eblcu.questionbank.persistence.entity.dto.TestPaperFormat;
import cn.eblcu.questionbank.ui.exception.BusinessException;
import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.PaperFormatInfo;
import cn.eblcu.questionbank.ui.model.StatusCodeEnum;
import cn.eblcu.questionbank.ui.model.TestPaperFormatViewModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CheckToken
@RestController
@RequestMapping("/testPaperFormat")
@Api(tags = "试卷组成管理API")
public class TestPaperFormatApi {
    private Logger logger=LoggerFactory.getLogger(TestPaperFormatApi.class);

    @Resource(name="testPaperFormatService")
    private ITestPaperFormatService testPaperFormatService;

    @Resource(name="testPaperService")
    private ITestPaperService testPaperService;

    @RequestMapping(value = "/queryTestPaperFormat", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "查询试卷组成",notes = "查询试卷组成",httpMethod = "GET")
    public BaseModle queryTestPaperFormat(@RequestParam Integer testPaperId){
        if(null==testPaperId || testPaperId<=0){
            logger.info("根据试卷组成testPaperFormatId参数错误");
            return BaseModle.getFailData(StatusCodeEnum.PARAM_ERROR.getCode(),"根据试卷组成testPaperFormatId参数错误");
        }
        Map<String, Object> initMap = MapUtils.initMap("testPaperId", testPaperId);
        List<TestPaperFormat> list = testPaperFormatService.selectList(initMap);
        return BaseModle.getSuccessData(list);
    }


    @RequestMapping(value = "/saveTestPaperFormat", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "保存试卷组成",notes = "保存试卷组成",httpMethod = "POST")
    public BaseModle saveTestPaperFormat(@Valid @RequestBody TestPaperFormatViewModel testPaperFormatViewModel)throws Exception{
        if(null==testPaperFormatViewModel.getTestPaperId()){
            logger.info("参数错误："+testPaperFormatViewModel.toString());
            return BaseModle.getFailData(StatusCodeEnum.PARAM_ERROR.getCode(),"参数错误："+testPaperFormatViewModel.toString());
        }
        TestPaper testPaper = testPaperService.selectByPrimaryKey(testPaperFormatViewModel.getTestPaperId());
        if(testPaper==null)
            return BaseModle.getFailData(StatusCodeEnum.PARAM_ERROR.getCode(),"试卷不存在");

        return testPaperFormatService.syncPaperFormatInfo(testPaperFormatViewModel);
    }
}
