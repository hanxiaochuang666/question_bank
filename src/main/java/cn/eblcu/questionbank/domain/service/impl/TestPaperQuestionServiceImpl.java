package cn.eblcu.questionbank.domain.service.impl;

import cn.eblcu.questionbank.domain.service.ITestPaperQuestionService;
import cn.eblcu.questionbank.infrastructure.util.MapAndObjectUtils;
import cn.eblcu.questionbank.infrastructure.util.MapUtils;
import cn.eblcu.questionbank.persistence.dao.*;
import cn.eblcu.questionbank.persistence.entity.dto.*;
import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.QuestionTypeCountModel;
import cn.eblcu.questionbank.ui.model.StatusCodeEnum;
import cn.eblcu.questionbank.ui.model.TestPaperQuestionResModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("testPaperQuestionService")
public class TestPaperQuestionServiceImpl extends BaseServiceImpl implements ITestPaperQuestionService {
    private Logger logger=LoggerFactory.getLogger(TestPaperQuestionServiceImpl.class);
    @Autowired
    private ITestPaperQuestionDao testPaperQuestionDao;

    @Autowired
    private IQuestionDao questionDao;

    @Autowired
    private IQuestionTypeDao questionTypeDao;

    @Autowired
    private ITestPaperFormatDao testPaperFormatDao;

    @Autowired
    private ITestPaperDao testPaperDao;
    @Override
    IBaseDao getDao() {
        return this.testPaperQuestionDao;
    }

    @Override
    public List<TestPaperQuestionResModel> queryTestPaper(int testPaperId) throws Exception{
        Map<String, Object> initMap = MapUtils.initMap("testPaperId", testPaperId);
        initMap.put("_sort_line","sort");
        initMap.put("_order_","asc");
        List<TestPaperQuestionResModel> res=new ArrayList<>();
        List<TestPaperQuestion> objects = getDao().selectList(initMap);
        List<Map<String,Object>> objLst=new ArrayList<>();
        int lastQuestionType=-1;
        int perScore=-1;
        //1.试题信息统一处理
        for (TestPaperQuestion object : objects) {
            Map<String, Object> stringObjectMap = MapAndObjectUtils.ObjectToMap(object);
            Question que = questionDao.selectByPrimaryKey(object.getQuestionId());
            if(que!=null) {
                stringObjectMap.put("questionBody", que.getQuestionBody());
                stringObjectMap.put("questionSound", que.getQuestionSound());
                stringObjectMap.put("questionOpt", que.getQuestionOpt());
                stringObjectMap.put("questionAnswer", que.getQuestionAnswer());
                stringObjectMap.put("questionResolve", que.getQuestionResolve());
                stringObjectMap.put("questionType", que.getQuestionType());
                if (lastQuestionType != que.getQuestionType()) {
                    lastQuestionType = que.getQuestionType();
                    QuestionType questionType = questionTypeDao.selectByPrimaryKey(lastQuestionType);
                    Map<String, Object> map = MapUtils.initMap("testPaperId", testPaperId);
                    map.put("questionType", questionType.getQuestionTypeId());
                    List<TestPaperFormat> objects1 = testPaperFormatDao.selectList(map);
                    if (objects1 == null || objects1.size() <= 0)
                        continue;
                    TestPaperFormat testPaperFormat = objects1.get(0);
                    int questionNum = testPaperFormat.getQuestionNum().intValue();
                    int score = testPaperFormat.getQuestionSpec();
                    perScore = score;
                    TestPaperQuestionResModel testPaperQuestionResModel = new TestPaperQuestionResModel();
                    testPaperQuestionResModel.setScore(score * questionNum);
                    testPaperQuestionResModel.setQuestionType(lastQuestionType);
                    testPaperQuestionResModel.setQuestionTypeName(questionType.getName());
                    res.add(testPaperQuestionResModel);
                }
                stringObjectMap.put("score", perScore);
                objLst.add(stringObjectMap);
            }
        }
        //2.试题信息分组打包
        for (TestPaperQuestionResModel re : res) {
            List<Map<String, Object>> lst=new ArrayList<>();
            for (Map<String, Object> map : objLst) {
                int questionType = (int)map.get("questionType");
                if(questionType==re.getQuestionType()){
                    lst.add(map);
                }
            }
            re.setQuestionLst(lst);
        }
        return res;
    }

    @Override
    @Transactional
    public BaseModle saveTestPaperQuestion(List<TestPaperQuestion> testPaperQuestionLst) {
        //1.检测试卷组成是否符合规范
        List<Integer> idLst=new ArrayList<>();
        int testPaperId=-1;
        for (TestPaperQuestion testPaperQuestion : testPaperQuestionLst) {
            idLst.add(testPaperQuestion.getQuestionId());
            if(null!=testPaperQuestion.getTestPagerId()&&testPaperId<0)
                testPaperId=testPaperQuestion.getTestPagerId().intValue();
        }
        List<QuestionTypeCountModel> questionTypeCountModels = questionDao.queryQuestionTypeCount(idLst);
        Map<String, Object> initMap = MapUtils.initMap("testPaperId", testPaperId);
        List<TestPaperFormat> list = testPaperFormatDao.selectList(initMap);
        int commonNum=0;
        for (TestPaperFormat format : list) {
            boolean isExistType=false;
            for (QuestionTypeCountModel questionTypeCountModel : questionTypeCountModels) {
                if(format.getQuestionType().intValue()==questionTypeCountModel.getQuestionType()){
                    isExistType=true;
                    commonNum++;
                    if(format.getQuestionNum()!=questionTypeCountModel.getCountNum()) {
                        QuestionType questionType = questionTypeDao.selectByPrimaryKey(questionTypeCountModel.getQuestionType());
                        logger.info(questionType.getName()+"数量与试卷设置数目不匹配");
                        return BaseModle.getFailData(StatusCodeEnum.BUSINESS_ERROR.getCode(),questionType.getName()+"数量与试卷设置数目不匹配");
                    }

                }
            }
            if(!isExistType){
                QuestionType questionType = questionTypeDao.selectByPrimaryKey(format.getQuestionType());
                logger.info(questionType.getName()+"数量与试卷设置数目不匹配");
                return BaseModle.getFailData(StatusCodeEnum.BUSINESS_ERROR.getCode(),questionType.getName()+"数量与试卷设置数目不匹配");
            }
        }
        if(commonNum!=list.size()||commonNum!=questionTypeCountModels.size()){
            logger.info("试题数量与试卷设置数目不匹配");
            return BaseModle.getFailData(StatusCodeEnum.BUSINESS_ERROR.getCode(),"试题数量与试卷设置数目不匹配");
        }
        //2.删除原来的试题
        testPaperQuestionDao.deleteByParams(initMap);
        //3.插入新的试题
        for (TestPaperQuestion testPaperQuestion : testPaperQuestionLst) {
            testPaperQuestion.setTestPaperQuestionId(null);
            testPaperQuestionDao.insertSelective(testPaperQuestion);

        }
        logger.info("试卷id为"+testPaperId+"的试题保存成功");
        return BaseModle.getSuccessData();
    }

    @Override
    public void intellectPaper(int testPaperId,String knowledges) {
        TestPaper testPaper = testPaperDao.selectByPrimaryKey(testPaperId);
        //1.检测创建者id是否有使用本机构下其他试题的权限(默认没有)

        //2.查询试卷组成;

        //3.查询自己可用的试题（根据知识点和用户id）


    }
}