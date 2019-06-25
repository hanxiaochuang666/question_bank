package cn.eblcu.questionbank;

import cn.eblcu.questionbank.infrastructure.factory.XWPFFactory.XwptFactory;
import cn.eblcu.questionbank.infrastructure.factory.XWPFFactory.impl.*;
import cn.eblcu.questionbank.infrastructure.factory.XWPFFactory.model.DocModel;
import cn.eblcu.questionbank.infrastructure.factory.XWPFFactory.model.DocQueModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPaperFactoryTests {
    @Test
    public void test1(){
        XwptFactory xwptFactory=new XwptFactory();
        xwptFactory.setDocName("测试.docx");
        DocModel docModel = new DocModel();
        docModel.setQuestionTypeName("测试标题1212");
        docModel.setTotalScore(120);

        DocModel docModel1=new DocModel();

        docModel1.setQuestionTypeName("单选题");
        docModel1.setTotalScore(30);
        docModel1.setSort(1);
        List<DocQueModel> questionLst1=new ArrayList<>();
        DocQueModel docQueModel=new DocQueModel();
        docQueModel.setNumber(1);
        docQueModel.setQuestionBody("康熙皇帝有几个老婆？");
        docQueModel.setQuestionOpt("[{'option':'1'},{'option':'3'},{'option':'10'},{'option':'N'}]");
        docQueModel.setQuestionAnswer("N");
        docQueModel.setScore(5);
        docQueModel.setQuestionReslove("历史未解之谜");
        questionLst1.add(docQueModel);
        questionLst1.add(docQueModel);
        questionLst1.add(docQueModel);
        questionLst1.add(docQueModel);
        questionLst1.add(docQueModel);
        questionLst1.add(docQueModel);
        docModel1.setQuestionLst(questionLst1);

        DocModel docModel2=new DocModel();
        docModel2.setQuestionTypeName("多选题");
        docModel2.setTotalScore(30);
        docModel2.setSort(2);
        List<DocQueModel> questionLst2=new ArrayList<>();
        DocQueModel docQueModel2=new DocQueModel();
        docQueModel2.setNumber(1);
        docQueModel2.setQuestionBody("下列中属于山西省的城市是");
        docQueModel2.setQuestionOpt("[{'option':'北京'},{'option':'太原'},{'option':'运城'},{'option':'清远'}]");
        docQueModel2.setQuestionAnswer("太原#&&&#运城");
        docQueModel2.setScore(5);
        docQueModel2.setQuestionReslove("自行百度");
        questionLst2.add(docQueModel2);
        questionLst2.add(docQueModel2);
        questionLst2.add(docQueModel2);
        questionLst2.add(docQueModel2);
        questionLst2.add(docQueModel2);
        questionLst2.add(docQueModel2);
        docModel2.setQuestionLst(questionLst2);

        DocModel docModel3=new DocModel();
        docModel3.setQuestionTypeName("判断题");
        docModel3.setTotalScore(30);
        docModel3.setSort(3);
        List<DocQueModel> questionLst3=new ArrayList<>();
        DocQueModel docQueModel3=new DocQueModel();
        docQueModel3.setNumber(1);
        docQueModel3.setQuestionBody("任重是否道远？");
        docQueModel3.setQuestionOpt("[{'option':'是'},{'option':'不是'}]");
        docQueModel3.setQuestionAnswer("是");
        docQueModel3.setScore(5);
        docQueModel3.setQuestionReslove("判断解读");
        questionLst3.add(docQueModel3);
        questionLst3.add(docQueModel3);
        questionLst3.add(docQueModel3);
        questionLst3.add(docQueModel3);
        questionLst3.add(docQueModel3);
        questionLst3.add(docQueModel3);
        docModel3.setQuestionLst(questionLst3);

        DocModel docModel4=new DocModel();
        docModel4.setQuestionTypeName("填空题");
        docModel4.setTotalScore(10);
        docModel4.setSort(4);
        List<DocQueModel> questionLst4=new ArrayList<>();
        DocQueModel docQueModel4=new DocQueModel();
        docQueModel4.setNumber(1);
        docQueModel4.setQuestionBody("光的速度是（）千米/秒");
        docQueModel4.setQuestionAnswer("3.8*10^8");
        docQueModel4.setScore(5);
        docQueModel4.setQuestionReslove("填空题解析");
        questionLst4.add(docQueModel4);
        questionLst4.add(docQueModel4);
        docModel4.setQuestionLst(questionLst4);

        DocModel docModel5=new DocModel();
        docModel5.setQuestionTypeName("综合题");
        docModel5.setTotalScore(30);
        docModel5.setSort(5);
        docModel5.setSynthesisStr("综合题题干综合题题干综合题题干综合题题干综合题题干综合题题干综合题题干综合题题干综合题题干综合题题干综合题题干综合题题干综合题题干综合题题干综合题题干综合题题干综合题题干综合题题干综合题题干综合题题干综合题题干");
        List<DocQueModel> questionLst5=new ArrayList<>();
        DocQueModel docQueModel5=new DocQueModel();
        docQueModel5.setNumber(1);
        docQueModel5.setQuestionBody("下列中属于山西省的城市是");
        docQueModel5.setQuestionOpt("[{'option':'北京'},{'option':'太原'},{'option':'运城'},{'option':'清远'}]");
        docQueModel5.setQuestionAnswer("太原#&&&#运城");
        docQueModel5.setScore(5);
        docQueModel5.setQuestionReslove("自行百度");
        questionLst5.add(docQueModel5);
        questionLst5.add(docQueModel5);
        questionLst5.add(docQueModel5);
        docModel5.setQuestionLst(questionLst5);

        DocModel docModel6=new DocModel();
        docModel6.setQuestionTypeName("配对题");
        docModel6.setTotalScore(15);
        docModel6.setSort(6);
        docModel6.setSynthesisStr("配对题题干配对题题干配对题题干配对题题干配对题题干配对题题干配对题题干配对题题干配对题题干");
        docModel6.setMatchOptStr("[{'option':' A:I’ll buy online only when I have to.'},{'option':' B:You can enjoy steep discounts with most online sellers.'},{'option':'C:I shift between buying on or off line depending on what suits me.'},{'option':'D:Shopping is not only a necessary task but a sociable pleasure.'}]");
        List<DocQueModel> questionLst6=new ArrayList<>();
        DocQueModel docQueModel6=new DocQueModel();
        docQueModel6.setNumber(1);
        docQueModel6.setQuestionBody("Jennifer");
        docQueModel6.setQuestionAnswer("D");
        docQueModel6.setScore(5);
        docQueModel6.setQuestionReslove("配对题解析");
        questionLst6.add(docQueModel6);
        questionLst6.add(docQueModel6);
        questionLst6.add(docQueModel6);
        docModel6.setQuestionLst(questionLst6);

        DocModel docModel7=new DocModel();
        docModel7.setQuestionTypeName("选词填空题");
        docModel7.setTotalScore(15);
        docModel7.setSort(7);
        docModel7.setSynthesisStr("选词填空题题干选词填空题题干选词填空题题干选词填空题题干选词填空题题干选词填空题题干选词填空题题干选词填空题题干选词填空题题干选词填空题题干");
        docModel7.setMatchOptStr("[{'option':' A:I’ll buy online only when I have to.'},{'option':' B:You can enjoy steep discounts with most online sellers.'},{'option':'C:I shift between buying on or off line depending on what suits me.'},{'option':'D:Shopping is not only a necessary task but a sociable pleasure.'}]");
        List<DocQueModel> questionLst7=new ArrayList<>();
        DocQueModel docQueModel7=new DocQueModel();
        docQueModel7.setNumber(1);
        docQueModel7.setQuestionAnswer("1.D   2.G   3.B   4.F");
        docQueModel7.setScore(5);
        docQueModel7.setQuestionReslove("选词填空题解析");
        questionLst7.add(docQueModel7);
        docModel7.setQuestionLst(questionLst7);

        //试卷标题测试
        xwptFactory.addDocModel(docModel);
        xwptFactory.addXwptWrite(new HeaderXwptWriteImpl());
        //试卷单选题测试
        xwptFactory.addDocModel(docModel1);
        xwptFactory.addXwptWrite(new SingleChoiceXwptWriteImpl());
        //试卷多选题测试
        xwptFactory.addDocModel(docModel2);
        xwptFactory.addXwptWrite(new CheckBoxXwptWriteImpl());
        //试卷判断题测试
        xwptFactory.addDocModel(docModel3);
        xwptFactory.addXwptWrite(new SingleChoiceXwptWriteImpl());
        //试卷填空题测试
        xwptFactory.addDocModel(docModel4);
        xwptFactory.addXwptWrite(new CompletionXwptWriteImpl());
        //试卷综合题测试
        xwptFactory.addDocModel(docModel5);
        xwptFactory.addXwptWrite(new SynthesisXwptWriteImpl());
        //试卷配对题测试
        xwptFactory.addDocModel(docModel6);
        docModel6.setQuestionTypeName(null);
        xwptFactory.addXwptWrite(new MatchingXwptWriteImpl());
        //试卷选词填空题测试
        xwptFactory.addDocModel(docModel7);
        xwptFactory.addXwptWrite(new CheckWordXwptWriteImpl());

        //是否导出答案，默认导出
        //xwptFactory.setExportAnswer(false);
        //是否导出解析，默认导出
        //xwptFactory.setExportReslove(false);
        xwptFactory.writeObject();
    }

}

