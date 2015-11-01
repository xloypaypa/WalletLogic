package model.db.virtual;

import model.db.VirtualFileTable;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xlo on 2015/9/16.
 * it's the image virtual table
 */
public class ImageVirtualTable implements VirtualFileTable {
    protected int count = 0;
    protected Map<String, byte[]> value = new HashMap<>();

    @Override
    public long count() {
        return value.size();
    }

    @Override
    public File find(String s) throws IOException {
        File ans = new File("./" + s);
        if (ans.exists()) {
            return ans;
        }
        if (!ans.createNewFile()) throw new IOException();
        FileOutputStream outputStream = new FileOutputStream(ans);
        outputStream.write(value.get(s));
        outputStream.close();
        return ans;
    }

    @Override
    public String insert(File file, String type) {
        byte[] bytes = new byte[(int) file.length()];
        try {
            InputStream inputStream = new FileInputStream(file);
            int now = 0;
            while (true) {
                int len = inputStream.read(bytes, now, bytes.length - now);
                if (len < 0) {
                    break;
                }
                now += len;
            }
        } catch (IOException e) {
            throw new RuntimeException("error file");
        }
        String path = "file" + count + "." + type;
        count++;
        value.put(path, bytes);
        return path;
    }

    @Override
    public void delete(String s) {
        value.remove(s);
    }

    @Override
    public Long getFileLength(String s) {
        return (long) value.get(s).length;
    }
}
