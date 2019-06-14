package cn.eblcu.questionbank.domain.service;

import cn.eblcu.questionbank.ui.exception.BusinessException;
import cn.eblcu.questionbank.ui.model.BaseModle;
import org.springframework.web.multipart.MultipartFile;

public interface KnowledgePointsService {

    BaseModle importKnowledgePoints(MultipartFile file) throws BusinessException;

}
