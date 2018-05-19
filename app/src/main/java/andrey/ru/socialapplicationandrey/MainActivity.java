package andrey.ru.socialapplicationandrey;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import andrey.ru.socialapplicationandrey.cloud.test.QatoraIlovaKunBulut;
import andrey.ru.socialapplicationandrey.fragments.general.cityplus.CityPlusFragment;
import andrey.ru.socialapplicationandrey.fragments.general.profile.MyDatabaseHelper;
import andrey.ru.socialapplicationandrey.fragments.general.profile.PictureViewActivity;
import andrey.ru.socialapplicationandrey.fragments.general.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_READ_STORAGE_PERMISSION = 200;
    private static final int REQUEST_WRITE_STORAGE_PERMISSION = 300;
    private static final int TAKE_PICTURE = 44;
    private Resources resources;
    private ActionBar actionBar;
    private DrawerLayout drawer;
    private CityPlusFragment cityPlusFragment;
    private ProfileFragment profileFragment;
    private Fragment currentFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private BottomNavigationView tabs;
    private int widthOfScreen;
    private int heightOfScreen;
    private MyDatabaseHelper mMyDatabaseHelper;
    private Vector<ProgressDialog> listOfWheels;
    private boolean message;
    private String token;
    private SharedPreferences sharedPreferences;

    public String getToken() {
        String tokenLcl = sharedPreferences.getString("token_key", "");
        return tokenLcl;
    }

    public void deleteImage(String fileNamePrm) {
        profileFragment.deleteImage(fileNamePrm);
    }

    public enum Items {
        MESSAGES, CHATS, CITY_ONLINE, FRIENDS, PROFILE, SETTINGS, PEOPLE_SEARCH, CREATE_CHAT
    }

    public int getDisplayWidth() {
        return widthOfScreen;
    }

    public int getDisplayHeight() {
        return heightOfScreen;
    }

    public void putToProfile(String titleStringPrm, String sPrm) {
        profileFragment.putToProfile(titleStringPrm, sPrm);
    }

    private String title;
    private DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
//            l.a(slideOffset);
            float toggleXLcl = drawerWidth * slideOffset;
            View vLcl = getToolbarNavigationIcon(toolbar);
            vLcl.setX(toggleXLcl);
//
            if (slideOffset < 0.2)
                actionBar.setTitle(title);
            else
                actionBar.setTitle("");

//            setViewParams(actionBarDrawerToggle, (int) toggleXLcl, coordinatorLayout.getHeight());
            drawerView.postInvalidate();

        }

        @Override
        public void onDrawerOpened(View drawerView) {
//            l.a(555);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
        }

        @Override
        public void onDrawerStateChanged(int newState) {
        }
    };
    private CoordinatorLayout coordinatorLayout;
    private int drawerWidth;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.appAnchorTheme);
//        l.pause(1500);
        super.onCreate(savedInstanceState);
        listOfWheels = new Vector<ProgressDialog>();
        mMyDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase dbLcl = mMyDatabaseHelper.getReadableDatabase();
//        l.a(dbLcl.)
         sharedPreferences = getSharedPreferences("Andrey", Context.MODE_WORLD_READABLE
                & Context.MODE_WORLD_WRITEABLE);
        Display displayLcl = getWindowManager().getDefaultDisplay();
        Point sizeLcl = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            displayLcl.getSize(sizeLcl);
            heightOfScreen = sizeLcl.y;
            widthOfScreen = sizeLcl.x;
        } else {
            heightOfScreen = displayLcl.getHeight();
            widthOfScreen = displayLcl.getWidth();
        }
        setContentView(R.layout.activity_main);
        AssetManager assetMabagerLcl = getApplicationContext().getAssets();

        Typeface typefaceLcl = Typeface.createFromAsset(assetMabagerLcl,
                String.format(Locale.US, "fonts/%s", "HelveticaNeueCyr-Light.otf"));


//        android:theme="@style/AppTheme.NoActionBar"
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundResource(R.drawable.side_nav_bar2);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(actionBarDrawerToggle);
        drawer.addDrawerListener(drawerListener);
        actionBarDrawerToggle.syncState();
        resources = getResources();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationViewItemsListener);
        float iLcl = 360 / (1f + 360 + 630 + 150);
        float i2Lcl = 630 / (1f + 360 + 630 + 150);
        drawerWidth = (int) (iLcl * heightOfScreen);
        float menuHeightLcl = i2Lcl * heightOfScreen;
        setViewParams(navigationView, drawerWidth, heightOfScreen);
        LinearLayout carBedLcl = (LinearLayout) navigationView.getHeaderView(0);
        setViewParams(carBedLcl, drawerWidth, (int) (heightOfScreen * (260f / 1140)));
        coordinatorLayout = (android.support.design.widget.CoordinatorLayout) findViewById(R.id.coordinator_layout);
        actionBar = getSupportActionBar();
        actionBar.setTitle("City-Online");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        cityPlusFragment = new CityPlusFragment();
        profileFragment = new ProfileFragment();
