package com.magic.wdl.animatedlinearlayout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdongliang on 16/10/17.
 */

public class AnimatedLinearLayout extends LinearLayout {
    private List<View> directChildViewList = new ArrayList<>();
    private OnClickListener directChildOnClickListener = null;

    private boolean isInited = false;

    public AnimatedLinearLayout(Context context) {
        super(context);
    }

    public AnimatedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimatedLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!isInited) {
            init();

            isInited = true;
        }
    }

    private void init() {
        // 获取直属子view数量
        int directChildCnt = getChildCount();
        for (int i = 0; i < directChildCnt; i++) {
            // 将直属子view添加到list中
            final View directChildView = getChildAt(i);
            directChildViewList.add(directChildView);

            // 给直属子view添加点击响应
            directChildView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 如果设置了子view的点击事件,先执行点击事件
                    if (null != directChildOnClickListener) {
                        directChildOnClickListener.onClick(v);
                    }

                    // 然后隐藏该view
                    directChildView.setVisibility(INVISIBLE);

                    // 并将排在该view之后的view依次往前移动一个位置,移动的距离为点击消失的view的宽度(或高度)
                    MarginLayoutParams  layoutParams = (MarginLayoutParams ) directChildView.getLayoutParams();

                    int currentPos = directChildViewList.indexOf(directChildView);
                    for (int j = currentPos + 1; j < directChildViewList.size(); j++) {
                        View viewToMove = directChildViewList.get(j);
                        if (getOrientation() == HORIZONTAL) {
                            float moveLeftVal = directChildView.getWidth() + layoutParams.leftMargin
                                    + layoutParams.rightMargin;

                            float curTranslationX = viewToMove.getTranslationX();
                            ObjectAnimator moveLeft = ObjectAnimator.ofFloat(viewToMove, "translationX", curTranslationX, curTranslationX - moveLeftVal);
                            moveLeft.setDuration(1000);
                            moveLeft.start();
                        } else {
                            float moveTopVal = directChildView.getHeight() + layoutParams.topMargin
                                    + layoutParams.bottomMargin;
                            float curTranslationY = viewToMove.getTranslationY();
                            ObjectAnimator moveTop = ObjectAnimator.ofFloat(viewToMove, "translationY", curTranslationY, curTranslationY - moveTopVal);
                            moveTop.setDuration(1000);
                            moveTop.start();
                        }
                    }
                }
            });
        }
    }

    // 设置直属子view点击事件
    public void setDirectChildOnClickListener(OnClickListener listener) {
        directChildOnClickListener = listener;
    }
}