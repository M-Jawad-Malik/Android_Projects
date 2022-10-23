package com.example.resortingapp;

	public class Image {
	private String pathImage;
	private int index;

	public Image(String pathImage, int index) {
		this.pathImage = pathImage;
		this.index = index;
	}

	public String getPathImage() {
		return pathImage;
	}

	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
