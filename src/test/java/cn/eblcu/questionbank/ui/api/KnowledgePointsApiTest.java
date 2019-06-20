/*
package cn.eblcu.questionbank.ui.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class KnowledgePointsApiTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种
    }

    @Test
    public void importExcel() throws Exception{

        File file = new File("D:\\SVNworkspace\\QuestionBank\\课程结构导入模板.xlsx");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "课程结构导入模板.xlsx", MediaType.TEXT_PLAIN_VALUE,
                new BufferedInputStream(new FileInputStream(file)));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/knowledge/importExcel")
                .file(mockMultipartFile)
                .param("categoryOne","11")
                .param("categoryTwo","12")
                .param("courseId","123")
                .param("courseName","Java程序设计")
                .param("orgId","1111111111")
//                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());

    }

    @Test
    public void getKnowledgePoints() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/knowledge/getKnowledgePoints")
                .param("courseId", "123")
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
    public void editKnowledgePoints() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/knowledge/editKnowledgePoints")
                .param("pointDetailId", "744")
                .param("name", "测试修改章节")
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
    public void addKnowledgePoints() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/knowledge/addKnowledgePoints")
                .param("courseId", "123")
                .param("parentId", "0")
                .param("name", "测试增加章节")
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
    public void deleteKnowledgePoints() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/knowledge/deleteKnowledgePoints")
                .param("pointDetailId", "742")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(200, status);
        System.out.println("返回参数:" + content);
    }
}*/
