package cn.eblcu.questionbank.domain.service.impl;

import cn.eblcu.questionbank.domain.service.ITestPaperFormatService;
import cn.eblcu.questionbank.infrastructure.util.MapAndObjectUtils;
import cn.eblcu.questionbank.persistence.dao.IBaseDao;
import cn.eblcu.questionbank.persistence.dao.ITestPaperDao;
import cn.eblcu.questionbank.persistence.dao.ITestPaperFormatDao;
import cn.eblcu.questionbank.persistence.entity.dto.TestPaper;
import cn.eblcu.questionbank.persistence.entity.dto.TestPaperFormat;
import cn.eblcu.questionbank.ui.model.PaperFormatInfo;
import cn.eblcu.questionbank.ui.model.TestPaperFormatViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("testPaperFormatService")
public class TestPaperFormatServiceImpl extends BaseServiceImpl implements ITestPaperFormatService {
    @Autowired
    private ITestPaperFormatDao testPaperFormatDao;

    @Autowired
    private ITestPaperDao testPaperDao;

    @Override
    IBaseDao getDao() {
        return this.testPaperFormatDao;
    }

    @Override
    @Transactional
    public void syncPaperFormatInfo(TestPaperFormatViewModel testPaperFormatViewModel) {
        List<PaperFormatInfo> paperFormatInfoLst = testPaperFormatViewModel.getPaperFormatInfoLst();
        int totalScore=0;
        for (PaperFormatInfo paperFormatInfo : paperFormatInfoLst) {
            TestPaperFormat testPaperFormat = MapAndObjectUtils.ObjectClone(paperFormatInfo, TestPaperFormat.class);
            testPaperFormat.setTestPaperId(testPaperFormatViewModel.getTestPaperId());
            totalScore+=testPaperFormat.getQuestionSpec().intValue();
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
        }
    }
}