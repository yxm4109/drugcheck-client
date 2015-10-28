package net.ucopy.drugcheck.model.manager;

import android.content.Context;
import android.util.Log;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import net.ucopy.drugcheck.ApplicationController;
import net.ucopy.drugcheck.Db4oHelper;
import net.ucopy.drugcheck.entity.DrugInfo;
import net.ucopy.drugcheck.tools.ViewUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import dalvik.system.DexClassLoader;

/**
 * Created by meituan on 15/10/27.
 * net.ucopy.drugcheck.model.manager
 */
public enum DrugInfoManager {

    getInstance;

    Object lock = new Object();

    public DrugInfo getDrugInfo(String barCode) {
        return null;
    }

    public DrugInfo getDrugInfoFromDB(String barCode) {
        DrugInfo res = null;
        ObjectContainer container = Db4oHelper.getInstance.getConnection();
        if (container != null) {
            DrugInfo drugInfo = new DrugInfo();
            drugInfo.setBarCode(barCode);

            ObjectSet<DrugInfo> objectSet = container.queryByExample(drugInfo);

            if (objectSet.size() > 0) {
                res = objectSet.get(0);
            }
        }
        return res;
    }

    private DrugInfo drugInfo;

    private void setDrugInfo(DrugInfo drugInfo) {
        this.drugInfo = drugInfo;
    }

    public DrugInfo getDrugInfoFromNet(String barCode) {

        final DrugInfo drugInfo;

        BmobQuery<DrugInfo> bmobQuery = new BmobQuery<>();

        synchronized (lock) {

            bmobQuery.getObject(ApplicationController.getAppContext(), "6b6c11c537", new GetListener<DrugInfo>() {
                @Override
                public void onSuccess(DrugInfo object) {
                    setDrugInfo(object);
                    lock.notify();
                }

                @Override
                public void onFailure(int code, String msg) {

                    Log.e("查询失败：", msg);
                }
            });

            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        return this.drugInfo;
    }

    public void saveDrugInfoToDB(DrugInfo drugInfo) {

        Db4oHelper.save(drugInfo);

    }

    public void saveDrugInfoToNet(DrugInfo drugInfo) {
        drugInfo.save();
    }

    public DrugInfo doSearch(String barCode) {




        return null;
    }

    public static void testDynamicLoad() {

        String aarPath = ApplicationController.getAppContext().getFilesDir().getAbsolutePath();
//            String aarName = "network-release.aar";
        String aarName = "dynamic.jar";
        copyFilesFromAssets(ApplicationController.getAppContext(), aarName, aarPath + "/" + aarName);

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
    public static void copyFilesFromAssets(Context context, String oldPath, String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);
            if (fileNames.length > 0) {
                File file = new File(newPath);
                file.mkdirs();
                for (String fileName : fileNames) {
                    copyFilesFromAssets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
