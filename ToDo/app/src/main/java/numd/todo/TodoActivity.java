package numd.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



public class TodoActivity extends AppCompatActivity {
    // Create an ArrayList
    ArrayList<String> items;
    // Create an ArrayAdapter
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        // Get a handle to ListView
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        // Load the items onCreate
        readItems();

        // This adapter Allows us to display the contents of an ArrayList within a ListView
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        items.add("First Item");
        items.add("Second Item");

        // Allow for removing items
        setupListViewListener();
    }

    public void addToDoItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        itemsAdapter.add(etNewItem.getText().toString());
        etNewItem.setText("");
        // Save the items when a new list item is added
        saveItems(); // Write to file
    }

    // Attach a "LongClickListener" to each item for ListView
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long rowId) {
                // Removes item
                items.remove(position);
                // Refreshes adapter
                itemsAdapter.notifyDataSetChanged();
                // Save the items when a list item is removed
                saveItems();
                return true;
            }
        });
    }

    // Open a file and read a newline-delimited list of items
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
            e.printStackTrace();
        }
    }

    // Open a file and write a newline-delimited list of items
    private void saveItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
