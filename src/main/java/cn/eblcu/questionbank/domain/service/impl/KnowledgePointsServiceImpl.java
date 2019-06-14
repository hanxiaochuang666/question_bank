package cn.eblcu.questionbank.domain.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.eblcu.questionbank.domain.service.KnowledgePointsService;
import cn.eblcu.questionbank.persistence.dao.KnowledgePointsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import cn.eblcu.questionbank.ui.exception.BusinessException;
import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.KnowledgePointsModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class KnowledgePointsServiceImpl implements KnowledgePointsService {

    @Value("${excel.importFields}")
    private String importFields;

    @Autowired
    private KnowledgePointsMapper pointsMapper;


    @Override
    public BaseModle importKnowledgePoints(MultipartFile file) throws BusinessException {

        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);// 表头
        importParams.setTitleRows(0);
        // 验证字段
        importParams.setNeedVerfiy(true);
        // 验证标题是否正确
        importParams.setImportFields(importFields.split(","));
        try {
            // 使用原生的，可以返回成功条数，失败条数
            ExcelImportResult<KnowledgePointsModel> result = ExcelImportUtil.importExcelMore(file.getInputStream(), KnowledgePointsModel.class,
                    importParams);
            List<KnowledgePointsModel> successList = result.getList();
            List<KnowledgePointsModel> failList = result.getFailList();
            if(!StringUtils.isEmpty(failList) && failList.size() > 0){
                throw new BusinessException("9998","上传失败："+failList.size()+"条，请检查excel内容是否规范！");
            }
            insertPoints(successList);
            // 使用工具
//            List<KnowledgePointsModel> results = ExcelUtiles.importExcel(file,0,1,KnowledgePointsModel.class);
        } catch (Exception e) {
            throw new BusinessException("9999","上传出错："+e.getMessage());
        }
        return BaseModle.getSuccessData();
    }


    private void insertPoints(List<KnowledgePointsModel> list){
        log.info("课程目录导入数据===================="+list.toString());
        Map<String,Object> paraMap = new HashMap<>();
        list.forEach(pointsModel -> {
            // todo 插入数据库
        });
    }
}
