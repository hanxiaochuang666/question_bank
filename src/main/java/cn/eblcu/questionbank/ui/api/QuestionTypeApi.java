package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.client.CheckToken;
import cn.eblcu.questionbank.domain.service.IQuestionTypeService;
import cn.eblcu.questionbank.persistence.entity.dto.QuestionType;
import cn.eblcu.questionbank.ui.model.BaseModle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CheckToken
@RestController
@RequestMapping(value = "/questionType")
@Slf4j
@Api(tags = "试题类型管理API")
public class QuestionTypeApi {

    @Autowired
    private IQuestionTypeService typeService;

    @RequestMapping(value = "/addQuestionType",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "新增试题类型")
    public BaseModle addQuestionType(@RequestBody QuestionType model, HttpServletRequest request){

        typeService.insertSelective(model);
        return BaseModle.getSuccessData();
    }

    @RequestMapping(value = "/deleteQuestionType",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "删除试题类型")
    public BaseModle deleteQuestionType(@RequestParam Integer id, HttpServletRequest request){

        QuestionType type = new QuestionType();
        type.setQuestionTypeId(id);
        type.setStatus((byte) 1);
        typeService.updateByPrimaryKeySelective(type);
        return BaseModle.getSuccessData();
    }

    @RequestMapping(value = "/updateQuestionType",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "修改试题类型")
    public BaseModle updateQuestionType(@RequestBody QuestionType model, HttpServletRequest request){

        typeService.updateByPrimaryKeySelective(model);
        return BaseModle.getSuccessData();
    }

    @RequestMapping(value = "/getQuestionTypeList",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "查询试题类型列表")
    public BaseModle getQuestionTypeList(@RequestParam Integer status, HttpServletRequest request){

        Map<String,Object> map = new HashMap<>();
        if(-1 != status){
            map.put("status",status);
        }
        return BaseModle.getSuccessData(typeService.selectList(map));
    }
}
