package com.github.filipebezerra.decifrandoingles.article;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.filipebezerra.decifrandoingles.R;
import com.github.filipebezerra.decifrandoingles.learning.Article;
import com.github.filipebezerra.decifrandoingles.learning.ArticleRepository;

public class ExerciseFragment extends Fragment {

    private TextView mQuestionView;

    private TextInputLayout mAnswerPlaceholderView;

    private EditText mAnswerView;

    private Article mArticle;

    private RequestRevisionAction mRequestRevisionAction;

    public ExerciseFragment() {
    }

    public static ExerciseFragment newInstance() {
        return new ExerciseFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadExtrasFromActivity();
        bindDataToViews();

        try {
            mRequestRevisionAction = (RequestRevisionAction) getActivity();
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
                R.layout.fragment_exercise, container, false);
        loadViews(fragmentView);
        return fragmentView;
    }

    private void loadViews(View fragmentView) {
        mQuestionView = (TextView) fragmentView.findViewById(R.id.question);
        mAnswerPlaceholderView = (TextInputLayout) fragmentView.findViewById(
                R.id.my_answer_placeholder);
        mAnswerView = (EditText) fragmentView.findViewById(R.id.my_answer);
        mAnswerView.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent e) {
                if (actionId == getResources().getInteger(R.integer.send)) {
                    doCheckAnswer();
                    return true;
                }
                return false;
            }
        });
        Button submitButton = (Button) fragmentView.findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCheckAnswer();
            }
        });
    }

    private void bindDataToViews() {
        mQuestionView.setText(Html.fromHtml(mArticle.getExercise().getQuestion()));
    }

    private void doCheckAnswer() {
        final String answer = mAnswerView.getText().toString();

        mAnswerPlaceholderView.setErrorEnabled(false);

        if (TextUtils.isEmpty(answer)) {
            mAnswerPlaceholderView.setErrorEnabled(true);
            mAnswerPlaceholderView.setError("Ainda não sabemos sua resposta");

        } else if (!answer.equalsIgnoreCase(mArticle.getExercise().getAnswer())) {
                new MaterialDialog.Builder(getActivity())
                        .title("Resposta incorreta")
                        .content("Infelizmente sua resposta não está certa.")
                        .positiveText("Tente novamente")
                        .negativeText("Revisar conteúdo")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog d,
                                    @NonNull DialogAction a) {
                                mAnswerView.setText("");
                                mAnswerView.requestFocus();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog d,
                                    @NonNull DialogAction a) {
                                if (mRequestRevisionAction != null) {
                                    mRequestRevisionAction.onRequestRevision();
                                }
                            }
                        })
                        .show();
        } else {
            ArticleRepository.saveAsLearnt(getActivity(), mArticle);

            new MaterialDialog.Builder(getActivity())
                    .title("Lição aprendida")
                    .content("Parabéns você aprendeu a lição " + mArticle.getTitle())
                    .positiveText("Quero aprender mais")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog d, @NonNull DialogAction a) {
                            getActivity().setResult(android.app.Activity.RESULT_OK,
                                    getActivity().getIntent());
                            getActivity().finish();
                        }
                    })
                    .show();
        }
    }

    interface RequestRevisionAction {
        void onRequestRevision();
    }
}
