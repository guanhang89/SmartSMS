package example.guanhang.smartsms.ui.activity;

import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.List;

import example.guanhang.smartsms.R;
import example.guanhang.smartsms.adapter.MainPagerAdapter;
import example.guanhang.smartsms.base.BaseActivity;
import example.guanhang.smartsms.fragment.ConversationFragment;
import example.guanhang.smartsms.fragment.GroupFragment;
import example.guanhang.smartsms.fragment.SearchFragment;

public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private List<Fragment> fragments;
    private TextView tv_conversation;
    private TextView tv_group;
    private TextView tv_search;
    private MainPagerAdapter adapter;
    private LinearLayout ly_conversation;
    private LinearLayout ly_group;
    private LinearLayout ly_search;
    private View v_indicate_line;

    @Override
    public void initView() {

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tv_conversation = (TextView) findViewById(R.id.tv_conversation);
        tv_group = (TextView) findViewById(R.id.tv_group);
        tv_search = (TextView) findViewById(R.id.tv_search);

        ly_conversation = (LinearLayout) findViewById(R.id.ly_conversation);
        ly_group = (LinearLayout) findViewById(R.id.ly_group);
        ly_search = (LinearLayout) findViewById(R.id.ly_search);

        v_indicate_line = findViewById(R.id.v_indicate);
    }

    @Override
    public void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int distance = positionOffsetPixels / 3;
                ViewPropertyAnimator.animate(v_indicate_line).translationX(distance + position * v_indicate_line.getWidth()).setDuration(0);
            }

            @Override
            public void onPageSelected(int position) {
                textLightAndSize();
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ly_conversation.setOnClickListener(this);
        ly_group.setOnClickListener(this);
        ly_search.setOnClickListener(this);

    }

    @Override
    public void initData() {
        fragments = new ArrayList<>();
        ConversationFragment fragment1 = new ConversationFragment();
        GroupFragment fragment2 = new GroupFragment();
        SearchFragment fragment3 = new SearchFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        textLightAndSize();

        setIndicate();
    }

    private void setIndicate() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        int width = point.x;
        v_indicate_line.getLayoutParams().width = width / 3;

    }

    @Override
    public void processOnlick(View view) {
        switch (view.getId()) {
            case R.id.ly_conversation:
                viewPager.setCurrentItem(0);
                break;
            case R.id.ly_group:
                viewPager.setCurrentItem(1);
                break;
            case R.id.ly_search:
                viewPager.setCurrentItem(2);
                break;
        }
    }
    private void textLightAndSize() {
        int item = viewPager.getCurrentItem();
        tv_conversation.setTextColor(item ==0? 0xffffffff:0xaa666666);
        tv_group.setTextColor(item ==1? 0xffffffff:0xaa666666);
        tv_search.setTextColor(item ==2? 0xffffffff:0xaa666666);

        ViewPropertyAnimator.animate(tv_conversation).scaleX(item ==0?1.2f:1).setDuration(200);
        ViewPropertyAnimator.animate(tv_group).scaleX(item ==1?1.2f:1).setDuration(200);
        ViewPropertyAnimator.animate(tv_search).scaleX(item ==2?1.2f:1).setDuration(200);
        ViewPropertyAnimator.animate(tv_conversation).scaleY(item ==0?1.2f:1).setDuration(200);
        ViewPropertyAnimator.animate(tv_group).scaleY(item ==1?1.2f:1).setDuration(200);
        ViewPropertyAnimator.animate(tv_search).scaleY(item ==2?1.2f:1).setDuration(200);

    }

}
