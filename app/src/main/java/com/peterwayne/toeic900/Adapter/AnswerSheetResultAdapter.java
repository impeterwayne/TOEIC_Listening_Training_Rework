package com.peterwayne.toeic900.Adapter;

import static com.peterwayne.toeic900.Utils.Utils.getTimeString;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.peterwayne.toeic900.Activity.ResultActivity;
import com.peterwayne.toeic900.Model.Answer;
import com.peterwayne.toeic900.Model.Question;
import com.peterwayne.toeic900.Model.QuestionPartOne;
import com.peterwayne.toeic900.Model.QuestionPartThreeAndFour;
import com.peterwayne.toeic900.Model.QuestionPartTwo;
import com.peterwayne.toeic900.R;
import com.peterwayne.toeic900.Utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AnswerSheetResultAdapter extends RecyclerView.Adapter<AnswerSheetResultAdapter.ViewHolder> {
    private Context context;
    private List<Answer> answerList;

    public AnswerSheetResultAdapter(final Context context,
                                    final List<Answer> answerList) {
        this.context = context;
        this.answerList = answerList;
    }

    @NonNull
    @Override
    public AnswerSheetResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_answer,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerSheetResultAdapter.ViewHolder holder, int position) {
        Answer answer = answerList.get(position);
        holder.txt_answer_number.setText(String.valueOf(answer.getNumber()));
        if(answer.getKeyChosen() != null)
        {
            holder.txt_answer_key.setText(answer.getKeyChosen());
            if(answer.getKeyChosen().equals(answer.getKey()))
            {
                holder.txt_answer_number.setBackgroundResource(R.drawable.bg_circle_green);
                holder.txt_answer_key.setBackgroundResource(R.drawable.bg_circle_green);
            }else
            {
                holder.txt_answer_number.setBackgroundResource(R.drawable.bg_circle_red);
                holder.txt_answer_key.setBackgroundResource(R.drawable.bg_circle_red);
            }
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return answerList.size();
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
                    Answer answer = answerList.get(getAdapterPosition());
                    Question questionData = null;
                    List<Question> questionList =((ResultActivity)context).getQuestionList();
                    for(Question question : questionList)
                    {
                        if(question.getNumber() == answer.getNumber())
                        {
                            questionData = question;
                        }
                        else if(question instanceof QuestionPartThreeAndFour)
                        {
                            if(((QuestionPartThreeAndFour) question).getNumber1() == answer.getNumber()||
                                ((QuestionPartThreeAndFour) question).getNumber2() == answer.getNumber()||
                                ((QuestionPartThreeAndFour) question).getNumber3() == answer.getNumber())
                            {
                                questionData = question;
                            }
                        }
                    }
                    openDialog(questionData,answer);
                }
            });
        }
    }
    private void openDialog(final Question question, final Answer answer) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(question instanceof QuestionPartOne)
        {
            dialog.setContentView(R.layout.diaglog_result_part_one);
            setUpWindow(dialog);
            addPlaybackControls(dialog,question);
            showScriptPartOne(dialog,(QuestionPartOne)question, answer);
        }
        else if(question instanceof QuestionPartTwo)
        {
            dialog.setContentView(R.layout.dialog_result_part_two);
            setUpWindow(dialog);
            addPlaybackControls(dialog,question);
            showScriptPartTwo(dialog,(QuestionPartTwo)question, answer);
        }else if(question instanceof QuestionPartThreeAndFour)
        {
            dialog.setContentView(R.layout.dialog_result_part_three_and_four);
            setUpWindow(dialog);
            addPlaybackControls(dialog,question);
            showScriptPartThreeAndFour(dialog,(QuestionPartThreeAndFour)question, answer);
        }
        dialog.show();
    }

    private void showScriptPartThreeAndFour(final Dialog dialog,
                                            final QuestionPartThreeAndFour question,
                                            final Answer answer) {
        HashMap<AppCompatButton,String> keyButtonsMap =new HashMap<>();
        TextView txt_question,txt_script;

        keyButtonsMap.put(dialog.findViewById(R.id.btn_key1A), "A");
        keyButtonsMap.put(dialog.findViewById(R.id.btn_key1B), "B");
        keyButtonsMap.put(dialog.findViewById(R.id.btn_key1C), "C");
        keyButtonsMap.put(dialog.findViewById(R.id.btn_key1D), "D");

        txt_question = dialog.findViewById(R.id.txt_question);
        txt_script = dialog.findViewById(R.id.txt_script);
        String script = question.getScript().replace("\\n","\n");
        txt_script.setText(script);
        if(question.getNumber1()==answer.getNumber())
        {
            txt_question.setText(question.getNumber1()+". " + question.getQuestion1());

            for(AppCompatButton key : keyButtonsMap.keySet())
            {
                if(answer.getKeyChosen()!=null)
                {
                    if(!answer.getKeyChosen().equals(answer.getKey()) && Objects.equals(keyButtonsMap.get(key), answer.getKeyChosen()))
                    {
                        key.setBackgroundResource(R.drawable.bg_wrong_answer);
                    }
                }
                if(Objects.equals(keyButtonsMap.get(key), answer.getKey()))
                {
                    key.setBackgroundResource(R.drawable.bg_right_answer);
                }
                if(Objects.equals(keyButtonsMap.get(key), "A"))
                {
                    key.setText(question.getScript_key1A());
                }else if(Objects.equals(keyButtonsMap.get(key), "B"))
                {
                    key.setText(question.getScript_key1B());
                }else if(Objects.equals(keyButtonsMap.get(key), "C"))
                {
                    key.setText(question.getScript_key1C());
                }else if(Objects.equals(keyButtonsMap.get(key), "D"))
                {
                    key.setText(question.getScript_key1D());
                }
            }

        }else if(question.getNumber2()==answer.getNumber())
        {
            txt_question.setText(question.getNumber2()+". " +question.getQuestion2());

            for(AppCompatButton key : keyButtonsMap.keySet())
            {
                if(answer.getKeyChosen()!=null)
                {
                    if(!answer.getKeyChosen().equals(answer.getKey()) && Objects.equals(keyButtonsMap.get(key), answer.getKeyChosen()))
                    {
                        key.setBackgroundResource(R.drawable.bg_wrong_answer);
                    }
                }
                if(Objects.equals(keyButtonsMap.get(key), answer.getKey()))
                {
                    key.setBackgroundResource(R.drawable.bg_right_answer);
                }
                if(Objects.equals(keyButtonsMap.get(key), "A"))
                {
                    key.setText(question.getScript_key2A());
                }else if(Objects.equals(keyButtonsMap.get(key), "B"))
                {
                    key.setText(question.getScript_key2B());
                }else if(Objects.equals(keyButtonsMap.get(key), "C"))
                {
                    key.setText(question.getScript_key2C());
                }else if(Objects.equals(keyButtonsMap.get(key), "D"))
                {
                    key.setText(question.getScript_key2D());
                }
            }
        }else if(question.getNumber3()==answer.getNumber())
        {
            txt_question.setText(question.getNumber3()+". " +question.getQuestion3());

            for(AppCompatButton key : keyButtonsMap.keySet())
            {
                if(answer.getKeyChosen()!=null)
                {
                    if(!answer.getKeyChosen().equals(answer.getKey()) && Objects.equals(keyButtonsMap.get(key), answer.getKeyChosen()))
                    {
                        key.setBackgroundResource(R.drawable.bg_wrong_answer);
                    }
                }
                if(Objects.equals(keyButtonsMap.get(key), answer.getKey()))
                {
                    key.setBackgroundResource(R.drawable.bg_right_answer);
                }
                if(Objects.equals(keyButtonsMap.get(key), "A"))
                {
                    key.setText(question.getScript_key3A());
                }else if(Objects.equals(keyButtonsMap.get(key), "B"))
                {
                    key.setText(question.getScript_key3B());
                }else if(Objects.equals(keyButtonsMap.get(key), "C"))
                {
                    key.setText(question.getScript_key3C());
                }else if(Objects.equals(keyButtonsMap.get(key), "D"))
                {
                    key.setText(question.getScript_key3D());
                }
            }
        }
        txt_question.setPaintFlags(txt_question.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);

    }

    private void showScriptPartTwo(final Dialog dialog,
                                   final QuestionPartTwo question,
                                   final Answer answer) {
        TextView txt_script_part_two = (TextView) dialog.findViewById(R.id.txt_script_part_two);
        TextView txt_number = dialog.findViewById(R.id.txt_number);
        HashMap<AppCompatButton, String> keyButtonsMap = new HashMap<>();
        keyButtonsMap.put(dialog.findViewById(R.id.btn_key1A), "A");
        keyButtonsMap.put(dialog.findViewById(R.id.btn_key1B), "B");
        keyButtonsMap.put(dialog.findViewById(R.id.btn_key1C), "C");
        txt_number.setText(answer.getNumber()+". ");
        txt_script_part_two.setText(question.getScript());
        for(AppCompatButton key : keyButtonsMap.keySet())
        {
            if(answer.getKeyChosen()!=null)
            {
                if(!answer.getKeyChosen().equals(answer.getKey()) && Objects.equals(keyButtonsMap.get(key), answer.getKeyChosen()))
                {
                    key.setBackgroundResource(R.drawable.bg_wrong_answer);
                }
            }
            if(Objects.equals(keyButtonsMap.get(key), answer.getKey()))
            {
                key.setBackgroundResource(R.drawable.bg_right_answer);

            }
            if(Objects.equals(keyButtonsMap.get(key), "A"))
            {
                key.setText(question.getScript_keyA());
            }else if(Objects.equals(keyButtonsMap.get(key), "B"))
            {
                key.setText(question.getScript_keyB());
            }else if(Objects.equals(keyButtonsMap.get(key), "C"))
            {
                key.setText(question.getScript_keyC());
            }
        }
    }

    private void showScriptPartOne(final Dialog dialog,
                                   final QuestionPartOne question,
                                   final Answer answer) {
        ImageView img_part_one = dialog.findViewById(R.id.img_part_one);
        TextView txt_number = dialog.findViewById(R.id.txt_number);
        HashMap<AppCompatButton, String> keyButtonsMap = new HashMap<>();
        keyButtonsMap.put(dialog.findViewById(R.id.btn_key1A), "A");
        keyButtonsMap.put(dialog.findViewById(R.id.btn_key1B), "B");
        keyButtonsMap.put(dialog.findViewById(R.id.btn_key1C), "C");
        keyButtonsMap.put(dialog.findViewById(R.id.btn_key1D), "D");

        Glide.with(context).load(question.getImage_url()).into(img_part_one);
        txt_number.setText(answer.getNumber()+". ");
        for(AppCompatButton key : keyButtonsMap.keySet())
        {
            if(answer.getKeyChosen()!=null)
            {
                if(!answer.getKeyChosen().equals(answer.getKey()) && Objects.equals(keyButtonsMap.get(key), answer.getKeyChosen()))
                    {
                        key.setBackgroundResource(R.drawable.bg_wrong_answer);
                    }
            }
            if(Objects.equals(keyButtonsMap.get(key), answer.getKey()))
            {
                key.setBackgroundResource(R.drawable.bg_right_answer);

            }
            if(Objects.equals(keyButtonsMap.get(key), "A"))
            {
                key.setText(question.getScript_keyA());
            }else if(Objects.equals(keyButtonsMap.get(key), "B"))
            {
                key.setText(question.getScript_keyB());
            }else if(Objects.equals(keyButtonsMap.get(key), "C"))
            {
                key.setText(question.getScript_keyC());
            }else if(Objects.equals(keyButtonsMap.get(key), "D"))
            {
                key.setText(question.getScript_keyD());
            }
        }
    }

    private void setUpWindow(final Dialog dialog) {
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
    }
    private void addPlaybackControls(final Dialog dialog, final Question question)
    {
        Slider slider = dialog.findViewById(R.id.slider);
        AppCompatImageView btn_play = dialog.findViewById(R.id.btn_play);
        AppCompatImageView btn_backward = dialog.findViewById(R.id.btn_backward);
        AppCompatImageView btn_forward = dialog.findViewById(R.id.btn_forward);
        TextView txt_timestamp = dialog.findViewById(R.id.txt_timestamp);
        Handler handler = new Handler();
        final Runnable[] runnable = new Runnable[1];
        slider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                return getTimeString((long)value);
            }
        });

        MediaPlayer mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(question.getAudio_url());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                txt_timestamp.setText(Utils.getTimeString(mediaPlayer.getDuration()));
                slider.setValueTo(mediaPlayer.getDuration());
                mediaPlayer.start();
                updateSliderProgress();
            }

            private void updateSliderProgress() {
                try {
                    int currPos = mediaPlayer.getCurrentPosition();
                    if(currPos<=slider.getValueTo())
                        slider.setValue(currPos);
                    runnable[0] = () -> {
                        try {
                            if(mediaPlayer.isPlaying())
                            {
                                btn_play.setImageResource(R.drawable.ic_pause);
                            }else {
                                btn_play.setImageResource(R.drawable.ic_play);
                            }
                            updateMediaTimeRemaining();
                            updateSliderProgress();
                        }catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    };
                    handler.postDelayed(runnable[0], 200);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void updateMediaTimeRemaining() {
                long timeRemain = mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition();
                txt_timestamp.setText(Utils.getTimeString(timeRemain));
            }
        });
        mediaPlayer.prepareAsync();
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                if(fromUser) {
                    mediaPlayer.seekTo((int) value); }
            }
        });
        btn_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+3000);
            }
        });
        btn_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-3000);
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    btn_play.setImageResource(R.drawable.ic_play);
                    mediaPlayer.pause();
                } else {
                    btn_play.setImageResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                }
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(mediaPlayer!=null)
                {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    handler.removeCallbacks(runnable[0]);
                }
            }
        });
    }

}
