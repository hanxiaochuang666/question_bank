package cn.eblcu.questionbank.domain.service;

import cn.eblcu.questionbank.ui.exception.BusinessException;
import cn.eblcu.questionbank.ui.model.BaseModle;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IKnowledgePointsService {

    BaseModle importKnowledgePoints(MultipartFile file, Map<String, Object> paraMap) throws BusinessException;

    BaseModle getKnowledgePoints(int courseId) throws BusinessException;

}
