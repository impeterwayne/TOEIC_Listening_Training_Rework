package com.peterwayne.toeic900.Database;

import static com.peterwayne.toeic900.Utils.Utils.NUMBER_QUESTION_TRAINING;
import static com.peterwayne.toeic900.Utils.Utils.getCurrentDate;
import static com.peterwayne.toeic900.Utils.Utils.getCurrentDayOfWeek;
import static com.peterwayne.toeic900.Utils.Utils.getFirebaseUser;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.peterwayne.toeic900.LocalData.RoomDbManager;
import com.peterwayne.toeic900.Model.Question;
import com.peterwayne.toeic900.Model.QuestionPartOne;
import com.peterwayne.toeic900.Model.QuestionPartThreeAndFour;
import com.peterwayne.toeic900.Model.QuestionPartTwo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
                    String dateToQuery = findDateToQuery(Integer.parseInt(doc.getId()));
                    Log.d("date", dateToQuery);
                    if(doc.contains(dateToQuery))
                    {
                        data.add(doc.get(dateToQuery,Integer.class));
                    }else
                    {
                        data.add(0);
                    }
                }
                data = sortStatisticDataSet(data);
                callback.onCallBack(data);
            }

            private String findDateToQuery(final int dayOfWeek) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date currentDate = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(currentDate);
                int distanceToPresent;
                if(getCurrentDayOfWeek()>=dayOfWeek)
                {
                    distanceToPresent = getCurrentDayOfWeek()-dayOfWeek;
                }else
                {
                    distanceToPresent = 7-dayOfWeek + getCurrentDayOfWeek();
                }
                c.add(Calendar.DATE, -distanceToPresent);
                return dateFormat.format(c.getTime());
            }
        });
    }
    private static List<Integer> sortStatisticDataSet(final List<Integer> data)
    {
        int step = data.size() - getCurrentDayOfWeek();
        Integer[] arr = new Integer[data.size()];
        for (int i = 0; i < arr.length; i++)
        {
            int temp = i-step;
            if(temp <0) temp = temp+7;
            arr[i] = data.get((temp)%data.size());
        }
        return Arrays.asList(arr);
    }
    public static void updatePracticeStatistic()
    {
        DocumentReference ref = db.collection("User").document(getFirebaseUser())
                .collection("Statistic").document(String.valueOf(getCurrentDayOfWeek()));
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.contains(getCurrentDate()))
                {
                    Integer newData = documentSnapshot.get(getCurrentDate(),Integer.class)+1;
                    ref.update(getCurrentDate(),newData);
                }else
                {
                    HashMap<String, Integer> value = new HashMap<String, Integer>();
                    value.put(getCurrentDate(), 1);
                    ref.set(value);
                }
            }
        });
    }

    public static void updateTotalPractice() {
        DocumentReference ref = db.collection("User").document(getFirebaseUser());
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.get("practice")==null)
                {
                    ref.set(new HashMap<String,Integer>().put("practice",1));
                }else
                {
                    Integer increment = documentSnapshot.get("practice", Integer.class)+1;
                    ref.update("practice", increment);
                }
            }
        });
    }
    public static void updateTarget(final int target){
        DocumentReference ref = db.collection("User").document(getFirebaseUser());
        HashMap<String, Object> value = new HashMap<>();
        value.put("target", target);
        ref.update(value);
    }

    public static void updateTestStatistics() {
        DocumentReference ref = db.collection("User").document(getFirebaseUser());
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.get("test")==null)
                {
                    ref.set(new HashMap<String,Integer>().put("test",1));
                }else
                {
                    Integer increment = documentSnapshot.get("test", Integer.class)+1;
                    ref.update("test", increment);
                }
            }
        });
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
