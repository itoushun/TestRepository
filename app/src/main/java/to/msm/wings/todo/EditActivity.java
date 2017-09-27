package to.msm.wings.todo;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

//編集アクティビティ
public class EditActivity extends AppCompatActivity {
    //定数
    private final static int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final static int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    private static final int MENU_ITEM0 = 0;
    private static final int MENU_ITEM1 = 1;

    //情報
    private int      pos;
    private ToDoItem toDoItem;

    //アクティビティ起動時に呼ばれる
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setTitle(pos<0?"追加":"編集");

        //パラメータの取得(3)
        //pos = -1;
        toDoItem = new ToDoItem();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pos = extras.getInt("pos");
            toDoItem.title = extras.getString("title");
            toDoItem.checked = extras.getBoolean("checked");
        }

        //レイアウトの生成
        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundColor(Color.WHITE);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);

        //エディットテキストの生成
        EditText editText = new EditText(this);
        editText.setTextColor(Color.BLACK);
        editText.setSingleLine();
        editText.setText(toDoItem.title, EditText.BufferType.NORMAL);
        editText.setHint("タイトル");
        LinearLayout.LayoutParams params0 =
            new LinearLayout.LayoutParams(MP, WC);
        params0.setMargins(
            Util.dp2px(this, 10),
            Util.dp2px(this, 10),
            Util.dp2px(this, 10),
            Util.dp2px(this, 10));

        editText.setLayoutParams(params0);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s,
                int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s,
                int start, int before, int count) {
                toDoItem.title = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        layout.addView(editText);

        //チェックボックスの追加
        CheckBox checkBox = new CheckBox(this);
        checkBox.setText("完了");
        checkBox.setTextColor(Color.BLACK);
        checkBox.setChecked(toDoItem.checked);
        LinearLayout.LayoutParams params1 =
            new LinearLayout.LayoutParams(WC, WC);
        params1.setMargins(
            Util.dp2px(this, 10),
            Util.dp2px(this, 10),
            Util.dp2px(this, 10),
            Util.dp2px(this, 10));
        checkBox.setLayoutParams(params1);
        checkBox.setOnCheckedChangeListener(
            new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton button, boolean b) {
                toDoItem.checked = b;
            }
        });
        layout.addView(checkBox);
    }

    //レイアウトパラメータの生成
    private LinearLayout.LayoutParams makeLayoutParams(int w, int h, int margin) {
        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(w, h);
        params.setMargins(margin, margin, margin, margin);
        return params;
    }

    //オプションメニューの生成
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        //削除アイテムの追加
        if (pos >= 0) {
            MenuItem item0 = menu.add(0, MENU_ITEM0, 0, "削除");
            item0.setIcon(android.R.drawable.ic_menu_delete);
            item0.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        //保存アイテムの追加
        MenuItem item1 = menu.add(0, MENU_ITEM1, 0, "保存");
        item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    //メニューアイテム選択イベントの処理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == MENU_ITEM0 || itemId == MENU_ITEM1) {
            String result = null;
            if (itemId == MENU_ITEM0) result = "delete";
            if (itemId == MENU_ITEM1) result = pos<0?"add":"edit";

            //パラメータの返信(4)
            Intent intent = new Intent();
            intent.putExtra("result", result);
            intent.putExtra("pos", pos);
            intent.putExtra("title", toDoItem.title);
            intent.putExtra("checked", toDoItem.checked);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
        return true;
    }
}