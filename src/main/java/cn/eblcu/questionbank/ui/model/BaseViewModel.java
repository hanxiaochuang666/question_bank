package cn.eblcu.questionbank.ui.model;

import lombok.Data;

@Data
public class BaseViewModel {
    private Integer page;
    private Integer limit;
    private String _sort_line;
    private String _order_;
}
