package cn.eblcu.questionbank.domain.service.impl;

import cn.eblcu.questionbank.domain.service.ITestPaperFormatService;
import cn.eblcu.questionbank.domain.service.ITestPaperQuestionService;
import cn.eblcu.questionbank.infrastructure.util.MapAndObjectUtils;
import cn.eblcu.questionbank.persistence.dao.IBaseDao;
import cn.eblcu.questionbank.persistence.dao.ITestPaperDao;
import cn.eblcu.questionbank.persistence.dao.ITestPaperFormatDao;
import cn.eblcu.questionbank.persistence.entity.dto.TestPaper;
import cn.eblcu.questionbank.persistence.entity.dto.TestPaperFormat;
import cn.eblcu.questionbank.ui.exception.BusinessException;
import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.PaperFormatInfo;
import cn.eblcu.questionbank.ui.model.TestPaperFormatViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("testPaperFormatService")
public class TestPaperFormatServiceImpl extends BaseServiceImpl implements ITestPaperFormatService {
    @Autowired
    private ITestPaperFormatDao testPaperFormatDao;

    @Resource(name="testPaperQuestionService")
    private ITestPaperQuestionService testPaperQuestionService;
    @Autowired
    private ITestPaperDao testPaperDao;

    @Override
    IBaseDao getDao() {
        return this.testPaperFormatDao;
    }

    @Override
    @Transactional(rollbackFor=BusinessException.class)
    public BaseModle syncPaperFormatInfo(TestPaperFormatViewModel testPaperFormatViewModel) throws Exception{
        List<PaperFormatInfo> paperFormatInfoLst = testPaperFormatViewModel.getPaperFormatInfoLst();
        int totalScore=0;
        for (PaperFormatInfo paperFormatInfo : paperFormatInfoLst) {
            TestPaperFormat testPaperFormat = MapAndObjectUtils.ObjectClone(paperFormatInfo, TestPaperFormat.class);
            testPaperFormat.setTestPaperId(testPaperFormatViewModel.getTestPaperId());
            totalScore+=testPaperFormat.getQuestionSpec().intValue()*testPaperFormat.getQuestionNum();
            if(null==paperFormatInfo.getTestPaperFormatId())
                //插入
                getDao().insertSelective(testPaperFormat);
            else
                //更新
                getDao().updateByPrimaryKeySelective(testPaperFormat);
        }
        TestPaper testPaper = new TestPaper();
        testPaper.setTestPaperId(testPaperFormatViewModel.getTestPaperId());
        testPaper.setTotalScore(totalScore);
        testPaperDao.updateByPrimaryKeySelective(testPaper);
        testPaper = testPaperDao.selectByPrimaryKey(testPaperFormatViewModel.getTestPaperId());
        if(testPaper.getFormType()==1){
            //智能组卷
            BaseModle baseModle = testPaperQuestionService.intellectPaper(testPaperFormatViewModel.getTestPaperId(), testPaperFormatViewModel.getKonwledges());
            if(!baseModle.isSuccess()){
                //抛出运行时异常，使事务回滚
                throw new BusinessException(baseModle.getCode(),baseModle.getDescribe());
            }else{
                return baseModle;
            }
        }
        return BaseModle.getSuccessData();
    }
}