package info.qianlong.interview.contentprovider;

/**
 * Created by junzhao on 2017/12/19.
 */

public class FileItem {

    private String mFileId;

    private String mFilePath;

    private String mFileName;

    public FileItem(String mFileId, String mFilePath, String mFileName) {
        this.mFileId = mFileId;
        this.mFilePath = mFilePath;
        this.mFileName = mFileName;
    }

    public String getmFileId() {
        return mFileId;
    }

    public void setmFileId(String mFileId) {
        this.mFileId = mFileId;
    }

    public String getmFilePath() {
        return mFilePath;
    }

    public void setmFilePath(String mFilePath) {
        this.mFilePath = mFilePath;
    }

    public String getmFileName() {
        return mFileName;
    }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
    }
}

