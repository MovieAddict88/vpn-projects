package com.mycompany.myapp.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtil 
{
    public static void save(Context c,String title,String content)
    {
        File dir=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SocksNet GEN");
        dir.mkdirs();
        File file=new File(dir,title+".json");
        try
        {
            OutputStream os=new FileOutputStream(file);
            os.write(content.getBytes());
            os.flush();
            os.close();
            Toast.makeText(c,"Save Successfuly!",1).show();
        }
        catch (IOException e)
        {
            Toast.makeText(c,e.getMessage(),1).show();
        }
    }
}
