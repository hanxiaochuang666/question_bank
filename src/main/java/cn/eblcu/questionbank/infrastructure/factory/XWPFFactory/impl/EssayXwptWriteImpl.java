package cn.eblcu.questionbank.infrastructure.factory.XWPFFactory.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * @ClassName EssayXwptWriteImpl
 * @Author 焦冬冬
 * 问答题
 * @Date 2019/6/17 16:03
 **/
public class EssayXwptWriteImpl extends SingleChoiceXwptWriteImpl{
    @Override
    protected void writeQuestionOpt(XWPFDocument docxDocument, String questionOpt) {

    }

    @Override
    protected void writeQuestionAnswer(XWPFDocument docxDocument, String answer) {
    }
}
