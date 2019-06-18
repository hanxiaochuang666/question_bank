package cn.eblcu.questionbank.infrastructure.factory.XWPFFactory.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * @ClassName CompletionXwptWriteImpl
 * @Author 焦冬冬
 * 填空题(可复用到计算题)
 * @Date 2019/6/17 15:55
 **/
public class CompletionXwptWriteImpl extends SingleChoiceXwptWriteImpl{

    //填空题没有选项的说法
    @Override
    protected void writeQuestionOpt(XWPFDocument docxDocument, String questionOpt) {

    }
}
