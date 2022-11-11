package com.zpj.progressbar.demo.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Installation {
    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    public synchronized static String id() {
        if (sID == null || sID == "") {

            File installation = new File(Environment.getExternalStorageDirectory(), INSTALLATION);
            try {
                if (!installation.exists()){
                    String id = "123456789";
                    setId(id);
                    return id;
                }
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID;
    }

    public synchronized static String setId(String id) {
            File installation = new File(Environment.getExternalStorageDirectory(), INSTALLATION);
            try {
                if (installation.exists()){
                    installation.delete();
                }
                installation.createNewFile();
                writeInstallationFile(id, installation);
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        return sID;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(String id, File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        out.write(id.getBytes());
        out.close();
    }
}
