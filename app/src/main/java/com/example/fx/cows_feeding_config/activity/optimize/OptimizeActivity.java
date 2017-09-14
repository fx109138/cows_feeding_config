package com.example.fx.cows_feeding_config.activity.optimize;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.fx.cows_feeding_config.R;
import com.example.fx.cows_feeding_config.adapter.CowListAdapter;
import com.example.fx.cows_feeding_config.entity.Cow;
import com.example.fx.cows_feeding_config.entity.Fodder;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by fx on 2017/9/13.
 */

public class OptimizeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSelectCow;
    private Button btnSelectFodder;
    private ListView lvSelectFodder;

    private List<Cow> cowList;
    private List<Fodder> fodderList;

    private Cow cow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        this.btnSelectCow = (Button) findViewById(R.id.btn_select_cow);
        this.btnSelectFodder = (Button) findViewById(R.id.btn_select_fodder);
        this.lvSelectFodder = (ListView) findViewById(R.id.lv_select_fodder);
    }

    private void initData() {
        cowList = DataSupport.findAll(Cow.class);
        fodderList = DataSupport.findAll(Fodder.class);
//   TODO     lvSelectFodder.setAdapter();
    }

    private void initEvent() {
        btnSelectCow.setOnClickListener(this);
        btnSelectFodder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select_cow:
                AlertDialog.Builder cowBuilder = new AlertDialog.Builder(this);
                cowBuilder.setSingleChoiceItems(new CowListAdapter(this, R.layout.item, cowList), 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ListView listView = ((AlertDialog) dialogInterface).getListView();
                        cow = (Cow) listView.getAdapter().getItem(i);
                        dialogInterface.dismiss();
                        btnSelectCow.setText(cow.getVariety() + "重" + cow.getWeight() + "产乳" + cow.getMilkProduction());
                    }
                }).create().show();
                break;
            case R.id.btn_select_fodder:
                Intent intent = new Intent(OptimizeActivity.this, FodderSelectActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivityForResult(intent, 8);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8 && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            fodderList=bundle.getParcelableArrayList("fodderInfoList");
            btnSelectFodder.setText("请设置饲料用量参数，单位kg");
//            TODO notify
        }
    }
}
