package com.shishuheng.reader.ui.filepicker;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.shishuheng.reader.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by shishuheng on 2018/1/23.
 */

public class FilePicker extends AlertDialog.Builder {
    View view;              //此FilePick的主界面
    Context context;
    File directory = null;  //当前的目录文件
    ArrayList<File> list;   //当前目录下的所有文件
    ListView filesList;     //文件列表ListView
    TextView returnToUp;    //返回上一层文本按钮
    AlertDialog dialog;     //外部调用create()方法生成的AlertDialog实例
    public FilePicker(Context context, String path) {
        super(context);
        this.context = context;
        directory = new File(path);
        list = new ArrayList<>();
        view = LayoutInflater.from(context).inflate(R.layout.dialog_filepicker, null);
        returnToUp = view.findViewById(R.id.back_FilePicker);
        filesList = view.findViewById(R.id.listView_FilePicker);
        setView(view);
        fillList();
    }

    void fillList() {
        //判断当前的文件是否为目录 如果是目录 获得此目录下的所有文件 并存入ArrayList
        if (directory.isDirectory() && directory != null) {
            list.clear();
            //初次创建的时候将当前目录显示为标题
            setTitle(directory.getName());
            //当目录改变时 重新设置标题
            if (dialog != null)
                dialog.setTitle(directory.getName());
            //获取所有此目录下的文件
            File[] files = directory.listFiles();
            //存入list
            if (files != null && files.length > 0) {
                for (File file : files) {
                    list.add(file);
                }
            }
            //按字典序排序
            Collections.sort(list);

            //创建ListView的适配器
            FileItemAdapter adapter = new FileItemAdapter(context, list);
            filesList.setAdapter(adapter);
            //设置ListView中的条目点击动作
            filesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    File file = list.get(position);
                    //如果选择的是目录 则将directory设置为当前选择 并递归调用fillList方法
                    if (file.isDirectory()) {
                        directory = file;
                        fillList();
                    } else {
                        //如果选择的是文件 则将此文件的绝对路径输出 (此处可改为需要的动作)
                        Log.v("文件选定", file.getAbsolutePath());
                    }
                }
            });

            //返回上一层按钮
            returnToUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //将directory设置为directory的parent 并递归调用fillList方法
                    if (!directory.getAbsolutePath().equals("\\")) {
                        File parent = directory.getParentFile();
                        if (parent != null && parent.exists()) {
                            directory = directory.getParentFile();
                            fillList();
                        }
                    }
                }
            });
        }
    }

    @Override
    public AlertDialog create() {
        //当创建dialog的时候 获取此AlertDialog实例
        dialog = super.create();
        return dialog;
    }
}
