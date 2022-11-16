package com.peterwayne.toeic900.LocalData;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StatusDAO {
    @Query("SELECT * FROM revision")
    List<QuestionReview> getAllReviews();

    @Query("SELECT * FROM done")
    List<QuestionReview> getAllDone();

    @Query("SELECT * FROM revision WHERE revision.id LIKE :ID")
    List<QuestionReview> getQuestionReviewById(final String ID);

    @Query("SELECT * FROM done WHERE done.id LIKE :ID")
    List<QuestionReview> getQuestionDoneById(final String ID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDoneQuestion(QuestionDone question);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReviewQuestion(QuestionReview question);
    @Delete
    void deleteReviewQuestion(QuestionReview question);
}
