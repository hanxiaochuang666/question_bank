package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.domain.service.IQuestionService;
import cn.eblcu.questionbank.infrastructure.util.SupperTokenUtils;
import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.QuestionModel;
import cn.eblcu.questionbank.ui.model.StatusCodeEnum;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/question")
@Slf4j
public class QuestionApi {

    @Autowired
    private IQuestionService questionService;

    @RequestMapping(value = "/insertQuestion",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "试题录入接口")
    public BaseModle insertQuestion(HttpServletRequest httpServletRequest,@RequestBody QuestionModel questionModel) throws Exception{

        int userId = SupperTokenUtils.getUserByToken(httpServletRequest);
        if(userId==-1){
            log.info("无效的用户，请注册或登录");
//            return BaseModle.getFailData(StatusCodeEnum.NO_AUTHORITY.getCode(),"没有登录");
        }
        questionService.insertQuestion(questionModel,httpServletRequest);
        return BaseModle.getSuccessData();
    };
}
