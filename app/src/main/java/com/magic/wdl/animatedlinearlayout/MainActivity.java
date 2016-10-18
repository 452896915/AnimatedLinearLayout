package com.magic.wdl.animatedlinearlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.animated_layout)
    protected AnimatedLinearLayout mAnimatedLinearLayoutHorizental;

    @Bind(R.id.animated_layout2)
    protected AnimatedLinearLayout mAnimatedLinearLayoutVertical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // 动态添加子view
        TextView textView = (TextView) getLayoutInflater()
                .inflate(R.layout.text_view_item, mAnimatedLinearLayoutHorizental, false);
        textView.setText("停车坐爱枫林晚");
        TextView textView2 = (TextView) getLayoutInflater()
                .inflate(R.layout.text_view_item, mAnimatedLinearLayoutHorizental, false);
        textView2.setText("霜叶红于二月花");
        mAnimatedLinearLayoutHorizental.addView(textView);
        mAnimatedLinearLayoutHorizental.addView(textView2);

        mAnimatedLinearLayoutHorizental.setDirectChildOnClickListener(getOnClickListener());
        mAnimatedLinearLayoutVertical.setDirectChildOnClickListener(getOnClickListener());
    }

    private View.OnClickListener getOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, ((TextView)v).getText(), Toast.LENGTH_SHORT).show();
            }
        };
    }
}
