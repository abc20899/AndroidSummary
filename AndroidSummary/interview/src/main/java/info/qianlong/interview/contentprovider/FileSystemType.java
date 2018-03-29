package info.qianlong.interview.contentprovider;

/**
 * Created by junzhao on 2017/12/19.
 */

public enum FileSystemType {

    photo,
    music,
    video,
    text,
    zip;

    public static FileSystemType getFileTypeByOrdinal(int ordinal) {
        for (FileSystemType type : values()) {
            if (type.ordinal() == ordinal) {
                return type;
            }
        }
        return photo;
    }
}
