package win.leizhang.demo.captcha.api.dto.base;

public class MainInputDTO<T> extends AbstractPublicInputDTO {

    private T inputParam;

    /**
     * 分页
     */
    // 第几页
    private Integer pageNum;
    // 每页几条
    private Integer pageSize;

    public MainInputDTO() {
        super();
    }

    public T getInputParam() {
        return inputParam;
    }

    public void setInputParam(T inputParam) {
        this.inputParam = inputParam;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
