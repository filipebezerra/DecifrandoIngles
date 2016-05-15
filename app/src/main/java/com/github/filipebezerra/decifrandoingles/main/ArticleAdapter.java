package com.github.filipebezerra.decifrandoingles.main;
import android.support.v7.widget.*;
import android.view.*;
import com.github.filipebezerra.decifrandoingles.learning.*;
import java.util.*;
import android.content.*;
import android.widget.*;
import com.github.filipebezerra.decifrandoingles.*;
import com.squareup.picasso.*;
import android.text.format.*;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
	private List<Article> mArticles;
	
	private Context mContext;
	
	public ArticleAdapter(Context context) {
		mArticles = ArticleRepository.list(context);
		mContext = context;
	}
	
	@Override
	public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final View itemView = LayoutInflater.from(mContext)
			.inflate(R.layout.item_article, parent, false);
		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(ArticleAdapter.ViewHolder holder, int position) {
		final Article article = mArticles.get(position);
		
		holder.title.setText(article.getTitle());
		Picasso.with(mContext)
			.load(article.getImageResource())
			.into(holder.image);
			
		if (article.isLearnt()) {
			String timeText = DateUtils.getRelativeTimeSpanString(article.getLearntAt(),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
			
			holder.learntAt.setText("Aprendido à " + timeText);
			holder.learntAt.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public int getItemCount() {
		return mArticles.size();
	}
	
	public Article getItem(int position) {
		return mArticles.get(position);
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		public TextView title;
		public ImageView image;
		public TextView learntAt;
		
		public ViewHolder(View itemView) {
			super(itemView);
			title = (TextView) itemView.findViewById(R.id.article_title);
			image = (ImageView) itemView.findViewById(R.id.article_image);
			learntAt = (TextView) itemView.findViewById(R.id.article_learnt_at);
		}
	}
	
	
	public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
	
	public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						return true;
					}

					@Override
					public void onLongPress(MotionEvent e) {
						View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
						if (child != null && clickListener != null) {
							clickListener.onLongClick(child, recyclerView.getChildPosition(child));
						}
					}
				});
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
