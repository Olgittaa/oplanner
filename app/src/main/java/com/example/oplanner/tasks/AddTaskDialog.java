package com.example.oplanner.tasks;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.oplanner.MainActivity;
import com.example.oplanner.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class AddTaskDialog extends Dialog implements DatePickerDialog.OnDateSetListener{

    Context context;
    @BindView(R.id.editText)
    public TextInputEditText editText;

    @BindView(R.id.date)
    public Button datebtn;

    @BindView(R.id.positivebtn)
    public Button positivebtn;

    @BindView(R.id.negativebtn)
    public Button negativebtn;

    @BindView(R.id.addSubtask)
    public Button addSubtask;

    @BindView(R.id.list)
    public SwipeMenuListView listView;

    public List<String> listSubtask;
    public ArrayAdapter<String> subTaskListAdapter;

    public boolean dateChanged = false;

    public AddTaskDialog(@NonNull Context context) {
        super(context);

        this.context = context;
        setTitle("Add task");
        setContentView(R.layout.add_task);
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.9);

        getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        ButterKnife.bind(this);

        try {
            setData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        subTaskListAdapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_list_item_1, this.listSubtask);
        listView.setAdapter(subTaskListAdapter);

        SwipeMenuCreator creator = createSwapeMenu();
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                        listSubtask.remove(position);
                        subTaskListAdapter.notifyDataSetChanged();
                return false;
            }
        });

        this.show();

        datebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        addSubtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubtaskOnClik(context, listSubtask, subTaskListAdapter);
            }
        });

        negativebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNegativeButtonClick();
            }
        });

        positivebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPositiveButtonClick();
            }
        });

    }

    private SwipeMenuCreator createSwapeMenu() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(context);
                deleteItem.setBackground(new ColorDrawable(0xFF766B5E));
                deleteItem.setWidth(100);
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };
        return creator;
    }

    protected abstract void setData() throws ParseException;

    protected abstract void onPositiveButtonClick();

    protected abstract void onNegativeButtonClick();

    private void addSubtaskOnClik(@NonNull Context context, List<String> listSubtask, ArrayAdapter<String> subTaskListAdapter) {
        EditText descriptionEditText = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Write a description");
        builder.setView(descriptionEditText);
        builder.setPositiveButton("Save", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!descriptionEditText.getText().toString().isEmpty()) {
                    listSubtask.add(descriptionEditText.getText().toString());
                    subTaskListAdapter.notifyDataSetChanged();
                }
            }
        });
        builder.show();
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, this
                , Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String str = dayOfMonth + "/" + (month+1) + "/" + year;
        datebtn.setText(str);
        dateChanged = true;
    }

    public Date setDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
}
