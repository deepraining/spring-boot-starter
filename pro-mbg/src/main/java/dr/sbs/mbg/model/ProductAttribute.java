package dr.sbs.mbg.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class ProductAttribute implements Serializable {
    private Long id;

    private Long productAttributeCategoryId;

    private String name;

    /**
     * 属性选择类型：0->唯一；1->单选；2->多选
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "属性选择类型：0->唯一；1->单选；2->多选", position = 4)
    private Integer selectType;

    /**
     * 属性录入方式：0->手工录入；1->从列表中选取
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "属性录入方式：0->手工录入；1->从列表中选取", position = 5)
    private Integer inputType;

    /**
     * 可选值列表，以逗号隔开
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "可选值列表，以逗号隔开", position = 6)
    private String inputList;

    /**
     * 排序字段：最高的可以单独上传图片
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "排序字段：最高的可以单独上传图片", position = 7)
    private Integer sort;

    /**
     * 分类筛选样式：1->普通；1->颜色
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "分类筛选样式：1->普通；1->颜色", position = 8)
    private Integer filterType;

    /**
     * 检索类型；0->不需要进行检索；1->关键字检索；2->范围检索
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "检索类型；0->不需要进行检索；1->关键字检索；2->范围检索", position = 9)
    private Integer searchType;

    /**
     * 相同属性产品是否关联；0->不关联；1->关联
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "相同属性产品是否关联；0->不关联；1->关联", position = 10)
    private Integer relatedStatus;

    /**
     * 是否支持手动新增；0->不支持；1->支持
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "是否支持手动新增；0->不支持；1->支持", position = 11)
    private Integer handAddStatus;

    /**
     * 属性的类型；0->规格；1->参数
     *
     * @mbg.generated
     */
    @ApiModelProperty(value = "属性的类型；0->规格；1->参数", position = 12)
    private Integer type;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductAttributeCategoryId() {
        return productAttributeCategoryId;
    }

    public void setProductAttributeCategoryId(Long productAttributeCategoryId) {
        this.productAttributeCategoryId = productAttributeCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSelectType() {
        return selectType;
    }

    public void setSelectType(Integer selectType) {
        this.selectType = selectType;
    }

    public Integer getInputType() {
        return inputType;
    }

    public void setInputType(Integer inputType) {
        this.inputType = inputType;
    }

    public String getInputList() {
        return inputList;
    }

    public void setInputList(String inputList) {
        this.inputList = inputList;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getFilterType() {
        return filterType;
    }

    public void setFilterType(Integer filterType) {
        this.filterType = filterType;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public Integer getRelatedStatus() {
        return relatedStatus;
    }

    public void setRelatedStatus(Integer relatedStatus) {
        this.relatedStatus = relatedStatus;
    }

    public Integer getHandAddStatus() {
        return handAddStatus;
    }

    public void setHandAddStatus(Integer handAddStatus) {
        this.handAddStatus = handAddStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productAttributeCategoryId=").append(productAttributeCategoryId);
        sb.append(", name=").append(name);
        sb.append(", selectType=").append(selectType);
        sb.append(", inputType=").append(inputType);
        sb.append(", inputList=").append(inputList);
        sb.append(", sort=").append(sort);
        sb.append(", filterType=").append(filterType);
        sb.append(", searchType=").append(searchType);
        sb.append(", relatedStatus=").append(relatedStatus);
        sb.append(", handAddStatus=").append(handAddStatus);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}