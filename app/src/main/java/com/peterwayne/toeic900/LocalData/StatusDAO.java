package com.peterwayne.toeic900.LocalData;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.peterwayne.toeic900.Model.Question;

import java.util.List;

@Dao
public interface StatusDAO {
    //Query data
    @Query("SELECT * FROM statusPartOne WHERE statusPartOne.done = 1 AND statusPartOne.id LIKE :id LIMIT 1")
    QuestionPartOneStatus getDonePartOneById(final String id);

    @Query("SELECT * FROM statusPartTwo WHERE statusPartTwo.done = 1 AND statusPartTwo.id LIKE :id LIMIT 1")
    QuestionPartTwoStatus getDonePartTwoById(final String id);

    @Query("SELECT * FROM statusPartThreeAndFour WHERE statusPartThreeAndFour.done = 1 AND statusPartThreeAndFour.id LIKE :id LIMIT 1")
    QuestionPartThreeAndFourStatus getDonePartThreeAndFourById(final String id);

    @Query("SELECT * FROM statusPartOne WHERE statusPartOne.toReview = 1 AND statusPartOne.id LIKE :id LIMIT 1")
    QuestionPartOneStatus getReviewPartOneById(final String id);

    @Query("SELECT * FROM statusPartTwo WHERE statusPartTwo.toReview = 1 AND statusPartTwo.id LIKE :id LIMIT 1")
    QuestionPartTwoStatus getReviewPartTwoById(final String id);

    @Query("SELECT * FROM statusPartThreeAndFour WHERE statusPartThreeAndFour.toReview = 1 AND statusPartThreeAndFour.id LIKE :id LIMIT 1")
    QuestionPartThreeAndFourStatus getReviewPartThreeAndFourById(final String id);



    @Query("SELECT * FROM statusPartOne WHERE statusPartOne.toReview = 1")
    List<QuestionPartOneStatus> getReviewPartOne();

    @Query("SELECT * FROM statusPartTwo WHERE statusPartTwo.toReview = 1")
    List<QuestionPartTwoStatus> getReviewPartTwo();

    @Query("SELECT * FROM statusPartThreeAndFour WHERE statusPartThreeAndFour.toReview = 1")
    List<QuestionPartThreeAndFourStatus> getReviewPartThreeAndFour();

    //Insert data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDoneQuestion(QuestionPartOneStatus question);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDoneQuestion(QuestionPartTwoStatus question);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDoneQuestion(QuestionPartThreeAndFourStatus question);


    //Update data
    @Update
    void addToReview(QuestionPartOneStatus question);
    @Update
    void addToReview(QuestionPartTwoStatus question);
    @Update
    void addToReview(QuestionPartThreeAndFourStatus question);
}
