package net.buildbox.pokeri.ui_listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list_view);

        // リストが空の時のビューの設定
        LayoutInflater inflater = getLayoutInflater();
        View emptyView = inflater.inflate(
            R.layout.activity_main,
            (ViewGroup) findViewById(R.id.empty_layout));
        listView.setEmptyView(emptyView);

        // リストビューのアイテムがクリックされた時の処理
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ListView lv = (ListView) parent;
                Toast.makeText(getApplicationContext(),
                    (String) lv.getItemAtPosition(position),
                    Toast.LENGTH_SHORT).show();
            }
        });

        // スクロールの最後尾検知
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // スクロールの状態が変化した時の通知
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                ListView listView = (ListView) view;
                // スクロール時の最終行を検知
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    if (listView.getFooterViewsCount() == 0) {
                        LayoutInflater inflater = LayoutInflater.from(view.getContext());
                        ImageView footerView = (ImageView) inflater.inflate(R.layout.view_footer, null, false);
                        // フッタービューの追加
                        listView.addFooterView(footerView);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ListView listView = findViewById(R.id.list_view);

        switch (item.getItemId()) {
            case R.id.menu_load:
                // リストビューの読み込み
                String[] language = {"C/C++", "Objective-C", "Fortran",
                    "Java", "Scala", "Basic", "Ruby", "JavaScript",
                    "Python", "PHP", "C#", "COBOL", "LISP", "Scheme",
                    "Haskell", "Erlang", "ASP", "HTML"};

                List<String> list = new ArrayList<>(); // 内部保持用
                Map<String,Boolean> map = new HashMap<>(); //読み込み用
                map.put("パターン1",true);
                map.put("パターン2",false);
                map.put("パターン3",true);
                map.put("パターン4",false);
                map.put("パターン5",true);
                for(Iterator<Map.Entry<String,Boolean>> iterator = map.entrySet().iterator();iterator.hasNext();){
                    Map.Entry<String,Boolean>entry = iterator.next();
                    if(entry.getValue()){
                        list.add(entry.getKey());
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this, android.R.layout.simple_list_item_1, list);


//                ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                    this, android.R.layout.simple_list_item_1, language);

                // アダプタの設定
                listView.setAdapter(adapter);
                return true;

            case R.id.menu_position:
                // 最終項目の表示
                listView.setSelection(listView.getCount() - 1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
