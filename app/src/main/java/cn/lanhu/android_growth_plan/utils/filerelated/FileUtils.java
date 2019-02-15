package cn.lanhu.android_growth_plan.utils.filerelated;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

import cn.lanhu.android_growth_plan.gaiban.app.config.AppConfig;
import cn.lanhu.android_growth_plan.gaiban.app.config.RxApplication;
import cn.lanhu.android_growth_plan.utils.encryptrelated.Base64Api;

/**
 * 文件操作工具类
 */
public class FileUtils {

    /**
     * 保存头像
     *
     * @param b
     */
    public static void saveAvatar(Bitmap b) {
        if (b == null) {
            return;
        }
        // 创建文件夹
        String fileName = "avatar.jpg";
        String avatarPath = FileUtils.getAvatarPath();
        File avatarPathfile = new File(avatarPath);
        if (!avatarPathfile.exists()) {
            avatarPathfile.mkdirs();
        }
        // 写入文件
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        File avatarFile = new File(avatarPathfile, fileName);
        if (avatarFile.exists()) {
            avatarFile.delete();
        }
        FileUtils.storeFileByInStream(isBm, avatarFile);
    }

    /**
     * 保存图片
     *
     * @param b
     */
    public static void saveImage(Bitmap b, String fileName) {
        if (b == null) {
            return;
        }
        // 创建文件夹
        //        String fileName = "upload.jpg";
        String imagePath = FileUtils.getImagePath() + "tianqi/";
        File imagePathfile = new File(imagePath);
        if (!imagePathfile.exists()) {
            imagePathfile.mkdirs();
        }
        // 写入文件
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        File imageFile = new File(imagePathfile, fileName);
        if (imageFile.exists()) {
            imageFile.delete();
        }
        FileUtils.storeFileByInStream(isBm, imageFile);
    }

    public static boolean imgIsExistence(String imgCount) {
        String imagePath = FileUtils.getImagePath() + "tianqi/";
        File imageFile = new File(imagePath, imgCount + ".jpg");
        return imageFile.exists();
    }

    public static void deleteAvata() {
        String fileName = "avatar.jpg";
        String avatarPath = FileUtils.getAvatarPath();
        File avatarFile = new File(new File(avatarPath), fileName);
        if (avatarFile.exists()) {
            if (avatarFile.delete()) {
                Logger.e("头像", "删除成功");
            }
        }
    }

    /**
     * 加载头像 如果为空 默认 返回程序icon
     */
    public static Bitmap loadAvator() {
        return FileUtils.path2Bitmap(FileUtils.getAvatarPath()
            + "avatar.jpg");
    }

    /**
     * 获取背景图片
     */
    public static Bitmap loadBackground(String imgCount) {
        return FileUtils.path2Bitmap(FileUtils.getImagePath()
            + "tianqi/" + imgCount + ".jpg");
    }


