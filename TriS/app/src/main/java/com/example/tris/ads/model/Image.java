package com.example.tris.ads.model;

	public class Image {
	private String pathImage;
	private int index;

	public Image(String imagePath, int index) {
		this.pathImage = imagePath;
		this.index = index;
	}

	public String getPathImage() {
		return pathImage;
	}

	public void setCaminhoImagem(String ImagePath) {
		this.pathImage = ImagePath;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
