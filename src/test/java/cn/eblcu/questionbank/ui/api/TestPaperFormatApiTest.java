package cn.eblcu.questionbank.ui.api;


import cn.eblcu.questionbank.ui.model.PaperFormatInfo;
import cn.eblcu.questionbank.ui.model.TestPaperFormatViewModel;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestPaperFormatApiTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种
    }

    @Test
    public void testQueryTestPaperFormat()throws Exception{
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/testPaperFormat/queryTestPaperFormat")
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

    @Test
    public void testSaveTestPaperFormat()throws  Exception{
        TestPaperFormatViewModel testPaperFormatViewModel=new TestPaperFormatViewModel();
        testPaperFormatViewModel.setTestPaperId(5);
        testPaperFormatViewModel.setKonwledges("1983;1081");
        List<PaperFormatInfo> list=new ArrayList<>();
        PaperFormatInfo temp=new PaperFormatInfo();
        temp.setQuestionType(1);
        temp.setQuestionNum(2);
        temp.setQuestionSpec(10);

        PaperFormatInfo temp1=new PaperFormatInfo();
        temp1.setQuestionType(2);
        temp1.setQuestionNum(1);
        temp1.setQuestionSpec(20);
        PaperFormatInfo temp2=new PaperFormatInfo();
        temp2.setQuestionType(4);
        temp2.setQuestionNum(2);
        temp2.setQuestionSpec(15);
        PaperFormatInfo temp3=new PaperFormatInfo();
        temp3.setQuestionType(9);
        temp3.setQuestionNum(4);
        temp3.setQuestionSpec(5);
        PaperFormatInfo temp4=new PaperFormatInfo();
        temp4.setQuestionType(3);
        temp4.setQuestionNum(1);
        temp4.setQuestionSpec(5);
        PaperFormatInfo temp5=new PaperFormatInfo();
        temp5.setQuestionType(5);
        temp5.setQuestionNum(1);
        temp5.setQuestionSpec(9);
        PaperFormatInfo temp6=new PaperFormatInfo();
        temp6.setQuestionType(7);
        temp6.setQuestionNum(1);
        temp6.setQuestionSpec(15);
        PaperFormatInfo temp7=new PaperFormatInfo();
        temp7.setQuestionType(6);
        temp7.setQuestionNum(1);
        temp7.setQuestionSpec(15);
        PaperFormatInfo temp8=new PaperFormatInfo();
        temp8.setQuestionType(8);
        temp8.setQuestionNum(1);
        temp8.setQuestionSpec(10);
        list.add(temp);
        list.add(temp1);
        list.add(temp2);
        list.add(temp3);
        list.add(temp4);
        list.add(temp5);
        list.add(temp6);
        list.add(temp7);
        list.add(temp8);
        testPaperFormatViewModel.setPaperFormatInfoLst(list);
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.post("/testPaperFormat/saveTestPaperFormat")
                .header("token","AItgJBAEsTI6FdGtTh266zZeHJ9FYGhc86s6Bsli0LXPdTWEIfmBeuzNdgOdiB+pxsRy0t5dUO/MkPG1ZcWlMwr+0txkG/+lW85dQEsgDch/LydR7NZxRXkNgcVNiuvNR6UVpODqtymcssBWfss+olMVqMuVwgcBsIy33BKXomE6zXFQ+ToaV9b4HL3xKWnEmIaiskU2knWev/StJF5W0vxf1a3AcNry0hDWkdRUH7wdgkRTBDLn+eoUslWDnjBEmABbNO6AZFxoONcfN7IY3/HhSIBswITMEZICNObivsOca9usv/eZoIvUdYxolxnRllY/Tdgg4MfWALsPm1L3yk/ALsluSujr4Yj90aqdSjgFVUqt0HIW2nIXBN9NWakj//ObQycTQztxpHwcNj8iyuCCmqSXaR6JIn1QU2Q81LIzyres/CO6H18Qm0qpkoWK42FG9HOolzu8qUWQuaYzEr4cmQA0ajv9WJlkfzWAvWDxYqDvlGyxORGnMGhXAItO")
                .content(JSONObject.toJSONString(testPaperFormatViewModel))
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
