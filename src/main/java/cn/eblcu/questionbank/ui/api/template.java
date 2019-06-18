package cn.eblcu.questionbank.ui.api;

import cn.eblcu.questionbank.infrastructure.util.SupperTokenUtils;
import cn.eblcu.questionbank.ui.model.BaseModle;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/template")
public class template {

    @RequestMapping(value = "/test1", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseModle test1(HttpServletRequest request)throws Exception{
        int userId = SupperTokenUtils.getUserByToken(request);
        if(userId<0){
            System.out.println("无效的用户id");
        }
        XWPFDocument doc = new XWPFDocument();
        System.out.println("userId is "+userId);
        return BaseModle.getSuccessData("ssss");
    }
}
