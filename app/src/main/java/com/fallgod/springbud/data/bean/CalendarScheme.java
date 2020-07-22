package com.fallgod.springbud.data.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Author: JackPan
 * Date: 2020-07-21
 * Time: 09:06
 * Description:
 */
@Entity
public class CalendarScheme {

    @PrimaryKey
    public int cId;

    @ColumnInfo(name = "year")
    public int year;

    @ColumnInfo(name = "month")
    public int month;

    @ColumnInfo(name = "day")
    public int day;

    @ColumnInfo(name = "color")
    public int color;

    @ColumnInfo(name = "text")
    public String text;
}
