package cn.eblcu.questionbank.ui.model;


import lombok.Data;

import java.util.List;
@Data
public class TestPaperFormatViewModel {
    private Integer testPaperId;
    private String  konwledges;
    private List<PaperFormatInfo> PaperFormatInfoLst;
}

