import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


​

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TextView mTextView;
​

    private List<Item> mItems = new ArrayList<>();
    private Map<String, List<Item>> mItemMap = new HashMap<>();

​

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

​

        mRecyclerView = findViewById(R.id.recycler_view);
        mTextView = findViewById(R.id.text_view);

​

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

​

        new FetchDataTask().execute();

    }
​
    private void updateUI() {
        if (mItems.isEmpty()) {
            mTextView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mTextView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

​

            // Group items by listId

            for (Item item : mItems) {
                List<Item> list = mItemMap.get(item.listId);
                if (list == null) {
                    list = new ArrayList<>();
                    mItemMap.put(item.listId, list);
                }
                list.add(item);
            }



public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TextView mTextView;

    private List<Item> mItems = new ArrayList<>();
    private Map<String, List<Item>> mItemMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mTextView = findViewById(R.id.text_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        new FetchDataTask().execute();
    }

    private void updateUI() {
        if (mItems.isEmpty()) {
            mTextView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mTextView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            // Group items by listId
            for (Item item : mItems) {
                List<Item> list = mItemMap.get(item.listId);
                if (list == null) {
                    list = new ArrayList<>();
                    mItemMap.put(item.listId, list);
                }
                list.add(item);
            }

            // Sort items by listId and name
            List<List<Item>> lists = new ArrayList<>(mItemMap.values());
            Collections.sort(lists, new Comparator<List<Item>>() {
                @Override
                public int compare(List<Item> o1, List<Item> o2) {
                    return o1.get(0).listId.compareTo(o2.get(0).listId);
                }
            });

          
            List<List<Item>> lists = new ArrayList<>(mItemMap.values());
            Collections.sort(lists, new Comparator<List<Item>>() {
                @Override
                public int compare(List<Item> o1, List<Item> o2) {
                    return o1.get(0).listId.compareTo(o2.get(0).listId);
                }
            });

            for (List<Item> list : lists) {
                Collections.sort(list, new Comparator<Item>() {
                    @Override
                    public int compare(Item item1, Item item2) {
                        int result = item1.listId.compareTo(item2.listId);
                        if (result == 0) {
                            result = item1.name.compareTo(item2.name);
                        }
                        return result;
                    }
                });
            }
        }
    }
}



public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<List<Item>> mLists;

    public ItemAdapter(List<List<Item>> lists) {
        mLists = lists;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        List<Item> list = mLists.get(position);
        holder.mListIdTextView.setText(list.get(0).listId);
        StringBuilder nameBuilder = new StringBuilder();
        for (Item item : list) {
            nameBuilder.append(item.name).append("\n");
        }
        holder.mNameTextView.setText(nameBuilder.toString());
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mListIdTextView;
        TextView mNameTextView;

        ItemViewHolder(View itemView) {
            super(itemView);
            mListIdTextView = itemView.findViewById(R.id.listIdTextView);
            mNameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }
}

RecyclerView recyclerView = findViewById(R.id.recyclerView);
recyclerView.setLayoutManager(new LinearLayoutManager(this));
recyclerView.setAdapter(new ItemAdapter(lists));



