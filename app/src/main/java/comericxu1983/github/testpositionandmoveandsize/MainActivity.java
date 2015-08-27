package comericxu1983.github.testpositionandmoveandsize;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    public static final String TAG = "eric_debug";

    private TextView text;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test();
            }
        });
    }

    private void test() {
        testGetViewInfo();
        testChangeViewPos();
//        testChangeViewSize();
//        testAnimatedChangeViewPos();
    }

    private void testAnimatedChangeViewPos() {

        //X和translationX的区别是，translationX是针对left的偏移量，而X是位置，假设原先text在(500, 500)的地方
        //那么translationX会从500，500移动到1500，500处
        //而X的方式只会从0,500移动到1000,1500
        ObjectAnimator translationX = ObjectAnimator.ofFloat(text, "translationX", 0, 1000);
        //        ObjectAnimator translationX = ObjectAnimator.ofFloat(text, "X", 0, 1000);

        translationX.setDuration(5 * 1000);


        translationX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                printViewInfo(text);

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        translationX.start();


    }

    private void testChangeViewSize() {
        text.setWidth(228);
        text.setHeight(70);

        text.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.d(TAG, "testChangeViewSize onLayoutChange");
                printViewInfo(text);

                /*
                D/eric_debug(24985): pos -> x:51.000000, y:51.000000, left:51, top:51, width:227, height:69, measuredWidth:227, measuredHeight:69
D/eric_debug(24985): pos -> x:1051.000000, y:51.000000, left:51, top:51, width:227, height:69, measuredWidth:227, measuredHeight:69
移动完毕后，left, top仍然没有变化，而X和Y已经变成正确值了
                 */

                //todo eric
//                text.removeOnLayoutChangeListener();
            }
        });
    }

    private void testChangeViewPos() {
        Log.d(TAG, "testChangeViewPos setTo point(600, 600)");


        //todo eric it doesn't work at all
//        text.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                Log.d(TAG, "testChangeViewPos onLayoutChange");
//                printViewInfo(text);
//
//                text.removeOnLayoutChangeListener(this);
//            }
//        });



        text.setX(600);
        text.setY(600);

        //不需要回调，直接已经将view 放到了对应位置
        printViewInfo(text);


    }
    private void testGetViewInfo() {
        Log.d(TAG, "view:" + text);

        printViewMargins(text);
        printViewPaddings(text);

        printViewInfo(text);
    }

    private void printViewInfo(View view) {
        String msg = String.format("pos -> x:%f, y:%f, left:%d, top:%d, width:%d, height:%d, measuredWidth:%d, measuredHeight:%d",
                view.getX(), view.getY(), view.getLeft(),
                view.getTop(), view.getWidth(), view.getHeight(),
                view.getMeasuredWidth(), view.getMeasuredHeight());

        Log.d(TAG, msg);
    }

    private void printViewPaddings(View view) {
        String msg = String.format("padding(%d, %d, %d, %d)",
                view.getPaddingLeft(),
                view.getPaddingTop(),
                view.getPaddingRight(),
                view.getPaddingBottom());

        Log.d(TAG, msg);
    }

    private void printViewMargins(View view) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        String msg = String.format("margin(%d, %d, %d, %d)",
                layoutParams.leftMargin, layoutParams.topMargin,
                layoutParams.rightMargin, layoutParams.bottomMargin);

        Log.d(TAG, msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
