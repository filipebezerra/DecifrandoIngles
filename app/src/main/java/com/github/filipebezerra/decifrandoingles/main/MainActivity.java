package com.github.filipebezerra.decifrandoingles.main;
import android.app.*;
import android.os.*;
import android.support.v7.app.*;
import com.github.filipebezerra.decifrandoingles.*;
import android.view.*;
import android.support.v7.widget.*;
import com.github.filipebezerra.decifrandoingles.learning.*;
import com.github.filipebezerra.decifrandoingles.article.*;
import android.content.*;
import com.github.filipebezerra.decifrandoingles.recyclerview.*;

public class MainActivity extends AppCompatActivity implements ArticleAdapter.ClickListener {

	private ArticleAdapter mAdapter;

	public static final int REQUEST_ARTICLE = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loadViews();
	}

	@SuppressWarnings("ConstantConditions")
	private void loadViews() {
		RecyclerView recyclerView = (RecyclerView) findViewById(android.R.id.list);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(mAdapter = new ArticleAdapter(this));
		recyclerView.addOnItemTouchListener(
				new ArticleAdapter.RecyclerTouchListener(this, recyclerView, this));
		recyclerView.addItemDecoration(new DividerDecoration(this));
	}
	
	@Override
	public void onClick(View view, int position) {
		final Article article = mAdapter.getItem(position);
		
		if (article != null) ArticleActivity.launch(this, position, article);
	}

	@Override
	public void onLongClick(View view, int position) {
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ARTICLE
			&& resultCode == Activity.RESULT_OK) {
				final int position = data.getExtras()
						.getInt(ArticleActivity.EXTRA_POSITION_IN_LIST);
				mAdapter.notifyItemChanged(position);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
