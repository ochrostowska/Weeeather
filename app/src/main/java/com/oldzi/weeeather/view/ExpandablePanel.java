package com.oldzi.weeeather.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import com.oldzi.weeeather.R;

public class ExpandablePanel extends LinearLayout {

    private final int expandViewHandlerId;
    private final int panelContentId;
    private final int viewGroupId;

    private final boolean isViewGroup;

    private View expandViewHandler;
    private View contentView;
    private ViewGroup viewGroup;

    private boolean isExpanded = false;
    private int collapsedHeight = 0;
    private int contentHeight = 0;
    private int animationDuration = 0;

    private OnExpandListener expandListener;

    public ExpandablePanel(Context context) {
        this(context, null);
    }

    public ExpandablePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        expandListener = new DefaultOnExpandListener();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandablePanel, 0, 0);

        collapsedHeight = (int) a.getDimension(R.styleable.ExpandablePanel_collapsedHeight, 0.0f);
        contentHeight = (int) a.getDimension(R.styleable.ExpandablePanel_contentHeight, 0.0f);
        animationDuration = a.getInteger(R.styleable.ExpandablePanel_animationDuration, 500);

        int handleId = a.getResourceId(R.styleable.ExpandablePanel_handle, 0);
        if (handleId == 0) {
            throw new IllegalArgumentException(
                    "The handle attribute is required and must refer "
                            + "to a valid child.");
        }

        int contentId = a.getResourceId(R.styleable.ExpandablePanel_content, 0);
        if (contentId == 0) {
            throw new IllegalArgumentException("The content attribute is required and must refer to a valid child.");
        }

        int viewGroupId = a.getResourceId(R.styleable.ExpandablePanel_viewgroup, 0);
        isViewGroup = a.getBoolean(R.styleable.ExpandablePanel_isviewgroup, false);
        if (isViewGroup) {
            this.viewGroupId = viewGroupId;
        } else {
            this.viewGroupId = 0;
        }

        expandViewHandlerId = handleId;
        panelContentId = contentId;

        a.recycle();
    }

    public void setOnExpandListener(OnExpandListener listener) {
        expandListener = listener;
    }

    public void setContentHeight(int contentHeight) {
        this.contentHeight = contentHeight;
    }

    public void setCollapsedHeight(int collapsedHeight) {
        this.collapsedHeight = collapsedHeight;
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        expandViewHandler = findViewById(expandViewHandlerId);
        if (expandViewHandler == null) {
            throw new IllegalArgumentException(
                    "The handle attribute is must refer to an"
                            + " existing child.");
        }
        if (viewGroupId != 0) {
            viewGroup = (ViewGroup) findViewById(viewGroupId);
        }

        contentView = findViewById(panelContentId);
        if (contentView == null) {
            throw new IllegalArgumentException(
                    "The content attribute must refer to an"
                            + " existing child.");
        }

        // thanks to this method, we we'll have collapsed view height by default
        android.view.ViewGroup.LayoutParams lp = contentView.getLayoutParams();
        lp.height = collapsedHeight;
        contentView.setLayoutParams(lp);

        expandViewHandler.setOnClickListener(new PanelToggler());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private class PanelToggler implements OnClickListener {
        public void onClick(View v) {
            Animation a;
            if (isExpanded) {
                a = new ExpandAnimation(contentHeight, collapsedHeight, true); //od ozwinietego do zwinietego  NP 150 50
                expandListener.onCollapse(expandViewHandler, contentView);
            } else {
                a = new ExpandAnimation(collapsedHeight, contentHeight, false); // ROZWIJANIE
                expandListener.onExpand(expandViewHandler, contentView);
            }
            a.setDuration(animationDuration);
            if (contentView.getLayoutParams().height == 0) //Need to do this or else the animation will not play if the height is 0
            {
                android.view.ViewGroup.LayoutParams lp = contentView.getLayoutParams();
                lp.height = 1;
                contentView.setLayoutParams(lp);
                contentView.requestLayout();
            }
            contentView.startAnimation(a);
            isExpanded = !isExpanded;
        }
    }

    private class ExpandAnimation extends Animation {
        private int mStartHeight;
        private int mDeltaHeight;

        public ExpandAnimation(int startHeight, int endHeight, boolean expanded) {
            if (expanded) {
                mStartHeight = contentHeight + collapsedHeight;
                mDeltaHeight = -(contentHeight);
            } else {
                mStartHeight = collapsedHeight;
                mDeltaHeight = contentHeight;
            }
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            android.view.ViewGroup.LayoutParams lp = contentView.getLayoutParams();
            lp.height = (int) (mStartHeight + mDeltaHeight * interpolatedTime);

            contentView.setLayoutParams(lp);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    public interface OnExpandListener {
        void onExpand(View handle, View content);
        void onCollapse(View handle, View content);
    }

    private class DefaultOnExpandListener implements OnExpandListener {
        public void onCollapse(View handle, View content) {}
        public void onExpand(View handle, View content) {}
    }
}