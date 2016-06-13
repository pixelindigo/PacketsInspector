package com.pixvoxsoftware.packetsinspector;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by PixelIndigo.
 */
public class FileBrowser {

    private String mDirectory;
    File[] mFiles;

    public FileBrowser(String directory) {
        mDirectory = directory;
    }

    public File[] getFiles() {
        if (mFiles == null) {
            mFiles = new File(mDirectory).listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory() || pathname.getName().endsWith(".pcap");
                }
            });
            Arrays.sort(mFiles, new FileComparator());
        }
        return mFiles;
    }

    public void nextDirectory(String directory) {
        mDirectory = directory;
        mFiles = null;
    }

    public File upDirectory() {
        mDirectory = new File(mDirectory).getParent();
        mFiles = null;
        return new File(mDirectory);
    }

    class FileComparator implements Comparator<File> {
        @Override
        public int compare(File lhs, File rhs) {

            if (lhs.isDirectory() == rhs.isDirectory()) { // Both files are directory OR file, compare by name
                return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
            } else if (lhs.isDirectory()) { // Directories before files
                return -1;
            } else { // rhs must be a directory
                return 1;
            }
        }
    }

}
