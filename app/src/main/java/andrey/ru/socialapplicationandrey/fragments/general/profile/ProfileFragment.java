package andrey.ru.socialapplicationandrey.fragments.general.profile;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.SupportActivity;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import andrey.ru.socialapplicationandrey.MainActivity;
import andrey.ru.socialapplicationandrey.R;
import andrey.ru.socialapplicationandrey.StatusAboutMePopup;
import andrey.ru.socialapplicationandrey.cloud.profile.DeleteImageCloudHandler;
import andrey.ru.socialapplicationandrey.cloud.profile.PhotoPostHandler;
import andrey.ru.socialapplicationandrey.cloud.profile.StatusAboutDataInterestsHandler;
import andrey.ru.socialapplicationandrey.l;

/**
 * Created by User on 5/29/2017.
 */

public class ProfileFragment extends Fragment {
    public static final String STATUS_TITLE = "Изменение статуса";
    public static final String ABOUT_TITLE = "Информация о себе";
    public static final String INTERESTS_TITLE = "Ваши интересы (через запятую)";
    public static final String DATA_TITLE = "Введите дату рождения";
    private static final int CRITICAL_SIZE = 1000000;

    private View view;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private LinearLayout photosBed;
    private LayoutInflater inflater;
    private int displayWidth;
    private int displayHeight;
    private ImageView tuda;
    private ImageView suda;
    private View.OnClickListener photoBedButtonsListener = new View.OnClickListener() {
        public boolean buttonBlocked;

        @Override
        public void onClick(View v) {
            int xLcl = horizontalScroll.getScrollX();
            int multiplierLcl = 0;
            switch (v.getId()) {
                case R.id.tuda:
                    multiplierLcl = 1 + xLcl / displayWidth;
                    horizontalScroll.scrollTo(multiplierLcl * displayWidth, 0);
                    break;
                case R.id.suda:
                    buttonBlocked = true;
                    int remainedLcl = xLcl % displayWidth;
                    if (remainedLcl > 0) {
                        multiplierLcl = xLcl / displayWidth;
                    } else
                        multiplierLcl = xLcl / displayWidth - 1;
                    horizontalScroll.scrollTo(multiplierLcl * displayWidth, 0);
                    break;
                case R.id.add_photo:
                    final MainActivity activityLcl = (MainActivity) getActivity();
                    launchNativeCameraForResult(activityLcl);
                    break;
            }
        }
    };
    private View.OnClickListener editButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.status_edit:
                    triggerEditWindow(STATUS_TITLE, statusTextView.getText().toString());
                    break;
                case R.id.about_edit:
                    triggerEditWindow(ABOUT_TITLE, aboutTextView.getText().toString());
                    break;
                case R.id.data_edit:
                    triggerEditWindow(DATA_TITLE, dataTextView.getText().toString());
                    break;
                case R.id.interests_edit:
                    triggerEditWindow(INTERESTS_TITLE, interestStrings);
                    break;
            }
        }
    };
    private View.OnClickListener photosListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String fileNameLcl;
            switch (v.getId()) {
                case R.id.photo:
                    fileNameLcl= (String) v.getTag();
                    viewExpantPicture(fileNameLcl);
                    break;
                case R.id.x:
                    fileNameLcl = (String) v.getTag();
                     MainActivity activityLcl=(MainActivity)getActivity();
                    new DeleteImageCloudHandler( activityLcl, fileNameLcl, activityLcl.getToken());
//                    if (photoLcl.exists())
//                    l.a(homeDirectory + "/" + fileNameLcl);
//                    imageUri = Uri.fromFile(photoLcl);
                    break;
            }
        }
    };

    public void deleteImage( String fileNamePrm) {
        File photoLcl = new File(homeDirectory + "/" + fileNamePrm);
        Uri imageUriLcl = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() +
                ".provider", photoLcl);
        ContentResolver contentResolver = getActivity().getContentResolver();
        contentResolver.delete(imageUriLcl, null, null);
        final View layoutLcl = photosBed.findViewWithTag(fileNamePrm);
        photosBed.post(new Runnable() {
            @Override
            public void run() {
                photosBed.removeView(layoutLcl);
            }
        });
    }

    private File homeDirectoryFile;

    private void viewExpantPicture(String fileNamePrm) {
        ((MainActivity)getActivity()).viewExpantPicture(fileNamePrm);
    }

    private HorizontalScrollView horizontalScroll;
    private TextView statusTextView;
    private TextView aboutTextView;
    private TextView interestsTextView;
    private TextView dataTextView;
    private String interestStrings;
    private Vector<TextView> InterestsVector;
    private Vector<String> stringsVector;
    private Uri imageUri;
    private String homeDirectory;

    public ProfileFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containerPrm,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        MainActivity activityLcl = (MainActivity) getActivity();
        displayWidth = activityLcl.getDisplayWidth();
        displayHeight = activityLcl.getDisplayHeight();
        view = inflater.inflate(R.layout.profile_fragment, containerPrm, false);
        photosBed = (LinearLayout) view.findViewById(R.id.photos_bed);
        horizontalScroll = (HorizontalScrollView) view.findViewById(R.id.scroll);
        tuda = (ImageView) view.findViewById(R.id.tuda);
        suda = (ImageView) view.findViewById(R.id.suda);
        tuda.setOnClickListener(photoBedButtonsListener);
        suda.setOnClickListener(photoBedButtonsListener);
        Button addPhotoButtonLcl = (Button) view.findViewById(R.id.add_photo);
        addPhotoButtonLcl.setOnClickListener(photoBedButtonsListener);
        ImageView statusEditButtonLcl = (ImageView) view.findViewById(R.id.status_edit);
        ImageView aboutEditButtonLcl = (ImageView) view.findViewById(R.id.about_edit);
        ImageView dataEditButtonLcl = (ImageView) view.findViewById(R.id.data_edit);
        ImageView interestsEditButtonLcl = (ImageView) view.findViewById(R.id.interests_edit);
        statusEditButtonLcl.setOnClickListener(editButtonsListener);
        aboutEditButtonLcl.setOnClickListener(editButtonsListener);
        dataEditButtonLcl.setOnClickListener(editButtonsListener);
        interestsEditButtonLcl.setOnClickListener(editButtonsListener);
        SharedPreferences prefsLcl = activityLcl.getSharedPreferences("persons", activityLcl.MODE_PRIVATE);
        statusTextView = (TextView) view.findViewById(R.id.status_text);
        statusTextView.setText(prefsLcl.getString(STATUS_TITLE, ""));
        aboutTextView = (TextView) view.findViewById(R.id.about_text);
        aboutTextView.setText(prefsLcl.getString(ABOUT_TITLE, ""));
        dataTextView = (TextView) view.findViewById(R.id.data_text);
        dataTextView.setText(prefsLcl.getString(DATA_TITLE, ""));
        InterestsVector = new Vector<>();
        TextView textViewLcl = (TextView) view.findViewById(R.id.item_1);
        InterestsVector.add(textViewLcl);
        textViewLcl = (TextView) view.findViewById(R.id.item_2);
        InterestsVector.add(textViewLcl);
        textViewLcl = (TextView) view.findViewById(R.id.item_3);
        InterestsVector.add(textViewLcl);
        textViewLcl = (TextView) view.findViewById(R.id.item_4);
        InterestsVector.add(textViewLcl);
        textViewLcl = (TextView) view.findViewById(R.id.item_5);
        InterestsVector.add(textViewLcl);
        textViewLcl = (TextView) view.findViewById(R.id.item_6);
        InterestsVector.add(textViewLcl);
        interestStrings = prefsLcl.getString(INTERESTS_TITLE, ""); //
        String[] interestsStringsArrayLcl = interestStrings.split(",");
        stringsVector = new Vector<String>();
        for (String s : interestsStringsArrayLcl)
            stringsVector.add(s);
        if (stringsVector.size() > 6) {
            stringsVector.setSize(6);
            stringsVector.trimToSize();
        }
        emptyItems();
        fillItems();
