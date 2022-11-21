package com.peterwayne.toeic900.Database;

import static com.peterwayne.toeic900.Utils.Utils.NUMBER_QUESTION_TRAINING;
import static com.peterwayne.toeic900.Utils.Utils.getDayOfWeek;
import static com.peterwayne.toeic900.Utils.Utils.getFirebaseUser;

import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.peterwayne.toeic900.LocalData.RoomDbManager;
import com.peterwayne.toeic900.Model.Question;
import com.peterwayne.toeic900.Model.QuestionPartOne;
import com.peterwayne.toeic900.Model.QuestionPartThreeAndFour;
import com.peterwayne.toeic900.Model.QuestionPartTwo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBQuery {
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void loadTestNameList(iTestNameCallback callback) {
        List<String> testList = new ArrayList<>();
        db.collection("Tests").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot doc : queryDocumentSnapshots) {
                    testList.add(doc.get("name", String.class));
                }
                callback.onCallBack(testList);
            }
        });
    }

    public static void loadDataPartOne(final Context context,
                                        final List<String> listTest,
                                       final iTrainingCallback<QuestionPartOne> dataCallback) {

        List<QuestionPartOne> questionList = new ArrayList<>();
        for(String test : listTest)
        {
            db.collection("Tests").document(test).collection("Part1").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(DocumentSnapshot doc: queryDocumentSnapshots)
                    {
                        QuestionPartOne question = doc.toObject(QuestionPartOne.class);
                        if(RoomDbManager.getInstance(context).statusDAO().getDonePartOneById(question.getId())==null)
                        {
                            questionList.add(question);
                            if(questionList.size() == NUMBER_QUESTION_TRAINING) break;
                        }
                    }
                    if(questionList.size()<=NUMBER_QUESTION_TRAINING) dataCallback.onCallBack(questionList);
                }
            });
        }
    }
    public static void loadDataPartTwo(final Context context,
                                       final List<String> listTest,
                                       final iTrainingCallback<QuestionPartTwo> dataCallback) {
        List<QuestionPartTwo> questionList = new ArrayList<>();
        for(String testName : listTest)
        {

            db.collection("Tests").document(testName).collection("Part2").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(DocumentSnapshot doc: queryDocumentSnapshots)
                    {
                        QuestionPartTwo question = doc.toObject(QuestionPartTwo.class);
                        if(RoomDbManager.getInstance(context).statusDAO().getDonePartTwoById(question.getId())==null)
                        {
                            questionList.add(question);
                        }
                        if(questionList.size()==NUMBER_QUESTION_TRAINING) break;
                    }
                    if(questionList.size()<=NUMBER_QUESTION_TRAINING) dataCallback.onCallBack(questionList);
                }
            });
        }
    }
    public static void loadDataPartThree(final Context context,
                                         final List<String> listTest,
                                         final iTrainingCallback<QuestionPartThreeAndFour> dataCallback) {
        List<QuestionPartThreeAndFour> questionList = new ArrayList<>();
        for(final String testName : listTest) {
            db.collection("Tests").document(testName).collection("Part3").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    for(DocumentSnapshot doc: queryDocumentSnapshots)
                    {
                        QuestionPartThreeAndFour question = doc.toObject(QuestionPartThreeAndFour.class);
                        if(RoomDbManager.getInstance(context).statusDAO().getDonePartThreeAndFourById(question.getId())==null)
                        {
                            questionList.add(question);
                        }
                        if(questionList.size()==NUMBER_QUESTION_TRAINING) break;
                    }
                    if(questionList.size()<=NUMBER_QUESTION_TRAINING) dataCallback.onCallBack(questionList);
                }
            });
        }
    }
    public static void loadDataPartFour(final Context context,
                                        final List<String> listTest,
                                        final iTrainingCallback<QuestionPartThreeAndFour> dataCallback) {
        final List<QuestionPartThreeAndFour> questionList = new ArrayList<>();
        for(final String testName : listTest) {
            db.collection("Tests").document(testName).collection("Part4").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(DocumentSnapshot doc: queryDocumentSnapshots)
                    {
                        QuestionPartThreeAndFour question = doc.toObject(QuestionPartThreeAndFour.class);
                        if(RoomDbManager.getInstance(context).statusDAO().getDonePartThreeAndFourById(question.getId())==null)
                        {
                            questionList.add(question);
                        }
                        if(questionList.size()==NUMBER_QUESTION_TRAINING) break;
                    }
                    if(questionList.size()<=NUMBER_QUESTION_TRAINING)dataCallback.onCallBack(questionList);
                }
            });
        }
    }
    public static void loadTestAudio(final String testName, iAudioCallback callback)
    {
        db.collection("Tests").document(testName).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String audio_url = (String)documentSnapshot.get("audio_url");
                callback.onCallBack(audio_url);
            }
        });
    }
    public static void loadTestQuestions(final String testName, iTestQuestionCallback callback) {
        final List<Question> questionList = new ArrayList<>();
        db.collection("Tests").document(testName).collection("Part1").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot doc: queryDocumentSnapshots)
                {
                    questionList.add(doc.toObject(QuestionPartOne.class));
                }
                db.collection("Tests").document(testName).collection("Part2").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot doc: queryDocumentSnapshots)
                        {
                            questionList.add(doc.toObject(QuestionPartTwo.class));
                        }
                        db.collection("Tests").document(testName).collection("Part3").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(DocumentSnapshot doc: queryDocumentSnapshots)
                                {
                                    questionList.add(doc.toObject(QuestionPartThreeAndFour.class));
                                }
                                db.collection("Tests").document(testName).collection("Part4").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for(DocumentSnapshot doc: queryDocumentSnapshots)
                                        {
                                            questionList.add(doc.toObject(QuestionPartThreeAndFour.class));
                                        }
                                        callback.onCallBack(questionList);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
    public static void loadStatisticData(iStatisticsCallback callback)
    {

        db.collection("User").document(getFirebaseUser())
                .collection("Statistic").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Integer> data = new ArrayList<>();
                for(DocumentSnapshot doc : queryDocumentSnapshots)
                {
                    if(doc.get("total",Integer.class)==null)
                    {
                        data.add(0);
                    }else
                    {
                        data.add(doc.get("total",Integer.class));
                    }
                }
                data = sortStatisticDataSet(data);
                callback.onCallBack(data);
            }
        });
    }
    private static List<Integer> sortStatisticDataSet(final List<Integer> data)
    {
        int step = data.size() - getDayOfWeek();
        Integer[] arr = new Integer[data.size()];
        for (int i = 0; i < arr.length; i++)
        {
            int temp = i-step;
            if(temp <0) temp = temp+7;
            arr[i] = data.get((temp)%data.size());
        }
        return Arrays.asList(arr);
    }

    public interface iTestNameCallback {
        void onCallBack(List<String> testNameList);
    }
    public interface iTrainingCallback<T extends Question>
    {
         void onCallBack(List<T> questionList);
    }
    public interface iAudioCallback
    {
        void onCallBack(String audio_url);
    }
    public interface iTestQuestionCallback
    {
        void onCallBack(List<Question> questionList);
    }
    public interface iStatisticsCallback
    {
        void onCallBack(List<Integer> dataStatistic);
    }
}
