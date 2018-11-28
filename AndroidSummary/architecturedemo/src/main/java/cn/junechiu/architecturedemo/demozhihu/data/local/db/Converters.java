package cn.junechiu.architecturedemo.demozhihu.data.local.db;


import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static String fromTimestamp(String[] arrays) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : arrays) {
            stringBuilder.append(s).append(",");
        }
        int length = stringBuilder.toString().length();
        if (length > 0) {
            stringBuilder = stringBuilder.deleteCharAt(length - 1);
        }
        return stringBuilder.toString();
    }

    @TypeConverter
    public static String[] dateToTimestamp(String string) {
        return string.split(",");
    }
}