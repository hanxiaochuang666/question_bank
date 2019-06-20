package cn.eblcu.questionbank.domain.service.impl;

import cn.eblcu.questionbank.domain.service.ITestPaperService;
import cn.eblcu.questionbank.infrastructure.util.MapUtils;
import cn.eblcu.questionbank.persistence.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("testPaperService")
public class TestPaperServiceImpl extends BaseServiceImpl implements ITestPaperService {
    @Autowired
    private ITestPaperDao testPaperDao;

    @Autowired
    private ITestPaperFormatDao testPaperFormatDao;

    @Autowired
    private ITestPaperQuestionDao testPaperQuestionDao;

    @Autowired
    private ITestResultDao testResultDao;
    @Override
    IBaseDao getDao() {
        return this.testPaperDao;
    }

    @Override
    @Transactional
    public void deleteTestPaperById(int id) {
        //1.删除试卷
        getDao().deleteByPrimaryKey(id);
        //2.删除试卷组成
        Map<String, Object> initMap = MapUtils.initMap("testPaperId", id);
        testPaperFormatDao.deleteByParams(initMap);
        //3.删除试卷的题目关联
        testPaperQuestionDao.deleteByParams(initMap);
    }

    @Override
    public int deleteTestPaperBatch(String testPagerIds) {
        boolean contains = testPagerIds.contains(";");
        if(!contains) {
            Map<String, Object> initMap = MapUtils.initMap("testPaperId", Integer.valueOf(testPagerIds));
            long count = testResultDao.selectCount(initMap);
            if (count>0)
                return 0;
            deleteTestPaperById(Integer.valueOf(testPagerIds));
            return 1;
        }
        String[] split = testPagerIds.split(";");
        int count=0;
        for (String s : split) {
            Map<String, Object> initMap = MapUtils.initMap("testPaperId", Integer.valueOf(testPagerIds));
            long count1 = testResultDao.selectCount(initMap);
            if(count1>0)
                continue;
            count++;
            deleteTestPaperById(Integer.valueOf(s));
        }
        return count;
    }
}