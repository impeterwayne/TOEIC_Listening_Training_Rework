package com.peterwayne.toeic900.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import com.peterwayne.toeic900.Activity.RealTestActivity;
import com.peterwayne.toeic900.Model.RealTest;
import com.peterwayne.toeic900.R;
import java.util.List;

public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.ViewHolder> {
    private Context context;
    private List<RealTest> testList;
    public TestListAdapter(Context context, List<RealTest> testList) {
        this.context = context;
        this.testList = testList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_test, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String testName = testList.get(position).getName();
        holder.txt_testName.setText(testName);
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txt_testName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_testName = itemView.findViewById(R.id.txt_testName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //TODO: add a dialog here
            openConfirmDialog();
        }
        private void openConfirmDialog() {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_confirm_realtest);
            Window window = dialog.getWindow();
            if (window==null)
            {
                return;
            }
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowAttrs = window.getAttributes();
            windowAttrs.gravity = Gravity.CENTER;
            window.setAttributes(windowAttrs);
            AppCompatButton btn_accept = dialog.findViewById(R.id.btn_accept);
            AppCompatButton btn_cancel = dialog.findViewById(R.id.btn_cancel);
            btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Intent intent = new Intent(context, RealTestActivity.class);
                    intent.putExtra("testName", testList.get(getAdapterPosition()).getName().trim());
                    context.startActivity(intent);
                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
