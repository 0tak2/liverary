package liverary.vo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NlBooksResult {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("kwd")
    @Expose
    private String kwd;
    @SerializedName("pageNum")
    @Expose
    private Integer pageNum;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("sort")
    @Expose
    private String sort;
    @SerializedName("result")
    @Expose
    private List<NlBook> result = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getKwd() {
        return kwd;
    }

    public void setKwd(String kwd) {
        this.kwd = kwd;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<NlBook> getResult() {
        return result;
    }

    public void setResult(List<NlBook> result) {
        this.result = result;
    }

}
