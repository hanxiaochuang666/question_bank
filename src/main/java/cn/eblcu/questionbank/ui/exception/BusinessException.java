package cn.eblcu.questionbank.ui.exception;

public class BusinessException extends Exception{
    private String code;
    private String describe;

    public BusinessException(String code,String message){
        super(message);
        this.code=code;
        this.describe=message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "code='" + code + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }
}
