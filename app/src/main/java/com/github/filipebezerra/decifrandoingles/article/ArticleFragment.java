package com.github.filipebezerra.decifrandoingles.article;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.github.filipebezerra.decifrandoingles.R;
import com.github.filipebezerra.decifrandoingles.learning.Article;
import com.github.filipebezerra.decifrandoingles.learning.ArticleRepository;

public class ArticleFragment extends Fragment {

    private Button mLearntButton;

    private TextView mArticleTitleView;

    private TextView mArticleContentView;

    private Article mArticle;

    private RequestExerciseAction mRequestExerciseAction;

    public ArticleFragment() {
    }

    public static ArticleFragment newInstance() {
        return new ArticleFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadExtrasFromActivity();
        bindDataToViews();

        try {
            mRequestExerciseAction = (ArticleFragment.RequestExerciseAction) getActivity();
        } catch (ClassCastException ignored) {
        }
    }

    private void loadExtrasFromActivity() {
        final Bundle extras = getActivity().getIntent().getExtras();
        final String keyword = extras.getString(ArticleActivity.EXTRA_ARTICLE_KEYWORD);
        mArticle = ArticleRepository.findByKeyword(keyword);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(
                R.layout.fragment_article, container, false);
        loadViews(fragmentView);
        return fragmentView;
    }

    private void loadViews(View fragmentView) {
        mArticleTitleView = (TextView) fragmentView.findViewById(R.id.title);
        mArticleContentView = (TextView) fragmentView.findViewById(R.id.content);
        mLearntButton = (Button) fragmentView.findViewById(R.id.buttonLearnt);
        setupButtonAction();
    }

    private void setupButtonAction() {
        mLearntButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRequestExerciseAction != null) {
                    mRequestExerciseAction.onRequestExercise();
                }
            }
        });
    }

    private void bindDataToViews() {
        mArticleTitleView.setText(mArticle.getTitle());
        mArticleContentView.setText(Html.fromHtml(mArticle.getContent()));

        if (mArticle.isLearnt()) {
            mLearntButton.setText("Quero exercitar mais");
        }
    }

    interface RequestExerciseAction {
        void onRequestExercise();
    }
}
