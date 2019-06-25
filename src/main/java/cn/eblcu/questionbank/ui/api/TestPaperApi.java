package cn.eblcu.questionbank.ui.api;


import cn.eblcu.questionbank.client.CheckToken;
import cn.eblcu.questionbank.domain.service.ITestPaperService;
import cn.eblcu.questionbank.domain.service.ITestResultService;
import cn.eblcu.questionbank.infrastructure.util.*;
import cn.eblcu.questionbank.persistence.entity.dto.TestPaper;
import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.StatusCodeEnum;
import cn.eblcu.questionbank.ui.model.TestPaperCreateModel;
import cn.eblcu.questionbank.ui.model.TestPaperViewModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

//@CheckToken
@RestController
@RequestMapping("/testPaper")
@Api(tags = "试卷管理API")
public class TestPaperApi {

    private Logger logger=LoggerFactory.getLogger(TestPaperApi.class);

    @Resource(name="testPaperService")
    private ITestPaperService testPaperService;

    @Resource(name="testResultService")
    private ITestResultService testResultService;

    @RequestMapping(value = "/list", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "查询试卷列表",notes = "查询试卷L列表",httpMethod = "POST")
    public BaseModle list(HttpServletRequest HttpServletRequest,@RequestBody TestPaperViewModel testPaperViewModel)throws Exception{
        int userId = SupperTokenUtils.getUserByToken(HttpServletRequest);
        if(userId==-1){
            logger.info("无效的用户，请注册或登录");
            return BaseModle.getFailData(StatusCodeEnum.NO_AUTHORITY.getCode(),"没有登录");
        }
        Map<String, Object> param = MapAndObjectUtils.ObjectToMap2(testPaperViewModel);
        if(!StringUtils.isEmpty(param.get("page")) && !StringUtils.isEmpty(param.get("limit"))){
            int page = Integer.valueOf(param.get("page").toString()).intValue();
            int pagesize=Integer.valueOf(param.get("limit").toString()).intValue();
            int __currentIndex__=(page-1)*pagesize;
            param.put("__currentIndex__",__currentIndex__);
            param.put("__pageSize__",pagesize);
        }
        param.put("createUser",userId);
        logger.info("list请求数据:"+param.toString());
        long count = testPaperService.selectCount(param);
        if (count<=0)
            return BaseModle.getSuccessData(null,count);
        List<TestPaper> testPaperList = testPaperService.selectList(param);
        return BaseModle.getSuccessData(testPaperList,count);
    }

    @RequestMapping(value = "/saveTestPaper", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "保存试卷",notes = "创建试卷",httpMethod = "POST")
    public BaseModle saveTestPaper(HttpServletRequest HttpServletRequest,@Valid @RequestBody TestPaperCreateModel testPaperCreateModel)throws Exception{
        //1.检验参数
        int userId = SupperTokenUtils.getUserByToken(HttpServletRequest);
        if(userId==-1){
            logger.info("无效的用户，请注册或登录");
            return BaseModle.getFailData(StatusCodeEnum.NO_AUTHORITY.getCode(),"没有登录");
        }
        //2.VO  BO转换
        TestPaper testPaper = MapAndObjectUtils.ObjectClone(testPaperCreateModel, TestPaper.class);
        String startTime = testPaperCreateModel.getStartTimeStr();
        Date startDate = DateUtils.string2Date(startTime, "yyyy-MM-dd");
        String endTime = testPaperCreateModel.getEndTimeStr();
        Date endDate = DateUtils.string2Date(endTime, "yyyy-MM-dd");
        testPaper.setStartTime(startDate);
        testPaper.setEndTime(endDate);
        testPaper.setCreateTime(new Date());
        testPaper.setCreateUser(userId);
        int count=0;
        //3.保存
        if(testPaper.getTestPaperId()==null) {
            count= testPaperService.insertSelective(testPaper);
        }else {
            count=testPaperService.updateByPrimaryKeySelective(testPaper);
        }
        return count>0?BaseModle.getSuccessData(testPaper.getTestPaperId()):BaseModle.getFailData(StatusCodeEnum.BUSINESS_ERROR.getCode(),"创建失败");
    }

    @RequestMapping(value = "/selectById", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据主键查询试卷",notes = "根据主键查询试卷",httpMethod = "GET")
    public BaseModle selectById(@RequestParam int testPagerId)throws Exception{
        TestPaper testPaper = testPaperService.selectByPrimaryKey(testPagerId);
        TestPaperCreateModel testPaperCreateModel = MapAndObjectUtils.ObjectClone(testPaper, TestPaperCreateModel.class);
        testPaperCreateModel.setStartTimeStr(DateUtils.date2String(testPaper.getStartTime(),"yyyy-MM-dd"));
        testPaperCreateModel.setEndTimeStr(DateUtils.date2String(testPaper.getEndTime(),"yyyy-MM-dd"));
        return BaseModle.getSuccessData(testPaperCreateModel);
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.DELETE ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据主键删除试卷",notes = "根据主键删除试卷",httpMethod = "DELETE")
    public BaseModle deleteById(@RequestParam int testPagerId){
        if(testPagerId>0) {
            Map<String, Object> initMap = MapUtils.initMap("testPaperId", testPagerId);
            long count = testResultService.selectCount(initMap);
            if(count>0){
                logger.info("试卷id为"+testPagerId+"的试卷正在使用中，不能删除");
                return BaseModle.getFailData(StatusCodeEnum.BUSINESS_ERROR.getCode(),"试卷id为"+testPagerId+"的试卷正在使用中，不能删除");
            }
            testPaperService.deleteTestPaperById(testPagerId);
        }
        return BaseModle.getSuccessData();
    }

    @RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "批量删除试卷",notes = "批量删除试卷",httpMethod = "DELETE")
    public BaseModle deleteBatch(@RequestParam String testPagerIds){
        if(StringUtils.isEmpty(testPagerIds)){
            logger.info("删除请求参数不能为空");
            return BaseModle.getFailData(StatusCodeEnum.PARAM_ERROR.getCode(),StatusCodeEnum.PARAM_ERROR.getDescribe());
        }
        return BaseModle.getSuccessData(testPaperService.deleteTestPaperBatch(testPagerIds));
    }

    @RequestMapping(value = "/exporTestPaperById", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "导出试卷",notes = "导出试卷",httpMethod = "GET")
    public BaseModle exporTestPaperById(@RequestParam int testPagerId, HttpServletResponse response){
        if(testPagerId>0){
            TestPaper testPaper= testPaperService.selectByPrimaryKey(testPagerId);
            String exportPath = testPaper.getExportPath();
            int i = exportPath.lastIndexOf("/");
            String fileName = exportPath.substring(i+1);
            String filePath=exportPath.substring(0,i+1);
            FileUtils.downLoad(response,filePath,fileName);
        }
        return BaseModle.getSuccessData();
    }

}
