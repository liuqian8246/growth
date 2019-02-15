package cn.lanhu.android_growth_plan.gaiban.app.cache;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

import cn.lanhu.android_growth_plan.gaiban.app.config.RxApplication;

/**
 * 数据删除工具类
 *
 */
public class DataCleanManager {
	
	/**
	 * 清除本应用内部缓存
	 * (/data/data/com.xxx.xxx/cache)
	 */
	public static void cleanInternalCache() {
		deleteFilesByDirectory(RxApplication.getInstance().getmContext().getCacheDir());
//		deleteFilesByDirectory(context.getFilesDir());
	}

	/**
	 * 清楚本应用所有数据库
	 * (/data/data/com.xxx.xxx/databases)
	 */
	public static void cleanDatabases() {
		deleteFilesByDirectory(new File("/data/data/"
				+ RxApplication.getInstance().getmContext().getPackageName() + "/databases"));
	}

	/**
	 * 清除本应用SharedPreference
	 * (/data/data/com.xxx.xxx/shared_prefs)
	 */
	public static void cleanSharedPreference() {
		deleteFilesByDirectory(new File("/data/data/"
				+ RxApplication.getInstance().getmContext().getPackageName() + "/shared_prefs"));
	}
	
	/**
	 * 按名字清除本应用数据库
	 * @param dbName
	 */
	public static void cleanDatabaseByName( String dbName) {
		RxApplication.getInstance().getmContext().deleteDatabase(dbName);
	}

	/**
	 * 清除/data/data/com.xxx.xxx/files下的内容
	 */
	public static void cleanFiles() {
		deleteFilesByDirectory(RxApplication.getInstance().getmContext().getFilesDir());
	}

	/**
	 * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
	 */
	public static void cleanExternalCache() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			deleteFilesByDirectory( RxApplication.getInstance().getmContext().getExternalCacheDir());
		}
	}

	/**
	 * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
	 * @param filePath
	 */
	public static void cleanCustomCache(String filePath) {
		deleteFilesByDirectory(new File(filePath));
	}
	
	/**
	 * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
	 * @param file
	 */
	public static void cleanCustomCache(File file) {
		deleteFilesByDirectory(file);
	}

	/**
	 * 清除本应用所有的数据
	 * @param filepath
	 */
	public static void cleanApplicationData( String... filepath) {
		cleanInternalCache();
		cleanExternalCache();
		cleanDatabases();
		cleanSharedPreference();
		cleanFiles();
		for (String filePath : filepath) {
			cleanCustomCache(filePath);
		}
	}

	public static void clearAllCache() {
		Context context = RxApplication.getInstance().getmContext();
		deleteDir(context.getCacheDir());
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			deleteDir(context.getExternalCacheDir());
		}
	}

	private static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (String aChildren : children) {
				boolean success = deleteDir(new File(dir, aChildren));
				if (!success) {
					return false;
				}
			}
		}
		assert dir != null;
		return dir.delete();
	}

	/**
	 * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 
	 * @param directory
	 */
	private static void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File child : directory.listFiles()) {
				if (child.isDirectory()) {
					deleteFilesByDirectory(child);
				} 
				child.delete();
			}
		}
	}

	public static String getTotalCacheSize() throws Exception {
		Context context = RxApplication.getInstance().getmContext();
		long cacheSize = getFolderSize(context.getCacheDir());
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			cacheSize += getFolderSize(context.getExternalCacheDir());
		}
		return getFormatSize(cacheSize);
	}

	// 获取文件
	//Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
	//Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
	public static long getFolderSize(File file) throws Exception {
		long size = 0;
		try {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				// 如果下面还有文件
				if (fileList[i].isDirectory()) {
					size = size + getFolderSize(fileList[i]);
				} else {
					size = size + fileList[i].length();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 格式化单位
	 *
	 * @param size
	 * @return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return size + "Byte";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
				+ "TB";
	}
}
