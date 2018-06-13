package net.gepergee.usualtestproject.customview.view;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import java.util.List;

/**
 * @author geqipeng
 * @date 2017/11/27
 */

public class CustomMarqueeText extends TextView implements Runnable{
        private int currentScrollX;// 当前滚动的位置
        private boolean isStop = false;
        private int textWidth;
        private List<String> mList;
        private final int REPEAT = 1;
        private int repeatCount = 0;
        private int currentNews = 0;

        public CustomMarqueeText(Context context) {
            super(context);
            init();
        }
        public CustomMarqueeText(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }
        public CustomMarqueeText(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        public void init(){
            setClickable(true);
            setSingleLine(true);
            setEllipsize(TextUtils.TruncateAt.MARQUEE);
            setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
        }

        public void setData(List<String> mList){
            if(mList == null || mList.size()==0){
                return;
            }
            this.mList = mList;
            currentNews = 0;
            String n = mList.get(currentNews);
            setText(n);
            setTag(n);
            startScroll();
        }

        @Override
        public void setText(CharSequence text, BufferType type) {
            super.setText(text, type);
            MeasureTextWidth();
            startScroll();
        }

        @Override
        public void onScreenStateChanged(int screenState) {
            super.onScreenStateChanged(screenState);
            if(screenState == SCREEN_STATE_ON){
                startScroll();
            }else{
                stopScroll();
            }
        }

        /**
         * 获取文字宽度
         */
        private void MeasureTextWidth() {
            Paint paint = this.getPaint();
            String str = this.getText().toString();
            textWidth = (int) paint.measureText(str);
        }

        @Override
        public void run() {
            if(textWidth < 1){
                if(mList != null && mList.size() > 0){
                    nextNews();
                }else{
                    return;
                }
            }
            currentScrollX += 1;// 滚动速度
            scrollTo(currentScrollX, 0);
            if (isStop) {
                return;
            }
            if (getScrollX() >= textWidth) {
                currentScrollX = -getWidth();
                scrollTo(currentScrollX, 0);
                if(repeatCount >= REPEAT){
                    //reach max times
                    nextNews();
                }else{
                    repeatCount ++;
                }
            }

            postDelayed(this, 10);
        }

        private void nextNews(){
            repeatCount = 0;
            currentNews ++;
            currentNews = currentNews%mList.size();//cycle index
            String n = mList.get(currentNews);
            setText(n);
            setTag(n);
        }

        // 开始滚动
        public void startScroll() {
            isStop = false;
            this.removeCallbacks(this);
            post(this);
        }
        // 停止滚动
        public void stopScroll() {
            isStop = true;
        }

}
