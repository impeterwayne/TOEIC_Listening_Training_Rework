package com.peterwayne.toeic900.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.peterwayne.toeic900.Model.Answer;
import com.peterwayne.toeic900.R;
import java.util.List;

public class AnswerMapAdapter extends RecyclerView.Adapter<AnswerMapAdapter.ViewHolder> {
    private Context context;
    private List<Answer> listAnswer;

    public AnswerMapAdapter(Context context, List<Answer> listAnswer) {
        this.context = context;
        this.listAnswer = listAnswer;
    }

    @NonNull
    @Override
    public AnswerMapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_answer,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerMapAdapter.ViewHolder holder, int position) {
        Answer answer = listAnswer.get(position);
        holder.txt_answer_number.setText(String.valueOf(answer.getNumber()));
        if(answer.getKeyChoosed()!=null)
        {
            holder.txt_answer_key.setText(answer.getKeyChoosed());
        }
    }

    @Override
    public int getItemCount() {
        return listAnswer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_answer_number, txt_answer_key;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_answer_number = itemView.findViewById(R.id.txt_answer_number);
            txt_answer_key = itemView.findViewById(R.id.txt_answer_key);
        }
    }
}
