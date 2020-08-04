package com.fallgod.springbud;

/**
 * Author: JackPan
 * Date: 2020-07-22
 * Time: 14:16
 * Description:常量配置类
 */
public class Constants {

    /**
     * 考勤选择事件
     */
    public static final int ATTENDANCE_ON_WORK = 1;    //上班
    public static final int ATTENDANCE_OFF_WORK = 2;    //下班
    public static final int ATTENDANCE_WORK = 3;    //打卡（上下班均已打卡）
    public static final int ATTENDANCE_HOLIDAY = 4;    //休息
    public static final int ATTENDANCE_LEAVE = 5;    //休假（年假等带薪假）
    public static final int ATTENDANCE_SOMETHING_LEAVE = 6;    //事假
    public static final int ATTENDANCE_SICK_LEAVE = 7;    //病假

    /**
     * 历史考勤json数据文件名
     */
    public static final String ATTENDANCE_JSON_NAME = "history_attendance.json";

}
