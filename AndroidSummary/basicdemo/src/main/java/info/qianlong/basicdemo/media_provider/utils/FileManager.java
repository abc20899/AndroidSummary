package info.qianlong.basicdemo.media_provider.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import info.qianlong.basicdemo.media_provider.bean.AppInfo;
import info.qianlong.basicdemo.media_provider.bean.FileBean;
import info.qianlong.basicdemo.media_provider.bean.ImgFolderBean;
import info.qianlong.basicdemo.media_provider.bean.Music;
import info.qianlong.basicdemo.media_provider.bean.Video;

/**
 * @描述 文件管理者, 可以获取本机的各种文件
 */
public class FileManager {

    private Context mContext;

    public ContentResolver mContentResolver;

    private FileManager() {
    }

    public void init(Context mContext) {
        this.mContext = mContext;
        mContentResolver = mContext.getContentResolver();
    }

    private static final class FileManagerHolder {
        private static final FileManager instance = new FileManager();
    }

    public static FileManager getInstance() {
        return FileManagerHolder.instance;
    }

    //获取音乐列表
    public List<Music> getMusics() {
        List<Music> musics = new ArrayList<>();
        Cursor c = null;
        try {
            c = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                    MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            while (c.moveToNext()) {
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));// 路径
                if (!new File(path).exists()) {
                    continue;
                }
                String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)); // 歌曲名
                String album = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)); // 专辑
                String artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)); // 作者
                long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));// 大小
                int duration = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));// 时长
                int time = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));// 歌曲的id
                // int albumId = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
                Music music = new Music(name, path, album, artist, size, duration, PinyinUtils.getPinyin(name));
                musics.add(music);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return musics;
    }

    //获取视频列表
    public List<Video> getVideos() {
        List<Video> videos = new ArrayList<>();
        Cursor c = null;
        try {
            // String[] mediaColumns = { "_id", "_data", "_display_name",
            // "_size", "date_modified", "duration", "resolution" };
            c = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
            while (c.moveToNext()) {
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));// 路径
                if (!new File(path).exists()) {
                    continue;
                }
                int id = c.getInt(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID));// 视频的id
                String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 视频名称
                String resolution = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION)); //分辨率
                long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));// 大小
                long duration = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));// 时长
                long date = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED));//修改时间
                Video video = new Video(id, path, name, resolution, size, date, duration);
                videos.add(video);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return videos;
    }

    // 获取视频缩略图
    public Bitmap getVideoThumbnail(int id) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bitmap = MediaStore.Video.Thumbnails.getThumbnail(mContentResolver, id, MediaStore.Images.Thumbnails.MICRO_KIND, options);
        return bitmap;
    }

    //通过文件类型得到相应文件的集合
    public List<FileBean> getFilesByType(int fileType) {
        List<FileBean> files = new ArrayList<FileBean>();
        // 扫描files文件库
        Cursor c = null;
        try {
            c = mContentResolver.query(MediaStore.Files.getContentUri("external"), new String[]{"_id", "_data", "_size"}, null, null, null);
            int dataindex = c.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int sizeindex = c.getColumnIndex(MediaStore.Files.FileColumns.SIZE);
            while (c.moveToNext()) {
                String path = c.getString(dataindex);
                if (FileUtils.getFileType(path) == fileType) {
                    if (!FileUtils.isExists(path)) {
                        continue;
                    }
                    long size = c.getLong(sizeindex);
                    FileBean fileBean = new FileBean(path, FileUtils.getFileIconByPath(path));
                    files.add(fileBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return files;
    }

    //得到图片文件夹集合
    public List<ImgFolderBean> getImageFolders() {
        List<ImgFolderBean> folders = new ArrayList();
        Cursor c = null;
        try {
            c = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                    MediaStore.Images.Media.MIME_TYPE + "= ? or " + MediaStore.Images.Media.MIME_TYPE + "= ?",
                    new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
            List<String> mDirs = new ArrayList<String>();//用于保存已经添加过的文件夹目录
            while (c.moveToNext()) {
                String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));// 路径
                File parentFile = new File(path).getParentFile();
                if (parentFile == null)
                    continue;
                String dir = parentFile.getAbsolutePath();
                if (mDirs.contains(dir))//如果已经添加过
                    continue;
                mDirs.add(dir);//添加到保存目录的集合中
                ImgFolderBean folderBean = new ImgFolderBean();
                int lastIndex = dir.lastIndexOf("/");
                folderBean.name = dir.substring(lastIndex + 1);
                folderBean.fistImgPath = path;
                folderBean.dir = dir;
                if (parentFile.list() == null)
                    continue;
                int count = parentFile.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        if (filename.endsWith(".webp") || filename.endsWith(".jpeg") || filename.endsWith(".jpg") || filename.endsWith(".png")) {
                            return true;
                        }
                        return false;
                    }
                }).length;
                folderBean.count = count;
                folders.add(folderBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return folders;
    }

    //通过图片文件夹的路径获取该目录下的图片
    public List<String> getImgListByDir(String dir) {
        List<String> imgPaths = new ArrayList<>();
        File directory = new File(dir);
        if (directory == null || !directory.exists()) {
            return imgPaths;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            String path = file.getAbsolutePath();
            if (FileUtils.isPicFile(path)) {
                imgPaths.add(path);
            }
        }
        return imgPaths;
    }

    //获取已安装apk的列表
    public List<AppInfo> getAppInfos() {
        List<AppInfo> appInfos = new ArrayList();
        //获取到包的管理者
        PackageManager packageManager = mContext.getPackageManager();
        //获得所有的安装包
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);

        //遍历每个安装包，获取对应的信息
        for (PackageInfo packageInfo : installedPackages) {

            AppInfo appInfo = new AppInfo();

            appInfo.applicationInfo = packageInfo.applicationInfo;
            appInfo.versionCode = packageInfo.getLongVersionCode();

            //得到icon
            Drawable drawable = packageInfo.applicationInfo.loadIcon(packageManager);
            appInfo.icon = drawable;

            //得到程序的名字
            String apkName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            appInfo.apkName = apkName;

            //得到程序的包名
            String packageName = packageInfo.packageName;
            appInfo.apkPackageName = packageName;

            //得到程序的资源文件夹
            String sourceDir = packageInfo.applicationInfo.sourceDir;
            File file = new File(sourceDir);
            //得到apk的大小
            long size = file.length();
            appInfo.apkSize = size;
            //获取到安装应用程序的标记
            int flags = packageInfo.applicationInfo.flags;
            if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                appInfo.isUserApp = false; //表示系统app
            } else {
                appInfo.isUserApp = true; //表示用户app
            }

            if ((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
                appInfo.isRom = false; //表示在sd卡
            } else {
                appInfo.isRom = true; //表示内存
            }
            appInfos.add(appInfo);
        }
        return appInfos;
    }

}
