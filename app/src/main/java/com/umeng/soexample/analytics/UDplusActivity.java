package com.umeng.soexample.analytics;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.dplus.UMADplus;
import com.umeng.soexample.BaseActivity;
import com.umeng.soexample.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangfei on 2018/1/23.
 */

public class UDplusActivity extends BaseActivity {

    private Context mContext;
    private final String mPageName = "UDplusActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("统计UDplus");
        setBackVisibily();

        mContext = this;
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        // MobclickAgent.setAutoLocation(true);
        // MobclickAgent.setSessionContinueMillis(1000);
//        MobclickAgent.setScenarioType(mContext, EScenarioType.E_UM_NORMAL);
        //设置dplus case
        MobclickAgent.setScenarioType(mContext, MobclickAgent.EScenarioType.E_DUM_NORMAL);


        findViewById(R.id.analytics_g1_b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UMADplus.track(mContext, "forward");
                Toast.makeText(mContext, "已完成track事件", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.analytics_g1_b2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> music = new HashMap<String, Object>();
                music.put("type", "popular");// 流行歌曲
                music.put("artist", "JJLin");// 艺术家
                music.put("duration", 3 * 60 * 1000);// 曲长3分钟
                music.put("listener", 90 * 1000);// 用户听1.5分钟
                // ... ...
                UMADplus.track(mContext, "music", music);
                Toast.makeText(mContext, "已完成自定义的track事件", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.analytics_g2_b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UMADplus.registerSuperProperty(mContext, "supperkey", "supperValue");
                Toast.makeText(mContext, "已完成设置超级属性", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.analytics_g2_b2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String s = UMADplus.getSuperProperties(mContext);

                    if (!TextUtils.isEmpty(s)) {
                        JSONObject obj = new JSONObject(s);
                        Log.d("test", "===>" + obj.getString("supperkey"));
                    }
                    Toast.makeText(mContext, "已完成获取超级属性", Toast.LENGTH_SHORT).show();
                } catch (Throwable e) {
                }
            }
        });
        findViewById(R.id.analytics_g2_b3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UMADplus.clearSuperProperties(mContext);
                Toast.makeText(mContext, "已完成清除超级属性", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.analytics_g3_b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 最多五个
                List<String> fisLaunchList = new ArrayList<String>();
                fisLaunchList.add("click");
                fisLaunchList.add("track_id_1");
                fisLaunchList.add("track_id_2");
                fisLaunchList.add("track_id_3");
                fisLaunchList.add("forward");
                UMADplus.setFirstLaunchEvent(mContext, fisLaunchList);
                Toast.makeText(mContext, "已完成关注首次触发事件", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_udplus;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(mContext);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(mContext);
    }
}
