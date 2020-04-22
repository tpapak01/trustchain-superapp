package nl.tudelft.trustchain.FOC;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import dalvik.system.DexClassLoader;

public class ExecutionActivity extends AppCompatActivity {
    private static Context context;

    private Class fragmentClass = null;
    private Fragment mainFragment = null;

    LinearLayout mainLayoutContainer = null;
    LinearLayout tmpLayout = null;


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.remove(mainFragment);
        transaction.commit();

        mainLayoutContainer.removeView(tmpLayout);

        super.onSaveInstanceState(savedInstanceState);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution);

        context = getApplicationContext();

        final String path = context.getExternalFilesDir(null).getAbsolutePath();
        final String apkPath =  path + "/demoboi.apk";
        final ClassLoader classLoader = new DexClassLoader(apkPath, context.getCacheDir().getAbsolutePath(), null, this.getClass().getClassLoader());

        mainLayoutContainer = (LinearLayout) findViewById(R.id.llcontainer);

        try {

            fragmentClass = classLoader.loadClass("com.example.demoboi.MainFragment");
            mainFragment = (Fragment) fragmentClass.newInstance();

            tmpLayout = new LinearLayout(getApplicationContext());
            tmpLayout.setId(1);

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.add(tmpLayout.getId(), mainFragment, "mainFragment");
            transaction.commit();

            mainLayoutContainer.addView(tmpLayout);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
