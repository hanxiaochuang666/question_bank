package cn.eblcu.questionbank.domain.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.eblcu.questionbank.domain.service.IKnowledgePointsService;
import cn.eblcu.questionbank.infrastructure.util.CommonUtils;
import cn.eblcu.questionbank.infrastructure.util.DateUtils;
import cn.eblcu.questionbank.persistence.dao.IKnowledgePointsDao;
import cn.eblcu.questionbank.persistence.dao.IKnowledgePointsDetailDao;
import cn.eblcu.questionbank.persistence.entity.dto.KnowledgePoints;
import cn.eblcu.questionbank.persistence.entity.dto.KnowledgePointsDetail;
import cn.eblcu.questionbank.ui.exception.BusinessException;
import cn.eblcu.questionbank.ui.model.BaseModle;
import cn.eblcu.questionbank.ui.model.KnowledgePointNode;
import cn.eblcu.questionbank.ui.model.KnowledgePointsModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class KnowledgePointsServiceImpl implements IKnowledgePointsService {


    @Value("${excel.importFields}")
    private String importFields;

    @Autowired
    private IKnowledgePointsDao pointsDao;
    @Autowired
    private IKnowledgePointsDetailDao pointsDetailDao;

    @Override
    @Transactional
    public BaseModle importKnowledgePoints(MultipartFile file, Map<String, Object> paraMap) throws BusinessException {

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
            if (!StringUtils.isEmpty(failList) && failList.size() > 0) {
                throw new BusinessException("9998", "上传失败：" + failList.size() + "条，请检查excel内容是否规范！");
            }
            insertPoints(successList, paraMap);
            // 使用工具
//            List<KnowledgePointsModel> results = ExcelUtiles.importExcel(file,0,1,KnowledgePointsModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("9999", "上传出错：" + e.getMessage());
        }
        return BaseModle.getSuccessData();
    }


    private void insertPoints(List<KnowledgePointsModel> list, Map<String, Object> map) {
        log.info("课程目录导入数据====================" + list.toString());
        // 先查这个课程是否已经有知识点了，有了就删除重新导入
        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("courseId", map.get("courseId"));
        List<KnowledgePoints> pointsList = pointsDao.selectList(objectMap);
        if (!CommonUtils.listIsEmptyOrNull(pointsList)) {
            pointsDao.deleteByPrimaryKey(pointsList.get(0).getKnowledgePointsId());
            List<KnowledgePointsDetail> details = pointsDetailDao.selectList(map);
            if (!CommonUtils.listIsEmptyOrNull(details)) {
                Map<String, Object> delMap = new HashMap<>();
                delMap.put("knowledgePointsId", details.get(0).getKnowledgePointsId());
                pointsDetailDao.deleteByParams(delMap);
            }
        }
        // 插入知识点总表
        KnowledgePoints points = new KnowledgePoints();
        points.setCourseId(Integer.parseInt(String.valueOf(map.get("courseId"))));
        points.setCategoryOne(Integer.parseInt(String.valueOf(map.get("categoryOne"))));
        points.setCategoryTwo(Integer.parseInt(String.valueOf(map.get("categoryTwo"))));
        points.setCourseName(String.valueOf(map.get("courseName")));
        points.setOrgId(Integer.parseInt(String.valueOf(map.get("orgId"))));
        points.setCreateTime(DateUtils.now());
        points.setCreateUser(Integer.parseInt(String.valueOf(map.get("createUserId"))));
        pointsDao.insertSelective(points);
        list.forEach(pointsModel -> {
            String chapterName = pointsModel.getChapter();
            String sectionName = pointsModel.getSection();
            String className = pointsModel.getKnowledgePoints();
            Integer parentId;
            // 1、章
            if (!StringUtils.isEmpty(chapterName)) {
                parentId = insertPointsDetail(pointsModel.getChapter(), pointsModel.getChapterMnemonicCode(),
                        points.getKnowledgePointsId(), 0);
                // 2、节
                Integer sectionParentId;
                if (!StringUtils.isEmpty(sectionName)) {
                    sectionParentId = insertPointsDetail(pointsModel.getSection(), pointsModel.getSectionMnemonicCode(),
                            points.getKnowledgePointsId(), parentId);
                    // 3、课时
                    if (!StringUtils.isEmpty(className)) {
                        insertPointsDetail(pointsModel.getKnowledgePoints(), pointsModel.getPointsMnemonicCode(),
                                points.getKnowledgePointsId(), sectionParentId);
                    }
                }
            }
        });
    }

    private int insertPointsDetail(String name, Integer titleNumber, Integer knowledgeId, Integer parentId) {
        KnowledgePointsDetail detail = new KnowledgePointsDetail();
        detail.setKnowledgePointsId(knowledgeId);
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("name", name);
        paraMap.put("knowledgePointsId", knowledgeId);
        List<KnowledgePointsDetail> lists = pointsDetailDao.selectList(paraMap);
        if (CommonUtils.listIsEmptyOrNull(lists)) {
            detail.setName(name);
            detail.setParentId(parentId);
            detail.setTitleNumber(titleNumber);
            pointsDetailDao.insertSelective(detail);
            return detail.getKnowledgePointsDetailId();
        }
        return lists.get(0).getKnowledgePointsDetailId();
    }

    @Override
    public BaseModle getKnowledgePoints(int courseId) throws BusinessException {

        Map<String, Object> map = new HashMap<>();
        map.put("courseId", courseId);
        List<KnowledgePoints> pointsList = pointsDao.selectList(map);
        if (CommonUtils.listIsEmptyOrNull(pointsList)) {
            return BaseModle.getFailData("-1", "该课程下还没有建立知识点！");
        }
        List<KnowledgePointNode> nodes = new ArrayList<>();
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("_sort_line", "title_number");
        paraMap.put("parentId", "0");
        paraMap.put("_order_", "ASC");
        paraMap.put("knowledgePointsId", pointsList.get(0).getKnowledgePointsId());
        nodes = getChildNodes(paraMap);
        return BaseModle.getSuccessData(nodes);
    }


    private List<KnowledgePointNode> getChildNodes(Map<String, Object> paraMap) {

        List<KnowledgePointNode> nodes = new ArrayList<>();
        List<KnowledgePointsDetail> lists = new ArrayList<>();
        lists = pointsDetailDao.selectList(paraMap);
        if (!CommonUtils.listIsEmptyOrNull(lists)) {
            lists.forEach(pointsDetail -> {
                KnowledgePointNode nodeTmp = new KnowledgePointNode();
                nodeTmp.setId(pointsDetail.getKnowledgePointsDetailId());
                nodeTmp.setKnowledgePointId(pointsDetail.getKnowledgePointsId());
                nodeTmp.setName(pointsDetail.getName());
                nodeTmp.setTitleNumber(pointsDetail.getTitleNumber());
                paraMap.put("_sort_line", "title_number");
                paraMap.put("parentId", pointsDetail.getKnowledgePointsDetailId());
                paraMap.put("_order_", "ASC");
                paraMap.put("knowledgePointsId", pointsDetail.getKnowledgePointsId());
                nodeTmp.setNodes(getChildNodes(paraMap));
                nodes.add(nodeTmp);
            });
        }
        return nodes;
    }


    @Override
    @Transactional
    public BaseModle addKnowledgePoints(int courseId, int parentId, String name) throws BusinessException {

        Map<String, Object> map = new HashMap<>();
        map.put("courseId", courseId);
        List<KnowledgePoints> pointsList = pointsDao.selectList(map);
        if (CommonUtils.listIsEmptyOrNull(pointsList)) {
            throw new BusinessException("-1", "未查到该课程！");
        }

        map.put("_sort_line", "title_number");
        map.put("parentId", parentId);
        map.put("_order_", "ASC");
        map.put("knowledgePointsId", pointsList.get(0).getKnowledgePointsId());
        List<KnowledgePointsDetail> lists = new ArrayList<>();
        lists = pointsDetailDao.selectList(map);
            KnowledgePointsDetail detail = new KnowledgePointsDetail();
            detail.setParentId(parentId);
            detail.setName(name);
            detail.setKnowledgePointsId(pointsList.get(0).getKnowledgePointsId());
        if (!CommonUtils.listIsEmptyOrNull(lists)) {
            detail.setTitleNumber(lists.get(lists.size() - 1).getTitleNumber() + 1);
        } else {
            detail.setTitleNumber(1);
        }
            pointsDetailDao.insertSelective(detail);
        return BaseModle.getSuccessData();
    }

    @Override
    public BaseModle editKnowledgePoints(int pointDetailId, String name) throws BusinessException {

        KnowledgePointsDetail detail = new KnowledgePointsDetail();
        detail.setName(name);
        detail.setKnowledgePointsDetailId(pointDetailId);
        pointsDetailDao.updateByPrimaryKeySelective(detail);
        return BaseModle.getSuccessData();
    }

    @Override
    @Transactional
    public BaseModle deleteKnowledgePoints(int pointDetailId) throws BusinessException {

        KnowledgePointsDetail detail = pointsDetailDao.selectByPrimaryKey(pointDetailId);
        if (StringUtils.isEmpty(detail)) {
            return BaseModle.getSuccessData();
        }
        List<KnowledgePointNode> nodes = new ArrayList<>();
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("_sort_line", "title_number");
        paraMap.put("parentId", detail.getParentId());
        paraMap.put("_order_", "ASC");
        paraMap.put("knowledgePointsId", detail.getKnowledgePointsId());

        // 删除时，同级下其后面的助记码都要调整
        List<KnowledgePointsDetail> lists = new ArrayList<>();
        lists = pointsDetailDao.selectList(paraMap);
        if (!CommonUtils.listIsEmptyOrNull(lists)) {
            lists.stream().filter(s -> s.getTitleNumber() > detail.getTitleNumber() && s.getTitleNumber() > 1)
                    .forEach(detailPoint -> {
                        detailPoint.setTitleNumber(detailPoint.getTitleNumber() - 1);
                        pointsDetailDao.updateByPrimaryKeySelective(detailPoint);
                    });
        }
        pointsDetailDao.deleteByPrimaryKey(pointDetailId);
        paraMap.put("parentId", pointDetailId);
        nodes = getChildNodes(paraMap);
        deleteNotes(nodes);
        return BaseModle.getSuccessData();
    }

    private void deleteNotes(List<KnowledgePointNode> nodes) {

        if (!CommonUtils.listIsEmptyOrNull(nodes)) {
            nodes.forEach(node -> {
                pointsDetailDao.deleteByPrimaryKey(node.getId());
                deleteNotes(node.getNodes());
            });
        }
    }


}
