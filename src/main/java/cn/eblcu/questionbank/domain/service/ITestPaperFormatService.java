package cn.eblcu.questionbank.domain.service;

import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.TestPaperFormatViewModel;

public interface ITestPaperFormatService extends IBaseService {

    /**
     * 同步试卷组成
     * @param testPaperFormatViewModel
     */
    BaseModle syncPaperFormatInfo(TestPaperFormatViewModel testPaperFormatViewModel)throws Exception;
}