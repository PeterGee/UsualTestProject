package net.gepergee.usualtestproject.customview.multiTouch;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 手势识别器
 * @author petergee
 * @date 2018/6/15
 */
public class GestureDetectorView extends View {
    private Context mContext;
    public GestureDetectorView(Context context) {
        this(context,null);
    }

    public GestureDetectorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        init();
    }

    private void init() {
        TextView textView=new TextView(mContext);
        textView.setText("点我");
        textView.setBackgroundColor(Color.GREEN);
        textView.setTextSize(10);

    }


}