//        interestsTextView=(TextView)view.findViewById(R.id.interests_text);
        return view;
    }

    private void emptyItems() {
        int i = 0;
        TextView textViewLcl;
        while (i < InterestsVector.size()) {
            textViewLcl = InterestsVector.get(i);
            textViewLcl.setText("");
            textViewLcl.setVisibility(View.INVISIBLE);
            i++;
        }
    }

    private void fillItems() {
        int i = 0;
        TextView textViewLcl;
        while (i < stringsVector.size()) {
            textViewLcl = InterestsVector.get(i);
            String sLcl = stringsVector.get(i).trim();
            if (sLcl.isEmpty())
                break;
            textViewLcl.setVisibility(View.VISIBLE);
            textViewLcl.setText(sLcl);
            i++;
        }
    }

    private void setViewParams(LinearLayout viewPrm, int widthPrm, int heightPrm) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(widthPrm, heightPrm);
        viewPrm.setLayoutParams(lp);
//        LinearLayout.LayoutParams layoutParamsLcl = (LinearLayout.LayoutParams) viewPrm.getLayoutParams();
//        layoutParamsLcl.height = heightPrm;
//        layoutParamsLcl.width = widthPrm;
//        viewPrm.setLayoutParams(layoutParamsLcl);
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showWheel();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LinearLayout vLcl;
                homeDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/CityPlus_cash/Photos/Me";
                l.a(homeDirectory);
                homeDirectoryFile = new File(homeDirectory);
                if (!homeDirectoryFile.exists()) {
                    stopWheel();
                    return;
                }
                String[] filesLcl = homeDirectoryFile.list();
                if (null == filesLcl) {
                    stopWheel();
                    return;
                }
                final Vector<LinearLayout> vectorOfViewsLcl = new Vector();
                final Vector<HashMap<String, Bitmap>> vectorOfBitmapsLcl = new Vector();
                for (String s : filesLcl) {
                    vLcl = (LinearLayout) inflater.inflate(R.layout.photo, null);
                    vectorOfViewsLcl.add(vLcl);
                    File fLcl = new File(homeDirectoryFile, s);
                    l.a("oioi "+fLcl.getPath()+fLcl.exists());
                    try {
                        Bitmap bitmapLcl = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), getUri(fLcl));
                        if(bitmapLcl==null)
                            continue;
                        final  Bitmap bitmapReadyLcl= resizeBitmap(bitmapLcl,true);
                        HashMap<String, Bitmap> mapLcl = new HashMap();
                        mapLcl.put(s, bitmapReadyLcl);
                        vectorOfBitmapsLcl.add(mapLcl);
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue;
                    }
                }
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        int i = 0;
                        for (HashMap<String, Bitmap> m : vectorOfBitmapsLcl) {
                            LinearLayout layoutLcl = vectorOfViewsLcl.get(i);
                            ImageView imageViewLcl = (ImageView) layoutLcl.findViewById(R.id.photo);//
                            imageViewLcl.setImageBitmap((Bitmap) (m.values().toArray()[0]));
                            ImageView crossLcl = (ImageView) layoutLcl.findViewById(R.id.x);
                            imageViewLcl.setOnClickListener(photosListener);
                            crossLcl.setOnClickListener(photosListener);
                            setViewParams(layoutLcl, displayWidth, photosBed.getHeight());
                            String tagLcl = (String) (m.keySet().toArray())[0];
                            imageViewLcl.setTag(tagLcl);
                            crossLcl.setTag(tagLcl);
                            layoutLcl.setTag(tagLcl);
                            photosBed.addView(layoutLcl);
                            i++;
                        }
                        stopWheel();
                    }
                });
            }
        }).start();
    }

    private void stopWheel() {
        ((MainActivity) getActivity()).stopWheel();
    }

    private void showWheel() {
         ((MainActivity) getActivity()).showWheel();
    }

    public String getPhone() {
        return phoneEditText.getText().toString();
    }

    //    public String getPassword() {
