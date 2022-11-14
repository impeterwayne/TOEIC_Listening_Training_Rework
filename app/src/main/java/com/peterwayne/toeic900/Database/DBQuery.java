package com.peterwayne.toeic900.Database;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.peterwayne.toeic900.Model.Question;
import com.peterwayne.toeic900.Model.QuestionPartOne;
import com.peterwayne.toeic900.Model.QuestionPartThreeAndFour;
import com.peterwayne.toeic900.Model.QuestionPartTwo;

import java.util.ArrayList;
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
                Log.d("TestListSize", String.valueOf(testList.size()));
            }
        });
    }
    public static void loadDataPartOne(List<String> listTest, iTrainingCallback<QuestionPartOne> dataCallback) {

        List<QuestionPartOne> questionList = new ArrayList<>();
        for(String test : listTest)
        {
            db.collection("Tests").document(test).collection("Part1").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    int count=0;
                    for(DocumentSnapshot doc: queryDocumentSnapshots)
                    {
                        questionList.add(doc.toObject(QuestionPartOne.class));
                        count++;
                        if(count==5) break;
                    }
                    dataCallback.onCallBack(questionList);
                }
            });
        }
    }
    public static void loadDataPartTwo(final List<String> listTest,
                                       iTrainingCallback<QuestionPartTwo> dataCallback) {
        List<QuestionPartTwo> questionList = new ArrayList<>();
        for(String testName : listTest)
        {
            db.collection("Tests").document(testName).collection("Part2").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    int count=0;
                    for(DocumentSnapshot doc: queryDocumentSnapshots)
                    {
                        questionList.add(doc.toObject(QuestionPartTwo.class));
                        count++;
                        if(count==5)break;
                    }
                    dataCallback.onCallBack(questionList);
                }
            });
        }
    }
    public static void loadDataPartThree(final List<String> listTest,
                                         iTrainingCallback<QuestionPartThreeAndFour> dataCallback) {
        List<QuestionPartThreeAndFour> questionList = new ArrayList<>();
        for(final String testName : listTest) {
            db.collection("Tests").document(testName).collection("Part3").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    int count=0;
                    for(DocumentSnapshot doc: queryDocumentSnapshots)
                    {
                        questionList.add(doc.toObject(QuestionPartThreeAndFour.class));
                        count++;
                        if(count==5)break;
                    }
                    dataCallback.onCallBack(questionList);
                }
            });
        }
    }
    public static void loadDataPartFour(final List<String> listTest,
                                         final iTrainingCallback<QuestionPartThreeAndFour> dataCallback) {
        final List<QuestionPartThreeAndFour> questionList = new ArrayList<>();
        for(final String testName : listTest) {
            db.collection("Tests").document(testName).collection("Part4").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    int count=0;
                    for(DocumentSnapshot doc: queryDocumentSnapshots)
                    {
                        questionList.add(doc.toObject(QuestionPartThreeAndFour.class));
                        count++;
                        if(count==5)break;
                    }
                    dataCallback.onCallBack(questionList);
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
    public interface iTestNameCallback {
        void onCallBack(List<String> testList);
    }
    public interface iTrainingCallback<T extends Question>
    {
         void onCallBack(List<T> data);
    }
    public interface iAudioCallback
    {
        void onCallBack(String audio_url);
    }
    public interface iTestQuestionCallback
    {
        void onCallBack(List<Question> questionList);
    }
}
