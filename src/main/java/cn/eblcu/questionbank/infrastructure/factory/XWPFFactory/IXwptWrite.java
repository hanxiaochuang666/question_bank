package cn.eblcu.questionbank.infrastructure.factory.XWPFFactory;

import cn.eblcu.questionbank.infrastructure.factory.XWPFFactory.model.DocModel;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * @ClassName IXwptWrite
 * @Author 焦冬冬
 * @Date 2019/6/17 9:57
 **/
public interface IXwptWrite {
    /**
     * 写入整个类型
     */
    void writeObject(XWPFDocument docxDocument,DocModel docModel,boolean isExportAnswer,boolean isExportReslove);

}
