package com.fallgod.springbud.data.dao;

import com.fallgod.springbud.data.bean.CalendarScheme;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Author: JackPan
 * Date: 2020-07-21
 * Time: 09:11
 * Description:
 */
@Dao
public interface CalendarSchemeDao {

    @Query("SELECT * FROM CalendarScheme")
    List<CalendarScheme> getAll();

    @Query("SELECT * FROM CalendarScheme WHERE cId IN (:schemeIds)")
    List<CalendarScheme> loadAllByIds(int[] schemeIds);

    @Query("SELECT * FROM CalendarScheme ORDER BY cId DESC LIMIT 0,1")
    CalendarScheme getLatest();

    @Insert
    void insertAll(CalendarScheme... calendarSchemes);

    @Insert
    void insertAll(List<CalendarScheme> list);

    @Update
    void update(CalendarScheme... calendarScheme);

    @Delete
    void delete(CalendarScheme... calendarScheme);
}