//        registration2Fragment = new RegistrationSecondStepFragment();
//        registration3Fragment = new RegistrationThirdStepFragment();
//        registration4Fragment = new RegistrationForthStepFragment();
//        forgotPassword1Fragment =new ForgotPassword1Fragment();
//        forgotPassword2Fragment=new ForgotPassword2Fragment();
        fragmentManager = getFragmentManager();
//        smsEnterForgotFragment=new SmsEnterForgotFragment();
        setFragment(cityPlusFragment);
        if (Build.VERSION.SDK_INT >= 23)
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestCameraPermission();
                    }
                }).start();
            }

    }

    public void setFragment(Fragment fragment) {
        currentFragment = fragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_bed,
                fragment);
        fragmentTransaction.commit();
    }

    private MyRowInNavigationMenu createNewNavigationList_sItem(Items itemsPrm) {
        int iconIdLcl = 0;
        int nameIdLcl = 0;
        String valueLcl = "";
        MyRowInNavigationMenu recordLcl = new MyRowInNavigationMenu(iconIdLcl, resources.getString(nameIdLcl), valueLcl);
        return recordLcl;
    }

    public void setViewParams(View viewPrm, int widthPrm, int heightPrm) {
        ViewGroup.LayoutParams layoutParamsLcl = (ViewGroup.LayoutParams) viewPrm.getLayoutParams();
        layoutParamsLcl.height = heightPrm;
        layoutParamsLcl.width = widthPrm;
        viewPrm.setLayoutParams(layoutParamsLcl);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menuPrm) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menuPrm);

        return super.onCreateOptionsMenu(menuPrm);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        l.a(666);
        switch (requestCode) {
            case TAKE_PICTURE:
                l.a(777);
                l.a(resultCode);
                if (resultCode == MainActivity.RESULT_OK) {
                    l.a("TAKE_PICTURE");
                    profileFragment.onActivityResultPhoto(this);
                }
        }
    }

    public void takePicture(final File filePrm, Uri imageUriPrm) {
//        l.a("uuuuuuuuuuuuuuuuuuu"+imageUriPrm.getPath());
//                File fLcl=new File( imageUriPrm.getPath());

//        intentLcl.        setData(imageUriPrm);
                l.a(4440);
//                String pathLcl = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CityPlus_cash/Photos/Me/" + fLcl.getName();
//                 fLcl=new File(pathLcl );
//                File dirsLcl = fLcl.getParentFile();
//l.a(444);
//
//                if(!dirsLcl.exists())
//                    dirsLcl.mkdirs();
//                l.a(555);
//                if(!fLcl.exists())
//                    try {
//                        fLcl.createNewFile();
//                    } catch (IOException e) {
//                        l.a(7878);
//                        e.printStackTrace();
//                    }
                Intent intentLcl = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intentLcl, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    grantUriPermission(packageName, imageUriPrm, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }

                intentLcl.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

//                intentLcl.putExtra(MediaStore.EXTRA_OUTPUT, imageUriPrm);
        if(null ==filePrm)
            l.a(2222222);
        else
            l.a(11111);
                intentLcl.putExtra(MediaStore.EXTRA_OUTPUT, imageUriPrm);
        intentLcl.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(intentLcl, TAKE_PICTURE);


    }
    public static View getToolbarNavigationIcon(Toolbar toolbar) {
        //check if contentDescription previously was set
        boolean hadContentDescription = TextUtils.isEmpty(toolbar.getNavigationContentDescription());
        String contentDescription = /*!hadContentDescription ? toolbar.getNavigationContentDescription() :*/ "navigationIcon";
        toolbar.setNavigationContentDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<View>();
        //find the view based on it's content description, set programatically or with android:contentDescription
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            toolbar.findViewsWithText(potentialViews, contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        }
        //Nav icon is always instantiated at this point because calling setNavigationContentDescription ensures its existence
        View navIcon = null;
        if (potentialViews.size() > 0) {
            navIcon = potentialViews.get(0); //navigation icon is ImageButton
        }
        //Clear content description if not previously present
        if (hadContentDescription)
            toolbar.setNavigationContentDescription(null);
        return navIcon;
    }

    private NavigationView.OnNavigationItemSelectedListener navigationViewItemsListener
            = new NavigationView.OnNavigationItemSelectedListener() {

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();
            int nameIdLcl = 0;
            switch (id) {
                case R.id.messages:
                    nameIdLcl = R.string.messages;
                    setFragment(cityPlusFragment);
                    break;
                case R.id.chats:
                    nameIdLcl = R.string.chats;
                    break;
                case R.id.city_online:
                    setFragment(cityPlusFragment);
                    nameIdLcl = R.string.city_online;
                    break;
                case R.id.friends_:
                    nameIdLcl = R.string.friends_;
                    setFragment(cityPlusFragment);
                    break;
                case R.id.profile:
                    nameIdLcl = R.string.profile;
                    setFragment(profileFragment);
                    test();
//                    new Profile
                    break;
                case R.id.settings_:
                    nameIdLcl = R.string.settings_;
                    setFragment(cityPlusFragment);
                    break;
                case R.id.people_search:
                    nameIdLcl = R.string.people_search;
                    setFragment(cityPlusFragment);
                    break;
                case R.id.create_talk:
                    nameIdLcl = R.string.create_talk;
                    setFragment(cityPlusFragment);
                    break;
                case R.id.log_out:
                    nameIdLcl = R.string.log_out;
                    sharedPreferences.edit().putString("token_key","").commit();
                    Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    finish();
                    break;
            }
            l.a(nameIdLcl);
            if (0 == nameIdLcl)
                return false;
            actionBar.setTitle(nameIdLcl);
            title = getResources().getString(nameIdLcl);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            return true;
        }
    };