//        return passwordEditText.getText().toString();
//    }
    public void triggerEditWindow(final String textInitPrm, final String oldTextPrm) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final StatusAboutMePopup dialog = new StatusAboutMePopup((MainActivity) getActivity(), textInitPrm, oldTextPrm);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    public void putToProfile(String titleStringPrm, String sPrm) {
        String statusStringLcl = (String) statusTextView.getText(),
                aboutStringLcl= (String) aboutTextView.getText(),
                dataStringLcl=(String) dataTextView.getText();
        MainActivity activityLcl = (MainActivity) getActivity();
        String tokenLcl = activityLcl.getToken();
        showWheel();
        switch ((titleStringPrm)) {
            case STATUS_TITLE:
                statusTextView.setText(sPrm);
                new StatusAboutDataInterestsHandler(activityLcl, tokenLcl,sPrm,aboutStringLcl,dataStringLcl,interestStrings);
                break;
            case ABOUT_TITLE:
                aboutTextView.setText(sPrm);
                new StatusAboutDataInterestsHandler(activityLcl, tokenLcl,statusStringLcl,sPrm,dataStringLcl,interestStrings);
                break;
            case DATA_TITLE:
                dataTextView.setText(sPrm);
                new StatusAboutDataInterestsHandler(activityLcl, tokenLcl,statusStringLcl,aboutStringLcl,sPrm,interestStrings);
                break;
            case INTERESTS_TITLE:
                interestStrings = sPrm;
                String[] interestsStringsArrayLcl = interestStrings.split(",");
                stringsVector.clear();
                for (String s : interestsStringsArrayLcl)
                    stringsVector.add(s);
                if (stringsVector.size() > 6) {
                    stringsVector.setSize(6);
                    stringsVector.trimToSize();
                }
                emptyItems();
                fillItems();
                new StatusAboutDataInterestsHandler(activityLcl, tokenLcl,statusStringLcl,aboutStringLcl,dataStringLcl,sPrm);
                break;
        }
    }

    public void launchNativeCameraForResult(final MainActivity mainActivity) {
        l.a(1010);
        String rootLcl =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        String homeDirectoryLcl = rootLcl + "/CityPlus_cash/Photos/Me";
        File fLcl = new File(homeDirectoryLcl);
        if (!fLcl.exists())
            fLcl.mkdirs();
        String[] dirsLcl = fLcl.list();
        int i = 0;
        for (String s : dirsLcl) {
            if (s.contains(".jpg")) ;
            i++;
        }
        l.a(i);
        ;
        if (i >= 3) {
            mainActivity.messageWindow("Много фотографий", true);
            return;
        }

        String fileStampTimeLcl = new SimpleDateFormat("hhmmss").format(new Date());
        File photoLcl = new File(fLcl, fileStampTimeLcl + ".jpg");
        if(!photoLcl.exists())
            try {
                photoLcl.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
//                            imageUri = Uri.fromFile(photoLcl);
        imageUri = FileProvider.getUriForFile(mainActivity, mainActivity.getApplicationContext().getPackageName() +
                ".provider", photoLcl);
        mainActivity.takePicture(photoLcl,imageUri);
    }

//
    public void onActivityResultPhoto(final MainActivity activityPrm) {
        showWheel();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final LinearLayout vLcl;//getExternalStorageDirectory
                homeDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/CityPlus_cash/Photos/Me";
                     Bitmap bitmapLcl=null;
                    final String nameLcl;
                    final Bitmap bitmapReadyLcl;
                Bitmap bitmapProperLcl=null;
                    try {
                        bitmapLcl = MediaStore.Images.Media.getBitmap(activityPrm.getContentResolver(),imageUri);
                        String pathLcl = imageUri.getPath();
                            l.a("nnnnuuuulllll");
                        bitmapReadyLcl= resizeBitmap(bitmapLcl,false);
                        l.a("kkkkk="+pathLcl);
                        nameLcl = pathLcl.substring(pathLcl.indexOf("/Me/") + 4);       //      4 chars forward
                         bitmapProperLcl= resizeToProperBitmap(bitmapLcl);
                        bitmapLcl.isRecycled();
                    } catch (IOException e) {
                        e.printStackTrace();
                        stopWheel();
                        return;
                    }
                     File fLcl= new File(homeDirectoryFile, nameLcl);
                if(bitmapLcl==null||bitmapLcl.isRecycled()){
                    if(fLcl.exists())
                        fLcl.delete();
                    fLcl= new File(homeDirectoryFile, nameLcl);
                    saveToFile(fLcl,bitmapProperLcl);
                }
                final File finalFileLcl=fLcl;
                vLcl = (LinearLayout) inflater.inflate(R.layout.photo, null);
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            ImageView imageViewLcl = (ImageView) vLcl.findViewById(R.id.photo);//
                            imageViewLcl.setImageBitmap(bitmapReadyLcl);
                            ImageView crossLcl = (ImageView) vLcl.findViewById(R.id.x);
                            imageViewLcl.setOnClickListener(photosListener);
                            crossLcl.setOnClickListener(photosListener);
                            setViewParams(vLcl, displayWidth, photosBed.getHeight());
                            imageViewLcl.setTag(nameLcl);
                            crossLcl.setTag(nameLcl);
                            vLcl.setTag(nameLcl);
                            photosBed.addView(vLcl);
                            ViewTreeObserver vto = horizontalScroll.getViewTreeObserver();
                            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
                                @Override
                                public void onGlobalLayout() {
                                    horizontalScroll.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                    horizontalScroll.fullScroll(horizontalScroll.FOCUS_RIGHT);
//                                    horizontalScroll.scrollTo(tv.getWidth(), 0);
                                }
                            });
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    new PhotoPostHandler(activityPrm.getToken(),  activityPrm, finalFileLcl) ;
                                }
                            }).start();
                        }
                    });
            }
        }).start();
    }

    private void saveToFile(File fLcl, Bitmap bitmapProperPrm) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fLcl);
            bitmapProperPrm.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                    l.a("out.close();");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap resizeToProperBitmap(Bitmap bitmapPrm) {

        int wLcl=bitmapPrm.getWidth();
        int hLcl=bitmapPrm.getHeight();
        l.a( "w="+wLcl);
        l.a("h="+hLcl);
        int sizeLcl=wLcl*hLcl;
        double ratioLcl=sizeLcl/CRITICAL_SIZE;
        double dividerLcl = Math.sqrt(ratioLcl);
        Bitmap bitmapLcl=null;
        if(dividerLcl<=1){
            return bitmapPrm;
        }
        else{
            wLcl =(int)(wLcl/dividerLcl);
            hLcl =(int)(hLcl/dividerLcl);
            bitmapLcl=Bitmap.createScaledBitmap(bitmapPrm,wLcl,hLcl,false);
            bitmapPrm.recycle();
            l.a("         bitmapPrm.recycle();");
            return bitmapLcl;
        }
    }

    private String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor =getActivity(). getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }
    private Bitmap resizeBitmap(Bitmap bitmapPrm, boolean recyclePrm) {
        int widthLcl = bitmapPrm.getWidth();
        int heightLcl = bitmapPrm.getHeight();
        int heighTargettLcl = horizontalScroll.getHeight();
        float aspectRatioLcl = widthLcl * 1f / heightLcl;
        int widthTargetLcl = (int) (heighTargettLcl*aspectRatioLcl);
        Bitmap rezultBitmapLcl = Bitmap.createScaledBitmap(bitmapPrm, widthTargetLcl, heighTargettLcl, false);
        if(recyclePrm){
            bitmapPrm.recycle();
        }
        return rezultBitmapLcl;
    }

    private Uri getUri(File fPrm) {
        MainActivity activityLcl = (MainActivity) getActivity();
        Uri imageUriLcl = FileProvider.getUriForFile(activityLcl, activityLcl.getApplicationContext().getPackageName() +
                ".provider", fPrm);
        return imageUriLcl;
    }

    /**
     * Self explained.
     *
     * @param uriPrm A Uri which used to get the path to image file.
     * @return the path of image file.
     */

    private String getPathOfMediaFile(Uri uriPrm) {
        String[] projectionLcl = {MediaStore.Images.Media.DATA};
        String resultLcl = uriPrm.getPath();
        l.a(" uri=" + uriPrm.getPath());
        if (resultLcl.contains(".jpg") || resultLcl.contains(".JPG"))
            return resultLcl;
        resultLcl = "";
        Cursor cursorLcl = getActivity().getContentResolver().query(uriPrm, projectionLcl, null, null, null);
        if (cursorLcl.moveToFirst()) {
            int columnIndexLcl = cursorLcl.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            resultLcl = cursorLcl.getString(columnIndexLcl);
        }
        cursorLcl.close();
        if (resultLcl == "") {
            resultLcl = "Error in getting directory";
        }
        return resultLcl;
    }
}

