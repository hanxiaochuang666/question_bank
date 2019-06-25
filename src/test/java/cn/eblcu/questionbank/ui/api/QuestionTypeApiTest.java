package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.persistence.entity.dto.QuestionType;
import cn.eblcu.questionbank.ui.model.QuestionModel;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class QuestionTypeApiTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种
    }


    @Test
    public void addQuestionType() throws Exception {
        QuestionType type = new QuestionType();
        type.setCode("test");
        type.setStatus((byte) 0);
        type.setIsObjective((byte) 1);
        type.setName("测试新增题型");
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.post("/questionType/addQuestionType")
                .header("token","AItgJBAEsTI6FdGtTh266zZeHJ9FYGhc86s6Bsli0LXPdTWEIfmBeuzNdgOdiB+pxsRy0t5dUO/MkPG1ZcWlMwr+0txkG/+lW85dQEsgDch/LydR7NZxRXkNgcVNiuvNR6UVpODqtymcssBWfss+olMVqMuVwgcBsIy33BKXomE6zXFQ+ToaV9b4HL3xKWnEmIaiskU2knWev/StJF5W0vxf1a3AcNry0hDWkdRUH7wdgkRTBDLn+eoUslWDnjBEmABbNO6AZFxoONcfN7IY3/HhSIBswITMEZICNObivsOca9usv/eZoIvUdYxolxnRllY/Tdgg4MfWALsPm1L3yk/ALsluSujr4Yj90aqdSjgFVUqt0HIW2nIXBN9NWakj//ObQycTQztxpHwcNj8iyuCCmqSXaR6JIn1QU2Q81LIzyres/CO6H18Qm0qpkoWK42FG9HOolzu8qUWQuaYzEr4cmQA0ajv9WJlkfzWAvWDxYqDvlGyxORGnMGhXAItO")
                .content(JSONObject.toJSONString(type))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(200, status);
        System.out.println("返回参数:" + content);

    }

    @Test
    public void deleteQuestionType() throws Exception {
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.delete("/questionType/deleteQuestionType")
                .header("token","AItgJBAEsTI6FdGtTh266zZeHJ9FYGhc86s6Bsli0LXPdTWEIfmBeuzNdgOdiB+pxsRy0t5dUO/MkPG1ZcWlMwr+0txkG/+lW85dQEsgDch/LydR7NZxRXkNgcVNiuvNR6UVpODqtymcssBWfss+olMVqMuVwgcBsIy33BKXomE6zXFQ+ToaV9b4HL3xKWnEmIaiskU2knWev/StJF5W0vxf1a3AcNry0hDWkdRUH7wdgkRTBDLn+eoUslWDnjBEmABbNO6AZFxoONcfN7IY3/HhSIBswITMEZICNObivsOca9usv/eZoIvUdYxolxnRllY/Tdgg4MfWALsPm1L3yk/ALsluSujr4Yj90aqdSjgFVUqt0HIW2nIXBN9NWakj//ObQycTQztxpHwcNj8iyuCCmqSXaR6JIn1QU2Q81LIzyres/CO6H18Qm0qpkoWK42FG9HOolzu8qUWQuaYzEr4cmQA0ajv9WJlkfzWAvWDxYqDvlGyxORGnMGhXAItO")
                .param("id","10")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(200, status);
        System.out.println("返回参数:" + content);

    }

    @Test
    public void updateQuestionType() throws Exception {
        QuestionType type = new QuestionType();
        type.setQuestionTypeId(10);
        type.setCode("test");
        type.setStatus((byte) 0);
        type.setIsObjective((byte) 1);
        type.setName("测试xiugai题型");
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.post("/questionType/updateQuestionType")
                .header("token","AItgJBAEsTI6FdGtTh266zZeHJ9FYGhc86s6Bsli0LXPdTWEIfmBeuzNdgOdiB+pxsRy0t5dUO/MkPG1ZcWlMwr+0txkG/+lW85dQEsgDch/LydR7NZxRXkNgcVNiuvNR6UVpODqtymcssBWfss+olMVqMuVwgcBsIy33BKXomE6zXFQ+ToaV9b4HL3xKWnEmIaiskU2knWev/StJF5W0vxf1a3AcNry0hDWkdRUH7wdgkRTBDLn+eoUslWDnjBEmABbNO6AZFxoONcfN7IY3/HhSIBswITMEZICNObivsOca9usv/eZoIvUdYxolxnRllY/Tdgg4MfWALsPm1L3yk/ALsluSujr4Yj90aqdSjgFVUqt0HIW2nIXBN9NWakj//ObQycTQztxpHwcNj8iyuCCmqSXaR6JIn1QU2Q81LIzyres/CO6H18Qm0qpkoWK42FG9HOolzu8qUWQuaYzEr4cmQA0ajv9WJlkfzWAvWDxYqDvlGyxORGnMGhXAItO")
                .content(JSONObject.toJSONString(type))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(200, status);
        System.out.println("返回参数:" + content);

    }

    @Test
    public void getQuestionTypeList() throws Exception {
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/questionType/getQuestionTypeList")
                .header("token","AItgJBAEsTI6FdGtTh266zZeHJ9FYGhc86s6Bsli0LXPdTWEIfmBeuzNdgOdiB+pxsRy0t5dUO/MkPG1ZcWlMwr+0txkG/+lW85dQEsgDch/LydR7NZxRXkNgcVNiuvNR6UVpODqtymcssBWfss+olMVqMuVwgcBsIy33BKXomE6zXFQ+ToaV9b4HL3xKWnEmIaiskU2knWev/StJF5W0vxf1a3AcNry0hDWkdRUH7wdgkRTBDLn+eoUslWDnjBEmABbNO6AZFxoONcfN7IY3/HhSIBswITMEZICNObivsOca9usv/eZoIvUdYxolxnRllY/Tdgg4MfWALsPm1L3yk/ALsluSujr4Yj90aqdSjgFVUqt0HIW2nIXBN9NWakj//ObQycTQztxpHwcNj8iyuCCmqSXaR6JIn1QU2Q81LIzyres/CO6H18Qm0qpkoWK42FG9HOolzu8qUWQuaYzEr4cmQA0ajv9WJlkfzWAvWDxYqDvlGyxORGnMGhXAItO")
                .param("status","0")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(200, status);
        System.out.println("返回参数:" + content);

    }
}