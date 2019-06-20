package cn.eblcu.questionbank.domain.service;

public interface ITestPaperService extends IBaseService {
    /**
     * 删除试卷及试卷附属内容
     * @param id
     */
    void deleteTestPaperById(int id);

    /**
     * 批量删除试卷
     * @param testPagerIds
     */
    int deleteTestPaperBatch(String testPagerIds);
}