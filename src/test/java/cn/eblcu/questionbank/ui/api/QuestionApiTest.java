package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.ui.model.QuestionModel;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class QuestionApiTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种
    }

    @Test
    public void insertQuestion() throws Exception{

        String danXuan = "{\"categoryOne\":11,\"categoryTwo\":22,\"courseId\":123,\"difficultyLevel\":1,\"knowledgePoints\":\"1081;1082;1983\",\"orgId\":123123,\"questionAnswer\":\"北京\",\"questionBody\":\"我国的首都是下列哪个？\",\"questionOpt\":\"[{'option':'北京'},{'option':'上海'},{'option':'广州'},{'option':'深圳'}]\",\"questionResolve\":\"中国首都是北京，上海是直辖市\",\"questionSound\":\"123we323re223432\",\"questionType\":1}";
        String duoXuan = "{\"categoryOne\":11,\"categoryTwo\":22,\"courseId\":123,\"difficultyLevel\":1,\"knowledgePoints\":\"1081;1983\",\"orgId\":123123,\"questionAnswer\":\"太原#&&&#南京\",\"questionBody\":\"下列哪些不是直辖市？\",\"questionOpt\":\"[{'option':'北京'},{'option':'上海'},{'option':'太原'},{'option':'南京'}]\",\"questionResolve\":\"中国共有4个直辖市：北京市、上海市、天津市、重庆市\",\"questionSound\":\"123we323re223432\",\"questionType\":2}";
        String panDuan = "{\"categoryOne\":11,\"categoryTwo\":22,\"courseId\":123,\"difficultyLevel\":1,\"knowledgePoints\":\"1081;1983\",\"orgId\":123123,\"questionAnswer\":\"不是\",\"questionBody\":\"中国是发达国家吗？\",\"questionOpt\":\"[{'option':'是'},{'option':'不是'}]\",\"questionResolve\":\"中国是最大的发展中国家，还没有步入发达国家的行列\",\"questionSound\":\"3124345455635\",\"questionType\":3}";
        String tianKong = "{\"categoryOne\":11,\"categoryTwo\":22,\"courseId\":123,\"difficultyLevel\":1,\"knowledgePoints\":\"1081;1983\",\"orgId\":123123,\"questionAnswer\":\"北京#&&&#河南省\",\"questionBody\":\"中国的首都是（），中国人口最大的省份是（）？\",\"questionResolve\":\"中国是最大的发展中国家，还没有步入发达国家的行列\",\"questionSound\":\"3124345455635\",\"questionType\":4}";
        System.out.println("入参JSon："+JSONObject.toJSONString(getModel()));
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.post("/question/insertQuestion")
                .header("token","AItgJBAEsTI6FdGtTh266zZeHJ9FYGhc86s6Bsli0LXPdTWEIfmBeuzNdgOdiB+pxsRy0t5dUO/MkPG1ZcWlMwr+0txkG/+lW85dQEsgDch/LydR7NZxRXkNgcVNiuvNR6UVpODqtymcssBWfss+olMVqMuVwgcBsIy33BKXomE6zXFQ+ToaV9b4HL3xKWnEmIaiskU2knWev/StJF5W0vxf1a3AcNry0hDWkdRUH7wdgkRTBDLn+eoUslWDnjBEmABbNO6AZFxoONcfN7IY3/HhSIBswITMEZICNObivsOca9usv/eZoIvUdYxolxnRllY/Tdgg4MfWALsPm1L3yk/ALsluSujr4Yj90aqdSjgFVUqt0HIW2nIXBN9NWakj//ObQycTQztxpHwcNj8iyuCCmqSXaR6JIn1QU2Q81LIzyres/CO6H18Qm0qpkoWK42FG9HOolzu8qUWQuaYzEr4cmQA0ajv9WJlkfzWAvWDxYqDvlGyxORGnMGhXAItO")
                .content(JSONObject.toJSONString(getModel()))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(200, status);
        System.out.println("返回参数:" + content);
    }

    QuestionModel getModel(){
        QuestionModel model = new QuestionModel();
        model.setCategoryOne(11);
        model.setCategoryTwo(22);
        model.setCourseId(123);
        model.setDifficultyLevel(1);
        model.setKnowledgePoints("1081;1983");
        model.setOrgId(123123);
        model.setQuestionBody("中国的首都是（），中国人口最大的省份是（）？");
//        model.setQuestionOpt("[{'option':'是'},{'option':'不是'}]");
        model.setQuestionAnswer("北京#&&&#河南省");
        model.setQuestionResolve("中国是最大的发展中国家，还没有步入发达国家的行列");
        model.setQuestionSound("3124345455635");
        model.setQuestionType(4);
        return model;
    }
}