//        private void myScrollTo(final int  x1Prm, final int x0Prm, final boolean sudaPrm) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    int x1Lcl = x1Prm
//                    ,x0Lcl=x0Prm;
//                    if(sudaPrm){
//                        while(x1Lcl<=x0Lcl){
//                            x0Lcl-=20;
//                            final int x00Lcl = x0Lcl;
//                            l.pause(20);
//                            if(x0Lcl<x1Lcl)
//                                x0Lcl=x1Lcl;
//                                horizontalScroll.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        horizontalScroll.scrollTo(x00Lcl,0);
//                                    }
//                                });
//
//                        }
//                    }
//                    buttonBlocked=false;
//                }
//            }).start();
//        }

//                    if (Build.VERSION.SDK_INT >= 23)
//                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
//                                != PackageManager.PERMISSION_GRANTED) {
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ((MainActivity) getActivity()).requestCameraPermission();
//                                }
//                            }).start();
//                            return;
//                        } else
//                            launchNativeCameraForResult(activityLcl);
//                    else
//        activityPrm.getContentResolver().notifyChange(selectedImageLcl, null);
//        ImageView imageView = (ImageView) findViewById(R.id.ImageView);
//        ContentResolver cr = getContentResolver();
//        Bitmap bitmap;
//        try {
//            bitmap = android.provider.MediaStore.Images.Media
//                    .getBitmap(cr, selectedImageLcl);
//
//            imageView.setImageBitmap(bitmap);
//            Toast.makeText(this, selectedImageLcl.toString(),
//                    Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
//                    .show();
//            Log.e("Camera", e.toString());
//        }
