package com.funnyApp.view.fragment.profile;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.funnyApp.R;
import com.funnyApp.helper.ImagePicker;
import com.funnyApp.helper.UIHelper;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateArticleFragment extends Fragment {

    private boolean mImageStatus=false;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE_ID = 234;
    private static final int CAMERA_REQUEST = 1888;
    final int PIC_CROP = 2;
    private Uri mPicUri;
    SearchableSpinner mSpCategory;
    String[] mCategory = {"فیلتر بر اساس دسته بندی مطلب", "مولتی مدیا","سخت افزار","برنامه نویسی","شبکه","گرافیک","نرم افزار"};

    public CreateArticleFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_create_article, container, false);
        ButterKnife.bind(this,view);

        configCategory(view);
        return view;
    }

    private void configCategory(View view) {
        mSpCategory = (SearchableSpinner) view.findViewById(R.id.sp_category);
        ArrayAdapter<String> adapteRregion = new ArrayAdapter<String>(getActivity(), R.layout.spineer_item, mCategory);
        adapteRregion.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);

        mSpCategory.setAdapter(adapteRregion);

        mSpCategory.setTitle("انتخاب");
        mSpCategory.setPositiveButton("خروج");
        mSpCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_IMAGE_ID:

                mPicUri = data.getData();
                Bitmap bitmap = ImagePicker.getImageFromResult(getActivity(), resultCode, data);

                break;
            case PIC_CROP:
                try {

                    mPicUri = data.getData();
                    Bundle extras = data.getExtras();
                    Bitmap thePic = extras.getParcelable("data");


                } catch (RuntimeException e) {
                    Log.i("The Error is:",e.toString());
                }
                break;
            case CAMERA_REQUEST:
                if (requestCode == 1888 && resultCode == Activity.RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    //mImagePic.setBackgroundColor(getResources().getColor(R.color.white));
                    //mImagePic.setImageBitmap(photo);
                    //call the presenter here to make networkCall
                }
                break;


            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    //Checking permition for android version 6
    public void checksCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("MyApp", "SDK >= 23");
            if (getActivity().checkSelfPermission(android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d("MyApp", "Request permission");
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.CAMERA},
                        REQUEST_IMAGE_CAPTURE);

                if (! shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                    Toast.makeText(getActivity(),"شما باید اجازه دسترسی به دوربین را صادر کنید",Toast.LENGTH_LONG).show();
                }
            }
            else {
                Log.d("MyApp", "Permission granted: taking pic");
                takeImage();
            }
        }
        else {
            takeImage();
            Log.d("MyApp", "Android < 6.0");
        }
    }
    //start image from camera
    private void takeImage() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1888);
    }
    private void performCrop() {
        try {

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(mPicUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, PIC_CROP);

        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
            Intent chooseImageIntent = ImagePicker.getPickImageIntent(getActivity());
            startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode== 1 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getActivity(),"granted well",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity(),"not granted",Toast.LENGTH_LONG).show();
        }

    }


    @OnClick(R.id.btnCreateArticle)
    public void btnCreateArticle_OnClick(){
        if (!mImageStatus){
            UIHelper.simpleDialogBox("مطلب شما بدون عکس منتشر شد.",getActivity(),R.layout.dialog_ok_box,"پیام");
        }

    }
    @OnClick(R.id.btnSelectPic)
    public void btnSelectPic_OnClick(){
        final CharSequence[] items = {"گرفتن عکس", "انتخاب عکس از گالری", "لغو"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("انتخاب");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("گرفتن عکس")) {
                    checksCameraPermission();

                } else if (items[item].equals("انتخاب عکس از گالری")) {
                    try {
                        performCrop();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e + "", Toast.LENGTH_LONG).show();
                    }
                } else if (items[item].equals("لغو")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}
