package com.github.filipebezerra.decifrandoingles.learning;

import com.github.filipebezerra.decifrandoingles.article.*;

public class Article {
	private String keyword;
	
	private String title;
	
	private int imageResource;
	
	private String content;
	
	private Exercise exercise;
	
	private boolean learnt;
	
	private long learntAt;

	public Article(String keywork, String title, int imageResource, String content, Exercise exercise) {
		this.keyword = keywork;
		this.title = title;
		this.imageResource = imageResource;
		this.content = content;
		this.exercise = exercise;
	}

	public void setLearntAt(long learntAt)
	{
		this.learntAt = learntAt;
	}

	public long getLearntAt()
	{
		return learntAt;
	}

	public void setLearnt(boolean learnt)
	{
		this.learnt = learnt;
	}

	public boolean isLearnt()
	{
		return learnt;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setImageResource(int imageResource) {
		this.imageResource = imageResource;
	}

	public int getImageResource() {
		return imageResource;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public Exercise getExercise() {
		return exercise;
	}
	
	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Article
			&& this.getTitle().equals(((Article)o).getTitle());
	}
	
}
