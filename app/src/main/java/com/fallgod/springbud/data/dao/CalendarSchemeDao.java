package com.fallgod.springbud.data.dao;

import com.fallgod.springbud.data.bean.CalendarScheme;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

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

    @Insert
    void insertAll(CalendarScheme... calendarSchemes);

    @Insert
    void insertAll(List<CalendarScheme> list);

    @Delete
    void delete(CalendarScheme calendarScheme);
}
