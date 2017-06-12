package com.hongzhiyuanzj.newercm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.zxing.client.android.CaptureActivity;
import com.hongzhiyuanzj.newercm.adapter.MyViewPageAdapter;
import com.hongzhiyuanzj.newercm.base.ToolbarActivity;
import com.hongzhiyuanzj.newercm.fragment.ChoosePhotoDialog;
import com.hongzhiyuanzj.newercm.fragment.LibraryFragment;
import com.hongzhiyuanzj.newercm.fragment.MineFragment;
import com.hongzhiyuanzj.newercm.fragment.RecommendFragment;
import com.hongzhiyuanzj.newercm.fragment.ShelfFragment;
import com.hongzhiyuanzj.newercm.ui.LoginActivity;
import com.hongzhiyuanzj.newercm.ui.SearchActivity;
import com.hongzhiyuanzj.newercm.util.FileHelper;
import com.hongzhiyuanzj.newercm.util.Prefer;
import com.hongzhiyuanzj.newercm.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends ToolbarActivity{

    private final int CAMERA_PERMISSION = 1;

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    private MenuItem scanner;
    private MenuItem search;;
    private Fragment book_shelf, recommend,library, mine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //*********************
//        Prefer.setLogin(true);
        //*******************
        ButterKnife.bind(this);
        initActionBar();
        initViewPager();
    }


    private void initActionBar(){
        setTitle(R.string.app_name);
        setDisplayHomeAsUpEnabled(false);
    }
    private void initViewPager(){
        List<Fragment> fragmentList = new ArrayList<>();
        book_shelf = new ShelfFragment();
        fragmentList.add(book_shelf);
        recommend = new RecommendFragment();
        fragmentList.add(recommend);
        library = new LibraryFragment();
        fragmentList.add(library);
        mine = new MineFragment();
        fragmentList.add(mine);
        viewPager.setAdapter(new MyViewPageAdapter(fragmentList, getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                checkMenuItemIsVisiable(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.getTabAt(0).setText(R.string.bookshelf).setIcon(Utils.setDrawableTint(getResources().getDrawable(R.drawable.ic_local_library_black_24dp),
                getResources().getColorStateList(R.color.main_tab_color_selector)));
        tabLayout.getTabAt(1).setText(R.string.recommend).setIcon(Utils.setDrawableTint(getResources().getDrawable(R.drawable.ic_star_black_24dp),
                getResources().getColorStateList(R.color.main_tab_color_selector)));
        tabLayout.getTabAt(2).setText(R.string.library).setIcon(Utils.setDrawableTint(getResources().getDrawable(R.drawable.ic_location_city_black_24dp),
                getResources().getColorStateList(R.color.main_tab_color_selector)));
        tabLayout.getTabAt(3).setText(R.string.mine).setIcon(Utils.setDrawableTint(getResources().getDrawable(R.drawable.ic_person_black_24dp),
                getResources().getColorStateList(R.color.main_tab_color_selector))
        );

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkMenuItemIsVisiable(viewPager.getCurrentItem());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("Activity", "onCreateOptionMenu");
        getMenuInflater().inflate(R.menu.zxing, menu);
        scanner = menu.findItem(R.id.start_zxing);
        search = menu.findItem(R.id.search);
        scanner.setIcon(Utils.setDrawableTint(scanner.getIcon(), getResources().getColor(R.color.textIcon)));
        search.setIcon(Utils.setDrawableTint(search.getIcon(),getResources().getColor(R.color.textIcon)));

        checkMenuItemIsVisiable(viewPager.getCurrentItem());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("Activity", "onOptionsItemSelected");

        switch (item.getItemId()){
            case R.id.start_zxing:
                requestPermission();
                break;
            case R.id.search:
                SearchActivity.start(this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changePage(int position){
        viewPager.setCurrentItem(position);
    }

    //改变登录状态时，修改所有界面
    public void initUI(){
        ((ShelfFragment)book_shelf).initBookShelf();
        ((RecommendFragment)recommend).getRecommendDir();
        ((MineFragment)mine).init();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CaptureActivity.ZXING_REQUEST_CODE && resultCode == RESULT_OK && data!=null){
            String capture_data = data.getStringExtra(CaptureActivity.EXTRA_DATA);
        }
        if(requestCode == ChoosePhotoDialog.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data!=null){

            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            ((MineFragment)mine).setHeadPhoto(bitmap);
            FileHelper.getInstance().saveHeadphoto(bitmap);
        }
        if(requestCode == ChoosePhotoDialog.REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data!=null){
            Uri uri = data.getData();
            FileHelper.getInstance().saveHeadphoto(uri);
            ((MineFragment)mine).setHeadPhoto(FileHelper.getInstance().getHeadphoto());


        }
    }

    private void checkMenuItemIsVisiable(int position){

        if(position==2){
            search.setVisible(true);
            scanner.setVisible(false);
        }
        if(position==0){
            search.setVisible(false);
            scanner.setVisible(true);
        }
        if(position==3||position==1){
            search.setVisible(false);
            scanner.setVisible(false);
        }
        if(!Prefer.isLogin()){
            scanner.setVisible(false);
        }
    }

    private void requestPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
            }
        }else{
            startActivityForResult(new Intent(this, CaptureActivity.class), CaptureActivity.ZXING_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_PERMISSION:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startActivityForResult(new Intent(this, CaptureActivity.class), CaptureActivity.ZXING_REQUEST_CODE);
                }else{
                    Utils.showToast("您已经拒绝了相机权限，请在权限管理中设置");
                }
                return;
            }
        }
    }

    public Fragment getFragment(Class<? extends Fragment> tClass){
        if(tClass.getSimpleName().equals("ShelfFragment")){
            return book_shelf;
        }
        if(tClass.getSimpleName().equals("MineFragment")){
            return mine;
        }
        return null;
    }


}
