package cn.eblcu.questionbank.ui.api;

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

/**
 * @ClassName TestPaperQuestionApiTest
 * @Author 焦冬冬
 * @Date 2019/6/20 15:44
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestPaperQuestionApiTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种
    }

    @Test
    public void testList() throws Exception{
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/testPaperQuestion/list")
                .header("token","AItgJBAEsTI6FdGtTh266zZeHJ9FYGhc86s6Bsli0LXPdTWEIfmBeuzNdgOdiB+pxsRy0t5dUO/MkPG1ZcWlMwr+0txkG/+lW85dQEsgDch/LydR7NZxRXkNgcVNiuvNR6UVpODqtymcssBWfss+olMVqMuVwgcBsIy33BKXomE6zXFQ+ToaV9b4HL3xKWnEmIaiskU2knWev/StJF5W0vxf1a3AcNry0hDWkdRUH7wdgkRTBDLn+eoUslWDnjBEmABbNO6AZFxoONcfN7IY3/HhSIBswITMEZICNObivsOca9usv/eZoIvUdYxolxnRllY/Tdgg4MfWALsPm1L3yk/ALsluSujr4Yj90aqdSjgFVUqt0HIW2nIXBN9NWakj//ObQycTQztxpHwcNj8iyuCCmqSXaR6JIn1QU2Q81LIzyres/CO6H18Qm0qpkoWK42FG9HOolzu8qUWQuaYzEr4cmQA0ajv9WJlkfzWAvWDxYqDvlGyxORGnMGhXAItO")
                .param("testPaperId","1")
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
