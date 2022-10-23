package com.example.resortingapp;

import java.io.Serializable;

public class Interest implements Serializable {
	private int path;
	private String Name;

	public Interest(int path, String name) {
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
