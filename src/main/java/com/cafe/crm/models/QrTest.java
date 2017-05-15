package com.cafe.crm.models;

import java.util.List;


public class QrTest {
	private int id;
	private String name;
	private List<String> product;
	private String link;

	public QrTest(int id, String name, List<String> product, String link) {
		this.id = id;
		this.name = name;
		this.product = product;
		this.link = link;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getProduct() {
		return product;
	}

	public void setProduct(List<String> product) {
		this.product = product;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "QrTest:" + "\n" +
				"id = " + id + "\n" +
				"name = " + name + "\n" +
				"product = " + product + "\n" +
				"link = " + link + "\n";
	}
}