private void test(){
    new Thread(new Runnable() {
        @Override
        public void run() {
            int i=0;  new QatoraIlovaKunBulut(MainActivity.this);
//            while(i<2){
//                i++;
//
//                l.pause(25000);
//            }

        }
    }).start();
}
    /**
     * Shows progress dialog wheel
     */
    public void showWheel() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final ProgressDialog progressLcl = new ProgressDialog(MainActivity.this, R.style.CustomDialog);//ProgressDialog.show(LoginActivity.this, "", "", true);
                progressLcl.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressLcl.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                progressLcl.show();
                listOfWheels.add(progressLcl);
            }
        });
    }
    /**
     * Stops progress dialog wheel
     */
    public void stopWheel() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!listOfWheels.isEmpty()) {
                    listOfWheels.lastElement().cancel();
                    listOfWheels.removeElement(listOfWheels.lastElement());
                }
            }
        });
    }

    /**
     * Self explained
     */
    public void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                confirmationCameraPermissionDialog().show();
            } else {
                l.a(888);
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA_PERMISSION);
            }
        }
    }

    /**
     * Self explained
     */
    private void requestReadStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                confirmationReadStoragePermissionDialog().show();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_READ_STORAGE_PERMISSION);
            }
        }
    }

    /**
     * Self explained
     */
    private void requestWriteStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                confirmationWriteStoragePermissionDialog().show();
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_STORAGE_PERMISSION);
            }
        }
    }

    /**
     * Shows OK/Cancel confirmation dialog about write to storage permission.
     */


    public AlertDialog confirmationWriteStoragePermissionDialog() {
        return new AlertDialog.Builder(this)
                .setMessage(R.string.request_write_permission)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_WRITE_STORAGE_PERMISSION);
                    }
                })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.this.finish();
                            }
                        })
                .create();
    }

    /**
     * Shows OK/Cancel confirmation dialog about read to storage  permission.
     */

    public AlertDialog confirmationReadStoragePermissionDialog() {
        return new AlertDialog.Builder(this)
                .setMessage(R.string.request_read_permission)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_READ_STORAGE_PERMISSION);
                    }
                })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.this.finish();
                            }
                        })
                .create();
    }

    /**
     * Shows OK/Cancel confirmation dialog about camera permission.
     */


    public AlertDialog confirmationCameraPermissionDialog() {
        return new AlertDialog.Builder(this)
                .setMessage(R.string.request_permission)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(
                                new String[]{Manifest.permission.CAMERA},
                                REQUEST_CAMERA_PERMISSION);
                    }
                })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.this.finish();
                            }
                        })
                .create();
    }

    /**
     * The handler of what user has choose on permission dialog
     * when it s a first launch of the application after installation
     *
     * @param requestCode  one of defined application constants
     * @param permissions
     * @param grantResults
     * @see Integer REQUEST_READ_STORAGE_PERMISSION
     * @see Integer REQUEST_WRITE_STORAGE_PERMISSION
     * @see Integer REQUEST_CAMERA_PERMISSION
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_STORAGE_PERMISSION:
                stopWheel();
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            requestWriteStoragePermission();
                        }
                    }).start();
                break;
            case REQUEST_WRITE_STORAGE_PERMISSION:
                stopWheel();
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    ;
                break;
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            requestReadStoragePermission();
                        }
                    }).start();
        }
    }

    public void message(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (message)
                    return;
                else
                    message = true;
//                if(onPause)
//                    return;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        l.pause(3000);
                        message = false;
                    }
                }).start();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void messageWindow(final String sPrm, final boolean mustDiePrm) {
        stopWheel();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final MyPopUp dialog = new MyPopUp(MainActivity.this, sPrm);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                if (mustDiePrm)
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            l.pause(3000);
                            if (dialog != null)
                                dialog.cancel();
                            ;
                        }
                    }).start();
            }
        });
    }

    public void viewExpantPicture(String imageNamePrm) {
        Intent photoPickerIntent = new Intent(this,PictureViewActivity.class);
        photoPickerIntent.putExtra("imageName", imageNamePrm);
        startActivity(photoPickerIntent);
    }
}
