package com.example.sandra.roomate_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ToDoActivity extends AppCompatActivity {
    private DatabaseReference Db;
    private ListView todosListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        setTitle("To Do");
        todosListView= (ListView) findViewById(R.id.todoListView);

       Db = FirebaseDatabase.getInstance().getReference().child("Tasks");
        Todo todo1= new Todo("id1","lavar","0001",00002,00003, Calendar.getInstance().getTime(),Calendar.getInstance().getTime(), "Marua");

        Todo todo2= new Todo("id1","planchar","0001",00002,00003, Calendar.getInstance().getTime(),Calendar.getInstance().getTime(), "Juan");

        Todo [] todosArray= new Todo[2];
        todosArray[0]=todo1;
        todosArray[1]=todo2;
        SimpleAdapter adapter= getTodosAdapter(todosArray);
        todosListView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(todosListView);
                                               
    }




    private SimpleAdapter getTodosAdapter(Todo[] todos){
        // create the item mapping

        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("E, d  M");
        String[] from = {"id", "description", "groupId", "assignee", "dueDate"};
        int[] to = {
                R.id.itemTodoId,
                R.id.itemTodoDesc,
                R.id.itemTodoGroupId,
                R.id.itemTodoAssignee,
                R.id.itemTodoDueDate};
        // prepare the list of all records
        List<HashMap<String, String>> fillMaps = new ArrayList<>();
        for(Todo todo : todos){
            HashMap<String, String> map = new HashMap<>();
            map.put("id", String.valueOf( todo.getId()));
            map.put("description", todo.getDescription());
            map.put("groupId", todo.getGroupId());
            map.put("assignee",  todo.getAssigneeName());
            map.put("dueDate", simpleDateFormat.format(todo.getDueDate()));
            fillMaps.add(map);
        }
        return new SimpleAdapter(this,fillMaps,R.layout.item_todo,from,to);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}









