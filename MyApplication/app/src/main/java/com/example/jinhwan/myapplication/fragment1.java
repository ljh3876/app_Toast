package com.example.jinhwan.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class fragment1 extends Fragment{
    String table_name[] = {"사과테이블","포도테이블","키위테이블","자몽테이블"};
    Table[] table = new Table[4];
    TextView textview_member,textview_name,textview_spa,textview_piz,textview_time,textview_price;
    Button btn_new,btn_modi,btn_init,table_1,table_2,table_3,table_4;
    private class Table {
        int index = 0;
        double price;
        String name, spa, piz, time, member;
        boolean member1, member2;
        private Table(String name) {
            this.name = name;
        }

        private void setTable(String time, String spa, String piz, boolean member1, boolean member2,int index) {
            this.time = time;
            this.spa = spa;
            this.piz = piz;
            this.member1 = member1;
            this.member2 = member2;
            this.index = index;
            if(member1 == true){
                this.member = "일반";
                this.price=Math.round((10000*Integer.parseInt(spa)+12000*Integer.parseInt(piz))*0.9);
            }else{
                this.member="VIP";
                this.price=Math.round((10000*Integer.parseInt(spa)+12000*Integer.parseInt(piz))*0.7);
            }
        }
    }
    void cleanTable(){
        textview_member.setText("");
        textview_spa.setText("");
        textview_piz.setText("");
        textview_time.setText("");
        textview_member.setText("");
        textview_price.setText("");
    }
    void setTable(int tableNumber){
        textview_member.setText(table[tableNumber].member);
        textview_spa.setText(table[tableNumber].spa);
        textview_piz.setText(table[tableNumber].piz);
        textview_time.setText(table[tableNumber].time);
        textview_member.setText(table[tableNumber].member);
        textview_price.setText(String.valueOf(table[tableNumber].price));
    }
    public void tableListener(int tableNumber, View btn) {
        Button temp = (Button)btn;
        textview_name.setText(table[tableNumber].name);
        if (table[tableNumber].index == 0) {
            Toast.makeText(getContext(), "주문이 없습니다.", Toast.LENGTH_SHORT).show();
            cleanTable();
        } else {
            setTable(tableNumber);
        }
        makeOrder(tableNumber,temp);
        initiateOrder(tableNumber,temp);
        modifyOrder(tableNumber,temp);
    }
    public void makeOrder(final int tableNumber,final Button btn){
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                View view = View.inflate(getContext(), R.layout.order,null);
                final TextView time = (TextView)view.findViewById(R.id.order_time);
                final EditText spa = (EditText)view.findViewById(R.id.order_spa);
                final EditText piz = (EditText)view.findViewById(R.id.order_piz);
                final RadioButton norm = (RadioButton)view.findViewById(R.id.radioButton_norm);
                final RadioButton vip = (RadioButton)view.findViewById(R.id.radioButton_vip);
                final TextView tableName= (TextView)view.findViewById(R.id.order_table);
                tableName.setText(table_name[tableNumber]);
                time.setText(DateFormat.getDateTimeInstance().format(new Date()));
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                dlg.setView(view)
                        .setPositiveButton("닫기",null)
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                table[tableNumber].setTable(time.getText().toString(),spa.getText().toString(),piz.getText().toString(),norm.isChecked(),vip.isChecked(),1);
                                btn.setText(table[tableNumber].name);
                                setTable(tableNumber);
                                Snackbar.make(v,"예약되었습니다.",Snackbar.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

    }
    public void initiateOrder(final int tableNumber,final Button btn){
        btn_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table[tableNumber].index=0;
                cleanTable();
                btn.setText(table[tableNumber].name+"(비어있음)");
            }
        });
    }
    public void modifyOrder(final int tableNumber, final Button btn){
        btn_modi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(table[tableNumber].index == 0){
                    Toast.makeText(getContext(),"비어있는 테이블입니다.",Toast.LENGTH_SHORT).show();
                }else{
                    View view = View.inflate(getContext(), R.layout.order,null);
                    final TextView time = (TextView)view.findViewById(R.id.order_time);
                    final EditText spa = (EditText)view.findViewById(R.id.order_spa);
                    final EditText piz = (EditText)view.findViewById(R.id.order_piz);
                    final RadioButton norm = (RadioButton)view.findViewById(R.id.radioButton_norm);
                    final RadioButton vip = (RadioButton)view.findViewById(R.id.radioButton_vip);
                    final TextView tableName= (TextView)view.findViewById(R.id.order_table);
                    time.setText(table[tableNumber].time);
                    spa.setText(table[tableNumber].spa);
                    piz.setText(table[tableNumber].piz);
                    norm.setChecked(table[tableNumber].member1);
                    vip.setChecked(table[tableNumber].member2);
                    tableName.setText(table[tableNumber].name);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                    dlg.setView(view)
                            .setPositiveButton("닫기",null)
                            .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    table[tableNumber].setTable(time.getText().toString(),spa.getText().toString(),piz.getText().toString(),norm.isChecked(),vip.isChecked(),1);
                                    btn.setText(table[tableNumber].name);
                                    setTable(tableNumber);
                                    Snackbar.make(v,"수정되었습니다.",Snackbar.LENGTH_SHORT).show();
                                }
                            }).show();
                }
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment1,container,false);
        init(fragView);
        setListener();
        return fragView;
    }

    public void init(View v){
        for(int i =0; i<table.length;i++)
            table[i]=new Table(table_name[i]);
        textview_member =(TextView)v.findViewById(R.id.textView_member);
        textview_name =(TextView)v.findViewById(R.id.textView_name);
        textview_spa =(TextView)v.findViewById(R.id.textView_spa);
        textview_piz =(TextView)v.findViewById(R.id.textView_piz);
        textview_time =(TextView)v.findViewById(R.id.textView_time);
        textview_price=(TextView)v.findViewById(R.id.textView_price);
        btn_new = (Button)v.findViewById(R.id.button_new);
        btn_init = (Button)v.findViewById(R.id.button);
        btn_modi = (Button)v.findViewById(R.id.button_modify);
        table_1=(Button)v.findViewById(R.id.button_table1);
        table_2=(Button)v.findViewById(R.id.button_table2);
        table_3=(Button)v.findViewById(R.id.button_table3);
        table_4=(Button)v.findViewById(R.id.button_table4);
    }
    void setListener(){
        table_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableListener(0,v);
            }
        });
        table_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableListener(1,v);
            }
        });
        table_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableListener(2,v);
            }
        });
        table_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableListener(3,v);
            }
        });
    }
}
