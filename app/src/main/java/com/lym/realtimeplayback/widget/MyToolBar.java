package com.lym.realtimeplayback.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.internal.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lym.realtimeplayback.R;


/**
 * Created by LYM on 2017/1/13.
 */
public class MyToolBar extends Toolbar {
    private LayoutInflater mInflater;
    private View mView;
    private EditText mSearchView;
    private TextView mTextTitle;
    private ImageButton mLeftButton;
    private Button mRightButton;

    public MyToolBar(Context context) {
        this(context, null);
    }

    public MyToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setContentInsetsRelative(10, 10);

        if (attrs != null){
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.MyToolBar, defStyleAttr, 0);

            final Drawable leftIcon = a.getDrawable(R.styleable.MyToolBar_leftButtonIcon);
            if (leftIcon != null) {
                setLeftButtonIcon(leftIcon);
            }

            final Drawable rightIcon = a.getDrawable(R.styleable.MyToolBar_rightButtonIcon);
            if (rightIcon != null) {
                setRightButtonIcon(rightIcon);
            }

            CharSequence rightButtonText = a.getText(R.styleable.MyToolBar_rightButtonTxt);
            if (rightButtonText != null){
                setRightButtonText(rightButtonText);
            }

            boolean isShowSearchView = a.getBoolean(R.styleable.MyToolBar_isShowSearchView, false);
            if (isShowSearchView){
                showSearchView();
                hideTitleView();
            }

            a.recycle();
        }
    }

    public void setRightButtonText(CharSequence rightButtonText) {
        if(mRightButton != null){
            mRightButton.setText(rightButtonText);
        }
        mRightButton.setVisibility(VISIBLE);
    }

    private void setLeftButtonIcon(Drawable leftIcon) {
        if (mLeftButton != null){
            mLeftButton.setImageDrawable(leftIcon);
            mLeftButton.setVisibility(VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setRightButtonIcon(Drawable rightIcon) {
        if (mRightButton != null){
            mRightButton.setBackground(rightIcon);
            mRightButton.setVisibility(VISIBLE);
        }
    }

    public void setRightButtonIcon(int icon) {
//        setRightButtonIcon(getResources().getDrawable(icon));

        if (mRightButton != null){
            mRightButton.setBackgroundResource(icon);
            mRightButton.setVisibility(VISIBLE);
        }
    }

    private void initView() {
        if (mView == null) {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.toolbar, null);
            mTextTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            mSearchView = (EditText) mView.findViewById(R.id.toolbar_searchview);
            mLeftButton = (ImageButton) mView.findViewById(R.id.toolbar_leftButton);
            mRightButton = (Button) mView.findViewById(R.id.toolbar_rightButton);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER_HORIZONTAL);
            addView(mView, lp);
        }
    }

    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        if (mTextTitle != null){
            mTextTitle.setText(title);
            showTitleView();
        }
    }

    public void showSearchView(){
        if (mSearchView != null){
            mSearchView.setVisibility(VISIBLE);
        }
    }

    public void hideSearchView(){
        if (mSearchView != null){
            mSearchView.setVisibility(GONE);
        }
    }

    public void showTitleView(){
        if (mTextTitle != null){
            mTextTitle.setVisibility(VISIBLE);
        }
    }

    public void hideTitleView(){
        if (mTextTitle != null){
            mTextTitle.setVisibility(GONE);
            mRightButton.setVisibility(GONE);
        }
    }

    public void setLeftButtonClickListener(OnClickListener listener){
        mLeftButton.setOnClickListener(listener);
    }

    public void setRightButtonClickListener(OnClickListener listener){
        mRightButton.setOnClickListener(listener);
    }

    public Button getRightButton(){
        return this.mRightButton;
    }
}
