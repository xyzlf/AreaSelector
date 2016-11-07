package com.xyzlf.area.selector;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private TextView areaText;
    private  OptionsPickerView pickerView;

    /**
     * 初始化View组件
     */
    private void initView() {
        areaText = (TextView) findViewById(R.id.area_text);
        areaText.setText(getString(R.string.area_text, getString(R.string.area_select_none)));
        findViewById(R.id.area_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerView.show();
            }
        });
        pickerView = new OptionsPickerView(MainActivity.this);
    }

    /**
     * 初始化地区数据
     */
    public void initData() {
        new AsyncTask<Void, Void, AreaModel>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected AreaModel doInBackground(Void... params) {
                return Utils.readAreaJson(getApplicationContext(), AreaModel.class);
            }

            @Override
            protected void onPostExecute(AreaModel areaModel) {
                super.onPostExecute(areaModel);

                if (null == areaModel) {
                    return;
                }
                List<AreaData> datas = areaModel.getData();
                if (null == datas || datas.isEmpty()) {
                    return;
                }
                final ArrayList<String> areaList = new ArrayList<>();
                final ArrayList<ArrayList<String>> cities = new ArrayList<>();

                for(AreaData data : datas) {
                    if (null == data) {
                        continue;
                    }
                    if (!TextUtils.isEmpty(data.getName())) {
                        areaList.add(data.getName());
                    }
                    cities.add(data.getCities());
                }

                if (null != pickerView) {
                    pickerView.setPicker(areaList, cities, true);
                    pickerView.setCyclic(false);

                    pickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3) {
                            areaText.setText(getString(R.string.area_text, areaList.get(options1) + " - " + cities.get(options1).get(option2)));
                        }
                    });
                }
            }
        }.execute();
    }

}
