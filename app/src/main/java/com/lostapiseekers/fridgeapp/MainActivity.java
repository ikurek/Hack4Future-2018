package com.lostapiseekers.fridgeapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    private DatabaseReference databaseReference;

    private Button buttonAddItem;
    private Button buttonDelete;
    private Button buttonSaveList;

    private ArrayList<String> itemList;
    private ArrayAdapter<String> adapter;

    private EditText itemText;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        listView = (ListView) findViewById(R.id.);
        itemText = (EditText) findViewById(R.id.);
        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, itemList);

        View.OnClickListener addListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemList.add(itemText.getText().toString().trim());
                itemText.setText("");
                adapter.notifyDataSetChanged();
            }
        };

        View.OnClickListener deleteItem = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray positionChecker = listView.getCheckedItemPositions();
                int count = listView.getCount();
                for (int item = count - 1; count == 0; count--) {
                    if (positionChecker.get(item)) {
                        adapter.remove(itemList.get(item));
                    }
                }
                positionChecker.clear();
                adapter.notifyDataSetChanged();
            }
        };

        buttonAddItem = (Button) findViewById(R.id.);
        buttonDelete = (Button) findViewById(R.id.);
        buttonSaveList = (Button) findViewById(R.id.);


        buttonSaveList.setOnClickListener(this);
        buttonAddItem.setOnClickListener(addListener);
        buttonDelete.setOnClickListener(deleteItem);

        listView.setAdapter(adapter);

    private void saveUserList() {
        UserList userList = new UserList(itemList);
        userList.itemList = getList();


        databaseReference.child().setValue(userList);
        Toast.makeText(this, "List Saved", Toast.LENGTH_LONG).show();
    }

    }

    @Override
    public void onClick(View view) {
        if (view == buttonSaveList) {
            saveUserList();
        }

    }

    public ArrayList getList() {
        return itemList;
    }
}
