package com.nuaa233.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nuaa233.sqlite.Favorite.MyFavActivity;
import com.nuaa233.sqlite.Main.AboutFragment;
import com.nuaa233.sqlite.Main.BillFragment;
import com.nuaa233.sqlite.Main.CalorieFragment;
import com.nuaa233.sqlite.Main.CanteenActivity;
import com.nuaa233.sqlite.Main.RankFragment;
import com.nuaa233.sqlite.Main.ShuoShuoActivity;
import com.nuaa233.sqlite.Main.UsrInfoFragment;

import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

public class TrueMainActivity extends AppCompatActivity {

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;

    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_main);

        //可设置最初显示的界面
        addFragmentToStack(new UsrInfoFragment());

        Log.d("hallo","test1");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String id = bundle.getString("id");
        String pwd = bundle.getString("pwd");

        Log.d("hallo","test2 "+id +" "+pwd);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.NUAACanteen);

        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        String nickname = new String("test");
        nickname = initData(id);
        final IProfile profile1 = new ProfileDrawerItem().withName(nickname).withEmail(id).withIcon(R.mipmap.profile3).withIdentifier(100);
        //final IProfile profile2 = new ProfileDrawerItem().withName(nickname).withEmail("alorma@github.com").withIcon(R.mipmap.profile).withIdentifier(101);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.mipmap.six_1)
                .addProfiles(
                        profile1
                )
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withDrawerLayout(R.layout.crossfade_drawer)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_disscussion).withIcon(FontAwesome.Icon.faw_pencil_square).withIdentifier(4),
                        new PrimaryDrawerItem().withName(R.string.drawer_canteen).withIcon(FontAwesome.Icon.faw_university).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_favorite).withIcon(FontAwesome.Icon.faw_heart).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_bill).withIcon(FontAwesome.Icon.faw_credit_card).withIdentifier(3),
                        new PrimaryDrawerItem().withName("我的卡路里").withIcon(FontAwesome.Icon.faw_fire).withIdentifier(8),
                        new PrimaryDrawerItem().withName(R.string.rank).withIcon(FontAwesome.Icon.faw_trophy).withIdentifier(5),
                        new PrimaryDrawerItem().withName(R.string.drawer_info).withIcon(FontAwesome.Icon.faw_id_card).withIdentifier(6),
                        new SectionDrawerItem().withName("其他"),
                        new SecondaryDrawerItem().withName(R.string.drawer_about).withIcon(FontAwesome.Icon.faw_info_circle).withIdentifier(7)
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                result.closeDrawer();
                                intent = new Intent(TrueMainActivity.this, CanteenActivity.class);
                                Bundle b = new Bundle();
                                b.putString("id", id);
                                intent.putExtras(b);
                            } else if (drawerItem.getIdentifier() == 2) {
                                result.closeDrawer();
                                intent = new Intent(TrueMainActivity.this, MyFavActivity.class);
                                Bundle b = new Bundle();
                                b.putString("id", id);
                                intent.putExtras(b);
                            } else if (drawerItem.getIdentifier() == 3) {
                                result.closeDrawer();
                                BillFragment mfragment = new BillFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mfragment).commit();
                            } else if (drawerItem.getIdentifier() == 4) {
                                result.closeDrawer();
                                intent = new Intent(TrueMainActivity.this, ShuoShuoActivity.class);
                                Bundle b = new Bundle();
                                b.putString("id", id);
                                intent.putExtras(b);
                            } else if (drawerItem.getIdentifier() == 5) {
                                result.closeDrawer();
                                RankFragment mfragment = new RankFragment();
                                Bundle bun = new Bundle();
                                bun.putString("id", id);
                                mfragment.setArguments(bun);
                                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mfragment).commit();
                            } else if (drawerItem.getIdentifier() == 6) {
                                result.closeDrawer();
                                Log.d("hallo", "test3");
                                UsrInfoFragment mfragment = new UsrInfoFragment();
                                Bundle bun = new Bundle();
                                bun.putString("id", id);
                                mfragment.setArguments(bun);
                                Log.d("hallo", "test4 " + bun.getString("id"));
                                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mfragment).commit();
                            } else if (drawerItem.getIdentifier() == 7) {
                                result.closeDrawer();
                                addFragmentToStack(new AboutFragment());
                            } else if (drawerItem.getIdentifier() == 8) {
                                result.closeDrawer();
                                CalorieFragment mfragment = new CalorieFragment();
                                Bundle bun = new Bundle();
                                bun.putString("id", id);
                                mfragment.setArguments(bun);
                                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mfragment).commit();
                            }
                            if (intent != null) {
                                TrueMainActivity.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();


        //get the CrossfadeDrawerLayout which will be used as alternative DrawerLayout for the Drawer
        //the CrossfadeDrawerLayout library can be found here: https://github.com/mikepenz/CrossfadeDrawerLayout
        crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();

        //define maxDrawerWidth
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        //add second view (which is the miniDrawer)
        final MiniDrawer miniResult = result.getMiniDrawer();
        //build the view for the MiniDrawer
        View view = miniResult.build(this);
        //set the background of the MiniDrawer as this would be transparent
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
        //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                //only close the drawer if we were already faded and want to close it now
                if (isFaded) {
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    //########################Menu###################################
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.quickview, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                crossfadeDrawerLayout.openDrawer(GravityCompat.START);
//                break;
//            case R.id.canteen_1:
//                Toast.makeText(this, "This is Canteen 1", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.canteen_2:
//                Toast.makeText(this, "This is Canteen 2", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.canteen_3:
//                Toast.makeText(this, "This is Canteen 3", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.canteen_4:
//                Toast.makeText(this, "This is Canteen 4", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//        }
//        return true;
//    }

    private void addFragmentToStack(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    private String initData(String Id) {
        String nickname = new String("123");
        dbHelper = new MyDatabaseHelper(this, "Info.db", null, 1);
        db = dbHelper.getWritableDatabase();
        cursor = db.rawQuery("select * from Usr_Info where _id like ?", new String[] {Id});
        if (cursor.moveToFirst()) {
            nickname = cursor.getString(cursor.getColumnIndex("nickname"));
        }

        return nickname;
    }
}