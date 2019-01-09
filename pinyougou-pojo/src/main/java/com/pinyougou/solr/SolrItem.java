package com.pinyougou.solr;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Dynamic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @Package: cn.itcast.pojo
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2019/1/8 0008  时间: 10:31
 * < >
 **/
public class SolrItem implements Serializable {
    @Field("id")
    private Long id;
	@Field("goodsId")
    private Long goodsId;
	@Field("title")
    private String title;
	@Field("price")
    private double price;
	@Field("image")
    private String image;
	@Field("category")
    private String category;
	@Field("seller")
    private String seller;
	@Field("brand")
    private String brand;
	@Field("updateTime")
    private Date updateTime;

	@Dynamic
	@Field("spec_*")
	private Map<String,String> specMap;

	public Map<String, String> getSpecMap() {
		return specMap;
	}

	public void setSpecMap(Map<String, String> specMap) {
		this.specMap = specMap;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price.doubleValue();
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