    /**
     * 压缩图片并保存
     *
     * @param bmp
     * @param
     */
    public static void compressBmpToFile(Bitmap bmp) {
        if (bmp == null) {
            return;
        }
        // 创建文件夹
        String fileName = "upload.jpg";
        String imagePath = AppConfig.DEFAULT_SAVE_IMAGE_PATH;

        File imagePathfile = new File(imagePath);
        if (!imagePathfile.exists()) {
            imagePathfile.mkdirs();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 80;//个人喜欢从80开始,
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            options -= 10;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        File file = new File(imagePathfile, fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取系统安装路径
     */
    public static String getSystemPackagePath() {
        Context c = RxApplication.getInstance().getmContext();
        String path = c.getApplicationContext().getFilesDir()
            .getAbsolutePath();
        return path;
    }

    /**
     * 获取头像路径
     */

    public static String getAvatarPath() {
        // 头像保存路径
        return AppConfig.DEFAULT_SAVE_AVATAR_PATH;
    }

    /**
     * 获取图片路径
     *
     * @return
     */
    public static String getImagePath() {
        // 头像保存路径
        return AppConfig.DEFAULT_SAVE_IMAGE_PATH;
    }


    public static String getPathFromUri(Context context, Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};

        // 好像是android多媒体数据库的封装接口，具体的看Android文档
        Cursor cursor = context.getContentResolver().query(uri, proj, null,
            null, null);
        // 按我个人理解 这个是获得用户选择的图片的索引值
        int column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
        cursor.moveToFirst();
        // 最后根据索引值获取图片路径
        String path = cursor.getString(column_index);
        return path;
    }

    public static String getImagePathFromUri(Context context, Uri fileUrl) {
        String fileName = null;
        Uri filePathUri = fileUrl;
        if (fileUrl != null) {
            if (fileUrl.getScheme().toString().compareTo("content") == 0) {
                // content://开头的uri
                Cursor cursor = context.getContentResolver().query(fileUrl,
                    null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    fileName = cursor.getString(column_index); // 取出文件路径

                    // Android 4.1 更改了SD的目录，sdcard映射到/storage/sdcard0
                    if (!fileName.startsWith("/storage")
                        && !fileName.startsWith("/mnt")) {
                        // 检查是否有”/mnt“前缀
                        fileName = "/mnt" + fileName;
                    }
                    cursor.close();
                }
            } else if (fileUrl.getScheme().compareTo("file") == 0) { // file:///开头的uri

                fileName = filePathUri.toString();// 替换file://
                fileName = filePathUri.toString().replace("file://", "");
                int index = fileName.indexOf("/sdcard");
                fileName = index == -1 ? fileName : fileName.substring(index);

                // if (!fileName.startsWith("/mnt")) {
                // // 加上"/mnt"头
                // fileName = "/mnt" + fileName;
                // }
            }
        }
        return fileName;
    }

    /**
     * 图片路径转为 bitmap
     *
     * @param pathString
     * @return
     */
    public static Bitmap path2Bitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 将input写到file文件中
     *
     * @param input
     * @return
     */
    public static void storeFileByInStream(InputStream input, File file) {
        if (null == input) {
            return;
        }
        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (null != output) {
                    output.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            } finally {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 压缩byte[]
     *
     * @param data
     * @return
     */
    public static byte[] Zip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /**
     * 压缩
     *
     * @param data 待压缩数据
     * @return byte[] 压缩后的数据
     */
    public static byte[] compress(byte[] data) {
        byte[] output = new byte[0];

        Deflater compresser = new Deflater();

        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        compresser.end();
        return output;
    }

    /**
     * 解压缩
     *
     * @param data 待压缩的数据
     * @return byte[] 解压缩后的数据
     */
    public static byte[] decompress(byte[] data) {
        byte[] output = new byte[0];

        Inflater decompresser = new Inflater();
        decompresser.reset();
        decompresser.setInput(data);

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        decompresser.end();
        return output;
    }

    /**
     * 将input写到file文件中
     *
     * @param
     * @return
     */
    public static void storeFileByInStream(byte[] byteData, File file) {
        if (null == byteData) {
            return;
        }
        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
            output.write(byteData, 0, byteData.length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (null != output) {
                    output.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static byte[] getBytes2(String filePath) {
        byte[] data = null;
        byte[] buffer = new byte[1024];
        int size = 0;
        try {
            FileInputStream in = new FileInputStream(filePath);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            System.out.println("bytes available:" + in.available());
            while ((size = in.read(buffer)) != -1) {
                out.write(buffer, 0, size);
            }
            in.close();
            data = out.toByteArray();
            out.flush();
            out.close();
            System.out.println("bytes size got is:" + data.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 将file文件内容读到byte[]数组中
     *
     * @param file
     * @return
     */
    public static byte[] toByteArray(File file) {
        if (!file.exists()) {
            return null;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream(
            (int) file.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 保存2进制数组到sdcard
     *
     * @param dir
     * @param fileName
     * @param datas
     * @return
     */
    public static final boolean saveBytestoSdcard(File dir, String fileName,
                                                  byte[] datas) {
        boolean sResult = false;
        if (!dir.exists()) {
            return false;
        }
        File writeFile = new File(dir, fileName);
        sResult = saveBytestoSdcard(writeFile, datas);
        return sResult;
    }

    public static final boolean saveBytestoSdcard(File file, byte[] datas) {
        boolean sResult = false;

        BufferedOutputStream bos = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            bos = new BufferedOutputStream(new FileOutputStream(file), 1024);
            bos.write(datas);
            bos.flush();
            bos.close();
            sResult = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sResult;
    }

    public static void stortFileByBytes(byte[] fileContent, File tempPdfFile) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(tempPdfFile, true);
            outputStream.write(fileContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 读每一行文本内容
     *
     * @param file
     * @return
     */
    public static List<String> readFileEveryLine(File file) {
        if (!file.exists() || file.length() < 1) {
            return null;
        }
        List<String> resultList = new ArrayList<String>();
        FileReader fileReader = null;
        BufferedReader bufferReader = null;
        try {
            fileReader = new FileReader(file);
            bufferReader = new BufferedReader(fileReader);
            while (bufferReader.ready()) {
                String lineStr = bufferReader.readLine();
                if (!TextUtils.isEmpty(lineStr)) {
                    resultList.add(lineStr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bufferReader) {
                    bufferReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != fileReader) {
                    try {
                        fileReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * 文件是否在dir中存在
     *
     * @return File
     */
    public static final boolean isExitInDir(File dir, String fileName) {
        if (null != dir && dir.exists()) {
            File file = new File(dir, fileName);
            return file.exists();
        }
        return false;
    }

    // 复制文件
    public static void copyFile(File sourceFile, File targetFile)
        throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    public static void saveBitmap2SdCard(String bitName, Bitmap mBitmap) {
        File f = new File("/sdcard/" + bitName + ".png");
        try {

            if (!f.exists()) {

                f.createNewFile();
            }
        } catch (IOException e) {
            Log.e("保存图片到SD卡", "在保存图片时出错：" + e.toString());

        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sd卡是否存在
     *
     * @return
     */
    public static final boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
            Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取文件创建或修改时间
     *
     * @param filePath 文件路径
     * @return
     */
    public static String getFileCreateTime(String filePath) {

        File file = new File(filePath);
        if (!file.exists())
            return "";
        long time = file.lastModified();// 返回文件最后修改时间，是以个long型毫秒数
        String ctime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            .format(new Date(time));
        return ctime;
    }

    /**
     * 删除文件 或文件夹
     *
     * @param filePath
     */
    public static void deleteFiles(String filePath) {
        // FileHelper.deleteFiles2(filePath);
        RecursionDeleteFile(new File(filePath));
    }

    public static void RecursionDeleteFile(File file) {
        if (!file.exists())
            return;

        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f);
            }
        }
    }

    public static byte[] getBitmapByte(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static final byte[] stream2byte(InputStream inStream)
        throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    public static InputStream bitmap2InputStream(Bitmap bm) {
        if (bm == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }


    public static String bitmap2Base64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64Api.encode(bitmapBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * bitmap转base64不压缩
     *
     * @param bitmap
     * @return
     */
    public static String bitmap2Base64NoCompress(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64Api.encode(bitmapBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * bitmap转base64不压缩
     *
     * @param bitmap
     * @return
     */
    public static String bitmap2Base64To30Compress(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64Api.encode(bitmapBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static File CacheRoot;

    private static ReadWriteLock rwl = new ReentrantReadWriteLock();


    /**
     * 存储Json文件
     *
     * @param json     json字符串
     * @param fileName 存储的文件名
     * @param append   true 增加到文件末，false则覆盖掉原来的文件
     */
    public static void writeJson(String json, String fileName,
                                 boolean append) {

        rwl.writeLock().lock();
        Context c = RxApplication.getInstance().getmContext();
        CacheRoot = c.getFilesDir();
        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            File ff = new File(CacheRoot, fileName);
            boolean boo = ff.exists();
            fos = new FileOutputStream(ff, append);
            os = new ObjectOutputStream(fos);
            if (append && boo) {
                FileChannel fc = fos.getChannel();
                fc.truncate(fc.position() - 4);

            }

            os.writeObject(json);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (os != null) {

                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            rwl.writeLock().unlock();

        }

    }

    /**
     * 读取json数据
     *
     * @param fileName
     * @return 返回值为list
     */

    @SuppressWarnings("resource")
    public static List<String> readJson(String fileName) {
        rwl.readLock().lock();
        Context c = RxApplication.getInstance().getmContext();
        CacheRoot = c.getFilesDir();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<String> result = new ArrayList<String>();
        File des = new File(CacheRoot, fileName);
        try {
            fis = new FileInputStream(des);
            ois = new ObjectInputStream(fis);
            while (fis.available() > 0)
                result.add((String) ois.readObject());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            rwl.readLock().unlock();

        }


        return result;
    }

    /**
     * 从assets读内容
     *
     * @param context
     * @param name
     * @return
     */
    public static String readFileFromAssets(Context context, String name) {
        InputStream is = null;
        try {
            is = context.getResources().getAssets().open(name);
        } catch (Exception e) {
            throw new RuntimeException(FileUtils.class.getName() + ".readFileFromAssets---->" + name + " not found");
        }
        return inputStream2String(is);
    }

    /**
     * 输入流转字符串
     *
     * @param is
     * @return 一个流中的字符串
     */
    public static String inputStream2String(InputStream is) {
        if (null == is) {
            return null;
        }
        StringBuilder resultSb = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            resultSb = new StringBuilder();
            String len;
            while (null != (len = br.readLine())) {
                resultSb.append(len);
            }
        } catch (Exception ex) {
        } finally {
            if (is != null) {
                closeIO(is);
            }
        }
        return null == resultSb ? null : resultSb.toString();
    }

    /**
     * 关闭流
     *
     * @param closeables
     */
    public static void closeIO(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                throw new RuntimeException(FileUtils.class.getClass().getName(), e);
            }
        }
    }


    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap, String fileName) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
            Environment.MEDIA_MOUNTED)) {
            savePath = "/sdcard/qrcode/pic/";
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                .getAbsolutePath()
                + "/qrcode/pic/";
        }
        try {
            if (fileName.isEmpty()) {
                filePic = new File(savePath + UUID.randomUUID().toString() + ".jpg");
            } else {
                filePic = new File(savePath + fileName + ".jpg");
            }
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }

    private static int count = 1;

    /**
     * 当两个图片的名字相同时，创建文件逻辑
     *
     * @param fileName 文件名称
     * @param appDir   文件夹
     * @param file     文件路径
     * @return
     */
    private static File recursiveQuery(String fileName, File appDir, File file) {
        String sf = file.toString();
        //截取图片名称后  (1)
        String substring = sf.substring(count >= 10 ? sf.length() - 8 : sf.length() - 7, sf.length() - 4);
        if (substring.contains("(") && substring.contains(")")) {
            String s = substring.substring(1, count >= 10 ? 3 : 2);
            int parseInt = Integer.parseInt(s);
            count = ++parseInt;
        }
        String mFileName = fileName + "(" + count + ")" + ".jpg";
        file = new File(appDir, mFileName);
        //有名字相同的就递归调用，直到不同未知
        if (file.exists()) {
            file = recursiveQuery(fileName, appDir, file);
        }
        return file;
    }

    public static File getSaveFile(Context context) {
        File file = new File(getImagePath(), "pic.jpg");
        return file;
    }

    public static File saveImageToGallery(Context context, Bitmap bmp, String fileName, File filePath) {
        File appDir = filePath;
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        //        String sFileName = "";
        //        if (fileName.isEmpty()) {
        //                    sFileName = System.currentTimeMillis() + ".jpg";
        //        String fileName = System.currentTimeMillis() + ""/* + "_" + (index + 1)*/;
        //        sFileName = fileName + ".jpg";
        //        } else {
        //        }
        //        sFileName = fileName + ".jpg";

        File file = new File(appDir, fileName);
        if (file.exists()) {
            file = recursiveQuery(fileName, appDir, file);
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException var13) {
            var13.printStackTrace();
        } catch (IOException var14) {
            var14.printStackTrace();
        }

        //保存到系统相册必须要有下面的这些，不然保存不成功
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            long timeMillis = System.currentTimeMillis();
            String systemTime = String.valueOf(timeMillis);
            String brand = android.os.Build.BRAND;
            String model = android.os.Build.MODEL;
            ExifInterface exifInterface = new ExifInterface(file.getAbsolutePath());
            exifInterface.setAttribute(ExifInterface.TAG_DATETIME, systemTime);
            exifInterface.setAttribute(ExifInterface.TAG_MAKE, brand);
            exifInterface.setAttribute(ExifInterface.TAG_MODEL, model);
            exifInterface.saveAttributes();
            Logger.e("systemTime-->" + systemTime + "\n设备品牌" + brand + "\n设备型号" + model);
        } catch (FileNotFoundException var11) {
            var11.printStackTrace();
        } catch (IOException var12) {
            var12.printStackTrace();
        }
        //一次广播系统可能更细不出来，所以连续发两次
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        //        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        return file;
    }

    /**
     * @param outFile 图片的目录路径
     * @param bitmap
     * @return
     */
    public static File storeBitmap(File outFile, Bitmap bitmap) {
        // 检测是否达到存放文件的上限
        if (!outFile.exists() || outFile.isDirectory()) {
            outFile.getParentFile().mkdirs();
        }
        FileOutputStream fOut = null;
        try {
            outFile.deleteOnExit();
            outFile.createNewFile();
            fOut = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
        } catch (IOException e1) {
            outFile.deleteOnExit();
        } finally {
            if (null != fOut) {
                try {
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    outFile.deleteOnExit();
                }
            }
        }
        return outFile;
    }
}
