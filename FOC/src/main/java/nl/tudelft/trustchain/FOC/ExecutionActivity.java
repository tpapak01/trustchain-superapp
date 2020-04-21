package nl.tudelft.trustchain.FOC;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import dalvik.system.DexClassLoader;

public class ExecutionActivity extends AppCompatActivity {
    private static Context context;

    private Class fragmentClass = null;
    private Fragment mainFragment = null;

    @Override
    protected void onPause() {
        super.onPause();

        Log.e("69", "lol");

        fragmentClass = null;
        mainFragment = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e("69", "destoryyyyyy");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("69", "beep boop");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution);

        context = getApplicationContext();
        final String path = context.getExternalFilesDir(null).getAbsolutePath();
        final String apkPath =  path + "/demoboi.apk";
        Log.e("69", apkPath);
        final ClassLoader classLoader = new DexClassLoader(apkPath, context.getCacheDir().getAbsolutePath(), null, this.getClass().getClassLoader());

        try {

            fragmentClass = classLoader.loadClass("com.example.demoboi.MainFragment");
            mainFragment = (Fragment) fragmentClass.newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        if(fragmentClass == null) {
            Log.d("lol", "shit");
        }

        if(mainFragment == null) {
            Log.d("lol", "shiet");
        }
        //adsasd

        LinearLayout llcont = (LinearLayout) findViewById(R.id.llcontainer);

        LinearLayout ll = new LinearLayout(getApplicationContext());
        int tmpid = 1;
        ll.setId(tmpid);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

//        Fragment frag = new MainFragment();

        transaction.add(ll.getId(), mainFragment, "fragment0");
        transaction.commit();

        llcont.addView(ll);
    }
}
