package cn.eblcu.questionbank.ui.api;


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

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestResultApiTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种
    }
    @Test
    public void testSyncTestPaper()throws Exception{
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.put("/testResult/syncTestPaper")
                .header("token","AItgJBAEsTI6FdGtTh266zZeHJ9FYGhc86s6Bsli0LXPdTWEIfmBeuzNdgOdiB+pxsRy0t5dUO/MkPG1ZcWlMwr+0txkG/+lW85dQEsgDch/LydR7NZxRXkNgcVNiuvNR6UVpODqtymcssBWfss+olMVqMuVwgcBsIy33BKXomE6zXFQ+ToaV9b4HL3xKWnEmIaiskU2knWev/StJF5W0vxf1a3AcNry0hDWkdRUH7wdgkRTBDLn+eoUslWDnjBEmABbNO6AZFxoONcfN7IY3/HhSIBswITMEZICNObivsOca9usv/eZoIvUdYxolxnRllY/Tdgg4MfWALsPm1L3yk/ALsluSujr4Yj90aqdSjgFVUqt0HIW2nIXBN9NWakj//ObQycTQztxpHwcNj8iyuCCmqSXaR6JIn1QU2Q81LIzyres/CO6H18Qm0qpkoWK42FG9HOolzu8qUWQuaYzEr4cmQA0ajv9WJlkfzWAvWDxYqDvlGyxORGnMGhXAItO")
                .param("userId","1")
                .param("courseId","123")
                .param("studentId","3")
                .param("studentName","jdd")
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
    public void testAddTestCallBack()throws Exception{
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.put("/testResult/addTestCallBack")
                .header("token","AItgJBAEsTI6FdGtTh266zZeHJ9FYGhc86s6Bsli0LXPdTWEIfmBeuzNdgOdiB+pxsRy0t5dUO/MkPG1ZcWlMwr+0txkG/+lW85dQEsgDch/LydR7NZxRXkNgcVNiuvNR6UVpODqtymcssBWfss+olMVqMuVwgcBsIy33BKXomE6zXFQ+ToaV9b4HL3xKWnEmIaiskU2knWev/StJF5W0vxf1a3AcNry0hDWkdRUH7wdgkRTBDLn+eoUslWDnjBEmABbNO6AZFxoONcfN7IY3/HhSIBswITMEZICNObivsOca9usv/eZoIvUdYxolxnRllY/Tdgg4MfWALsPm1L3yk/ALsluSujr4Yj90aqdSjgFVUqt0HIW2nIXBN9NWakj//ObQycTQztxpHwcNj8iyuCCmqSXaR6JIn1QU2Q81LIzyres/CO6H18Qm0qpkoWK42FG9HOolzu8qUWQuaYzEr4cmQA0ajv9WJlkfzWAvWDxYqDvlGyxORGnMGhXAItO")
                .param("courseId","1")
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

    @Test
    public void testGetStudentCourse()throws Exception{
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/testResult/getStudentCourse")
                .header("token","AItgJBAEsTI6FdGtTh266zZeHJ9FYGhc86s6Bsli0LXPdTWEIfmBeuzNdgOdiB+pxsRy0t5dUO/MkPG1ZcWlMwr+0txkG/+lW85dQEsgDch/LydR7NZxRXkNgcVNiuvNR6UVpODqtymcssBWfss+olMVqMuVwgcBsIy33BKXomE6zXFQ+ToaV9b4HL3xKWnEmIaiskU2knWev/StJF5W0vxf1a3AcNry0hDWkdRUH7wdgkRTBDLn+eoUslWDnjBEmABbNO6AZFxoONcfN7IY3/HhSIBswITMEZICNObivsOca9usv/eZoIvUdYxolxnRllY/Tdgg4MfWALsPm1L3yk/ALsluSujr4Yj90aqdSjgFVUqt0HIW2nIXBN9NWakj//ObQycTQztxpHwcNj8iyuCCmqSXaR6JIn1QU2Q81LIzyres/CO6H18Qm0qpkoWK42FG9HOolzu8qUWQuaYzEr4cmQA0ajv9WJlkfzWAvWDxYqDvlGyxORGnMGhXAItO")
                .param("useType","0")
                .param("studentId","2")
                //.param("courseId","123")
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
