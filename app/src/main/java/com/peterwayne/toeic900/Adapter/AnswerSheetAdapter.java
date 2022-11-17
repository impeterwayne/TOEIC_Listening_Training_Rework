package com.peterwayne.toeic900.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peterwayne.toeic900.Activity.RealTestActivity;
import com.peterwayne.toeic900.Model.Answer;
import com.peterwayne.toeic900.Model.Question;
import com.peterwayne.toeic900.Model.QuestionPartThreeAndFour;
import com.peterwayne.toeic900.R;
import java.util.List;

public class AnswerSheetAdapter extends RecyclerView.Adapter<AnswerSheetAdapter.ViewHolder> {
    private Context context;
    private List<Answer> listAnswer;
    public AnswerSheetAdapter(Context context, List<Answer> listAnswer) {
        this.context = context;
        this.listAnswer = listAnswer;
    }

    @NonNull
    @Override
    public AnswerSheetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_answer,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerSheetAdapter.ViewHolder holder, int position) {
        Answer answer = listAnswer.get(position);
        holder.txt_answer_number.setText(String.valueOf(answer.getNumber()));
        if(answer.getKeyChosen()!=null)
        {
            holder.txt_answer_number.setBackgroundResource(R.drawable.bg_cirle_primary);
            holder.txt_answer_key.setBackgroundResource(R.drawable.bg_cirle_primary);
            holder.txt_answer_key.setText(answer.getKeyChosen());
        }
    }

    @Override
    public int getItemCount() {
        return listAnswer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_answer_number, txt_answer_key;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_answer_number = itemView.findViewById(R.id.txt_answer_number);
            txt_answer_key = itemView.findViewById(R.id.txt_answer_key);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long questionNumber = listAnswer.get(getAdapterPosition()).getNumber();
                    List<Question> questionList = ((RealTestActivity)context).getQuestionData();
                    if(questionList!=null)
                    {
                        for(Question question : questionList) {
                            if(question.getNumber()==questionNumber)
                            {
                                int index = questionList.indexOf(question);
                                ((RealTestActivity)context).getTestPager().setCurrentItem(index);
                                break;
                            }
                            else if(question instanceof QuestionPartThreeAndFour)
                            {
                                if(((QuestionPartThreeAndFour) question).getNumber1()==questionNumber ||
                                        ((QuestionPartThreeAndFour) question).getNumber2()==questionNumber ||
                                        ((QuestionPartThreeAndFour) question).getNumber3()==questionNumber)
                                {
                                    int index = questionList.indexOf(question);
                                    ((RealTestActivity)context).getTestPager().setCurrentItem(index);
                                    break;
                                }
                            }
                        }
                    }
                }
            });
        }
    }
}
