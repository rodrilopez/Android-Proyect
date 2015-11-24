package com.groups.p2.saferider;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;


public class
        MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private String[] titulo;
    private DrawerLayout NavDrawerLayout;
    private ListView NavList;
    private ArrayList<item_objct> NavItms;
    private TypedArray NavIcons;
    NavigationAdapter NavAdapter;
    public static final int PICK_CONTACT_REQUEST = 1;
    private Uri contactUri;
    SQLiteAdapter sqlA = new SQLiteAdapter(this, "Config", null, 1);


    private EditText et1,et2,et3;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        NavDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavList = (ListView) findViewById(R.id.navigation_drawer);
        View header = getLayoutInflater().inflate(R.layout.header, null);
        NavList.addHeaderView(header);
        NavIcons = getResources().obtainTypedArray(R.array.nav_iconos);
        titulo = getResources().getStringArray(R.array.nav_options);
        NavItms= new ArrayList<item_objct>();
        NavItms.add(new item_objct(titulo[0], NavIcons.getResourceId(0, -1)));
        NavItms.add(new item_objct(titulo[1], NavIcons.getResourceId(1, -1)));
        NavItms.add(new item_objct(titulo[2], NavIcons.getResourceId(2, -1)));
        NavAdapter=new NavigationAdapter(this, NavItms);
        NavList.setAdapter(NavAdapter);

        NavList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                MostrarFragment(position + 1);
            }
        });
        try {
            MostrarFragment(1);
        }catch (Exception e){

        }
    }

    public void savePhone(View view){

        TextView et1 = (TextView) findViewById(R.id.textView11);

        String phone1 = et1.getText().toString();

        ContentValues newValues = new ContentValues();
        newValues.put("phone", phone1);

        sqlA.Actualizar(this, "Config", newValues, 1);
        Toast.makeText(getApplicationContext(), "The new Contact is Save", Toast.LENGTH_SHORT).show();
        MostrarFragment(1);
    }


    public void saveEmail(View view){



        EditText et1 = (EditText) findViewById(R.id.editText4);

        String item2 = et1.getText().toString();

        ContentValues newValues = new ContentValues();
        newValues.put("mail", item2);

        sqlA.Actualizar(this, "Config", newValues, 1);
        Toast.makeText(getApplicationContext(), "The new E-mail are Saved", Toast.LENGTH_SHORT).show();
        MostrarFragment(1);
    }

    public void savePassword(View view){

        String pass = sqlA.Leer(this,"Config",1,3);
        System.out.println(pass);

        EditText pas = (EditText) findViewById(R.id.editTextpassold);
        String pass1 = pas.getText().toString();

        if (pass.equals(pass1)){

            EditText pasn1 = (EditText) findViewById(R.id.editTextpassnew1);
            EditText pasn2 = (EditText) findViewById(R.id.editTextpassnew2);

            String newpass1 = pasn1.getText().toString();
            String newpass2 = pasn2.getText().toString();

            System.out.println("holaa");;

            if (newpass1.equals(newpass2)){

                ContentValues newValues = new ContentValues();
                newValues.put("password", newpass1);

                sqlA.Actualizar(this,"Config",newValues, 1);
                Toast.makeText(getApplicationContext(), "The new Password is Save", Toast.LENGTH_SHORT).show();
                MostrarFragment(1);Toast.makeText(getApplicationContext(), "The new Password is Save", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "The new Passwords aren't equals", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(), "The old Password is Incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    public void initPickContacts(View v){

        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(i, PICK_CONTACT_REQUEST);
    }



    private void renderContact(Uri uri){

        try {
            TextView contactPhone = (TextView)findViewById(R.id.textView10);

            contactPhone.setText(getPhone(uri));
        }catch (Exception a){
            TextView contactPhone = (TextView)findViewById(R.id.textView11);

            contactPhone.setText(getPhone(uri));
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {

                contactUri = intent.getData();

                renderContact(contactUri);
            }
        }
    }



    private String getPhone(Uri uri) {
    /*
    Variables temporales para el id y el teléfono
     */
        String id = null;
        String phone = null;

        /************* PRIMERA CONSULTA ************/
    /*
    Obtener el _ID del contacto
     */
        Context context = null;
        Cursor contactCursor = getContentResolver().query(
                uri,
                new String[]{ContactsContract.Contacts._ID},
                null,
                null,
                null);


        if (contactCursor.moveToFirst()) {
            id = contactCursor.getString(0);
        }
        contactCursor.close();

        /************* SEGUNDA CONSULTA ************/
    /*
    Sentencia WHERE para especificar que solo deseamos
    números de telefonía móvil
     */
        String selectionArgs =
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE+"= " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;

    /*
    Obtener el número telefónico
     */
        Cursor phoneCursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                selectionArgs,
                new String[]{id},
                null
        );
        if (phoneCursor.moveToFirst()) {
            phone = phoneCursor.getString(0);
        }
        phoneCursor.close();

        return phone;
    }



    public void Start(View view){
        String foo = sqlA.Leer(this,"Config",1,3);
        if (foo != null){
            Intent i = new Intent(this, MapsActivity.class);
            startActivity(i);
        }
    }

    public void Save(View view){

        et1 = (EditText) findViewById(R.id.editText5);
        et2 = (EditText) findViewById(R.id.editText6);
        et3 = (EditText) findViewById(R.id.editText7);
        TextView et5 = (TextView) findViewById(R.id.textView10);
        String name = et1.getText().toString();
        String email = et2.getText().toString();
        String pass = et3.getText().toString();
        String phone1 = et5.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("name", name);
        registro.put("mail", email);
        registro.put("password", pass);
        registro.put("phone", phone1);
        sqlA.Cargar(this, "Config", registro);
        Toast.makeText(getApplicationContext(), "Settings are saved", Toast.LENGTH_SHORT).show();
        MostrarFragment(1);

    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }


    private void MostrarFragment(int position){
        Fragment fragment= null;
        System.out.println(position);
        switch (position){
            case 1:
                fragment=new Home();
                break;
            case 2:
                fragment=new Home();
                break;
            case 3:
                SQLiteHelper admin = new SQLiteHelper(this, "Config", null, 1);
                SQLiteDatabase bd = admin.getReadableDatabase();
                Cursor fila = bd.rawQuery("select * from Config", null);
                if (fila.moveToFirst()){
                    fragment = new Settings2();

                }else {
                    fragment = new Settings();

                }
                break;
            case 4:
                fragment=new Help();
                break;
            default:
                fragment = new Home();
                position=1;
                break;
        }
        if (fragment!=null){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

            NavList.setItemChecked(position, true);
            NavList.setSelection(position);

            setTitle(titulo[position-1]);

            NavDrawerLayout.closeDrawer(NavList);
        }else {
            Log.e("Error ", "MostrarFregment" + position);
        }
    }


    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ff0e848b"));
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        actionBar.setBackgroundDrawable(colorDrawable);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.home, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).MostrarFragment(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
