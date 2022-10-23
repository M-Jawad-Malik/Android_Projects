package com.example.tris.ads.model;

import java.io.Serializable;

public class Category implements Serializable {
	private int path;
	private String Name;

	public Category(int path, String name) {
		this.path = path;
		this.Name = name;
	}

	public int getPath() {
		return path;
	}

	public void setPath(int caminho) {
		this.path = caminho;
	}

	public String getName() {
		return Name;
	}

	public void setName(String nome) {
		this.Name = nome;
	}
}
