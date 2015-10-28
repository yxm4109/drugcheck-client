package net.ucopy.drugcheck;


import android.content.Context;

import net.ucopy.drugcheck.entity.DrugInfo;
import net.ucopy.drugcheck.entity.SearchAction;
import net.ucopy.drugcheck.model.manager.SearchActionManager;
import net.ucopy.drugcheck.tools.ViewUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import dalvik.system.DexClassLoader;

public class TestData {

    public static void addSearchAction() {

        List<SearchAction> res = SearchActionManager.getInstance.getSearchActions();
        if (res.size() == 0) {
            SearchAction searchAction = new SearchAction();
            searchAction.setBarCode("1234567890123456789000");

            searchAction.setDate("1234567890123456789000");
            searchAction.setCityName("1234567890123456789000");
            searchAction.setImei("1234567890123456789000");
            searchAction.setPoiName("1234567890123456789000");

            SearchActionManager.getInstance.saveSearchAction(searchAction);

            DrugInfo drugInfo = new DrugInfo();

            drugInfo.setBarCode("1234567890123456789000");
            Db4oHelper.save(drugInfo);
//            searchAction = new SearchAction();
//            searchAction.setBarCode("qqqqqqqqqqqqqqqqqqqq00");

//            SearchActionManager.getInstance.saveSearchAction(searchAction);
        }

    }


    public static void testDynamicLoad() {

        String aarPath = ApplicationController.getAppContext().getFilesDir().getAbsolutePath();
//            String aarName = "network-release.aar";
        String aarName = "dynamic.jar";
        copyFilesFassets(ApplicationController.getAppContext(), aarName, aarPath + "/" + aarName);

        DexClassLoader classLoader = new DexClassLoader(aarPath + "/" + aarName, aarPath,
                null, ApplicationController.getAppContext().getClassLoader());

        File file = new File(aarPath + "/" + aarName);

        if (file.exists()) {
            ViewUtil.toast(ApplicationController.getAppContext(), "file exists");

        }


        try {
            Class mLoadClass = classLoader.loadClass("net.ucopy.drugcheck.network.GetDrugInfoNetWork");

            Object object = mLoadClass.newInstance();

            Method getMoney = mLoadClass.getMethod("getDrugInfoByBarCode", String.class);
            getMoney.setAccessible(true);
            String money = getMoney.invoke(object, "hello").toString();
            ViewUtil.toast(ApplicationController.getAppContext(), money);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * 从assets目录中复制整个文件夹内容
     *
     * @param context Context 使用CopyFiles类的Activity
     * @param oldPath String  原文件路径  如：/aa
     * @param newPath String  复制后路径  如：xx:/bb/cc
     */
    public static void copyFilesFassets(Context context, String oldPath, String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);//获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {//如果是目录
                File file = new File(newPath);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFilesFassets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {//如果是文件
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


}
