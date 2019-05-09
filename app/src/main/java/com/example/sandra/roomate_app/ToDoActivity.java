package com.example.sandra.roomate_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ToDoActivity extends AppCompatActivity {
    private DatabaseReference Db;
    private ListView todosListView;
    Todo [] todosArray;
    SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        setTitle("To Do");
        todosListView= (ListView) findViewById(R.id.todoListView);

        Db = FirebaseDatabase.getInstance().getReference().child("Tasks");
        Todo todo1= new Todo("id1","lavar","0001","me", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(), "Marua");

        Todo todo2= new Todo("id1","planchar","0001","me", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(), "Juan");

        Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List notes = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                    String desAr=noteDataSnapshot.child("description").getValue().toString();
                    String assigAr=noteDataSnapshot.child("assigneeName").getValue().toString();
                    String createdAr=noteDataSnapshot.child("createdBy").getValue().toString();
                    Boolean finished= Boolean.valueOf(noteDataSnapshot.child("finished").getValue().toString());

                    //Todo todoItem = noteDataSnapshot.getValue(Todo.class);
                    Todo todoItem= new Todo();
                    todoItem.setDescription(desAr);
                    todoItem.setAssigneeName(assigAr);
                    todoItem.setCreatedBy(createdAr);
                    todoItem.setFinished(finished);
                    notes.add(todoItem);
                }

                todosArray= new Todo[notes.size()];
                for(int i=0;i<notes.size();i++){
                    todosArray[i]= (Todo) notes.get(i);
                }
                adapter= getTodosAdapter(todosArray);
                adapter.notifyDataSetChanged();
                todosListView.setAdapter(adapter);
                setListViewHeightBasedOnChildren(todosListView);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }




    private SimpleAdapter getTodosAdapter(Todo[] todos){
        // create the item mapping

        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("E, d  M");
        String[] from = {"id", "description", "groupId", "assignee"};
        int[] to = {
                R.id.itemTodoId,
                R.id.activityMade_textView,
                R.id.itemTodoGroupId,
                R.id.elementUpdated_textView,
               // R.id.itemTodoDueDate
        };
        // prepare the list of all records
        List<HashMap<String, String>> fillMaps = new ArrayList<>();
        for(Todo todo : todos){
            HashMap<String, String> map = new HashMap<>();
            map.put("id", String.valueOf( todo.getId()));
            map.put("description", todo.getDescription());
            map.put("groupId", todo.getGroupId());
            map.put("assignee",  todo.getAssigneeName());
//            map.put("dueDate", simpleDateFormat.format(todo.getDueDate()));
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

    public void createTodo(View v){
        Db = FirebaseDatabase.getInstance().getReference().child("Tasks");
        DashboardTodo fragment = new DashboardTodo();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.relative,  fragment).addToBackStack(null);
        transaction.commit();
    }
}









