package com.github.filipebezerra.decifrandoingles.learning;
import java.util.*;
import android.content.*;
import android.content.res.*;
import com.github.filipebezerra.decifrandoingles.*;
import android.util.*;
import com.github.filipebezerra.decifrandoingles.article.*;
import android.preference.*;
import android.support.v4.content.*;

public class ArticleRepository {
	private static Map<String, Article> ARTICLES;

	public static void saveAsLearnt(Context context, Article article) {
		if (!article.isLearnt()) {
			article.setLearnt(true);
			article.setLearntAt(System.currentTimeMillis());
			
			SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
			
			SharedPreferencesCompat.EditorCompat
				.getInstance()
				.apply(
					editor.putLong(article.getKeyword(), article.getLearntAt()));
		}
	}
	
	public static List<Article> list(Context context) {
		if (ARTICLES == null) {
			Map<String, Article> map = new LinkedHashMap<>();
			
			final Resources res = context.getResources();
			
			final String [] keywords = res.getStringArray(R.array.articles_keywords);
			final String [] titles = res.getStringArray(R.array.articles_titles);
			final String [] contents = res.getStringArray(R.array.articles_contents);
			final TypedArray images = res.obtainTypedArray(R.array.articles_images);
			
			final String [] questions = res.getStringArray(R.array.questions);
			final String [] answers = res.getStringArray(R.array.answers);
			
			for(int i = 0; i < titles.length; i++) {
				Article article = new Article(
					keywords[i], titles[i], images.getResourceId(i, -1), 
					contents[i], new Exercise(questions[i], answers[i]));
					
					SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(context);
					if (prefs.contains(article.getKeyword())) {
						article.setLearnt(true);
						article.setLearntAt(prefs.getLong(article.getKeyword(), 0));
					}
					
				map.put(keywords[i], article);
			}
			ARTICLES = map;
		}
		
		return new ArrayList<>(ARTICLES.values());
	}
	
	public static Article findByKeyword(String keyword) {
		return ARTICLES.get(keyword);
	}
	
}
