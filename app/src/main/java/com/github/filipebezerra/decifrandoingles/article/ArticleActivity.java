package com.github.filipebezerra.decifrandoingles.article;
import android.support.v7.app.*;
import android.os.*;
import com.github.filipebezerra.decifrandoingles.*;
import com.github.filipebezerra.decifrandoingles.learning.*;
import android.content.*;
import android.widget.*;
import android.text.*;
import android.support.v4.app.Fragment;
import android.app.*;
import com.github.filipebezerra.decifrandoingles.main.*;

public class ArticleActivity extends AppCompatActivity
implements ArticleFragment.RequestExerciseAction,
	ExerciseFragment.RequestRevisionAction
{

	static final String EXTRA_ARTICLE_KEYWORD = "ArticleKeyword";

	public static String EXTRA_POSITION_IN_LIST = "PositionInList";

	public static void launch(Activity activity, int position, Article article) {
		activity.startActivityForResult(new Intent(activity, ArticleActivity.class)
			.putExtra(EXTRA_POSITION_IN_LIST, position)
			.putExtra(EXTRA_ARTICLE_KEYWORD, article.getKeyword()),
			MainActivity.REQUEST_ARTICLE
		);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		getSupportFragmentManager().beginTransaction()
			.add(R.id.fragment_placeholder, ArticleFragment.newInstance())
			.commit();
	}
	
	@Override
	public void onRequestExercise() {
		final Fragment fragment = getSupportFragmentManager()
			.findFragmentById(R.id.fragment_placeholder);
		
		if (fragment == null || ! (fragment instanceof ExerciseFragment)) {
			getSupportFragmentManager().beginTransaction()
				.replace(R.id.fragment_placeholder, ExerciseFragment.newInstance())
				.addToBackStack(null)
				.commit();
		}
	}
	
	@Override
	public void onRequestRevision() {
		final Fragment fragment = getSupportFragmentManager()
			.findFragmentById(R.id.fragment_placeholder);

		if (fragment == null || ! (fragment instanceof ArticleFragment)) {
			getSupportFragmentManager().beginTransaction()
				.replace(R.id.fragment_placeholder, ArticleFragment.newInstance())
				.commit();
		}
	}

} 
