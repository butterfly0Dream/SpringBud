package com.fallgod.springbud.data;

import com.fallgod.springbud.App;
import com.fallgod.springbud.data.bean.CalendarScheme;
import com.fallgod.springbud.data.dao.CalendarSchemeDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Author: JackPan
 * Date: 2020-07-21
 * Time: 09:20
 * Description:
 */
@Database(entities = {CalendarScheme.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase mInstance;

    public abstract CalendarSchemeDao calendarSchemeDao();

    //数据库的实例化是很昂贵的，尽量使用单例模式
    public static AppDatabase getInstance(){
        if (mInstance == null){
            synchronized (AppDatabase.class){
                if (mInstance == null){
                    mInstance = Room.databaseBuilder(App.getInstance(),
                            AppDatabase.class,"db_spring").build();
                }
            }
        }
        return mInstance;
    }
}
