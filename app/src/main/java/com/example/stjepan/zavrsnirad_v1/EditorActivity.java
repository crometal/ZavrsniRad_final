package com.example.stjepan.zavrsnirad_v1;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Dimension;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.stjepan.zavrsnirad_v1.data.FoodContract.FoodEntry;
import com.example.stjepan.zavrsnirad_v1.data.FoodDbHelper;


public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_FOOD_LOADER = 0;
    FoodDbHelper foodDbHelper;
    private Uri mCurrentFoodUri;
    private EditText nameEditText;
    private EditText fatTotalEditText;
    private EditText fatO3EditText;
    private EditText fatO6EditText;
    private EditText proteinEditText;
    private EditText carboEditText;
  //  private EditText fiberEditText;
    private EditText energyEditText;

    EditText convertedValue;
    RadioButton tokJ;
    RadioButton tokcal;

    /**Boolean flag that keeps track of whether the food has been edited or not*/
    private boolean mFoodHasChanged = false;

    /**OnTouchListener that listens for any user touches on a View, implying that they are modifying the view.
    Change the mFoodHadChanged boolean to true*/
    private View.OnTouchListener mTouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mFoodHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        /**Examine intent in order to figure out if user is creating food or editing food */
        Intent intent = getIntent();
        mCurrentFoodUri = intent.getData();

        if (mCurrentFoodUri == null){
            setTitle("Dodaj namirnicu");
            invalidateOptionsMenu();
        } else{
            setTitle("Izmjeni namirnicu");
            /** Loader is reading data from database. Display current values in the editor*/
            getLoaderManager().initLoader(EXISTING_FOOD_LOADER, null, this);
        }

        nameEditText = (EditText) findViewById(R.id.edit_foodName);
        fatTotalEditText = (EditText) findViewById(R.id.edit_fatTotal);
        fatO3EditText = (EditText) findViewById(R.id.edit_FatOmega3);
        fatO6EditText = (EditText) findViewById(R.id.edit_FatOmega6);
        proteinEditText = (EditText) findViewById(R.id.edit_Proteins);
        carboEditText = (EditText) findViewById(R.id.edit_Carbohydrates);
      //  fiberEditText = (EditText) findViewById(R.id.edit_fiber);
        energyEditText = (EditText) findViewById(R.id.edit_Energy);

        nameEditText.setOnTouchListener(mTouchListener);
        fatTotalEditText.setOnTouchListener(mTouchListener);
        fatO3EditText.setOnTouchListener(mTouchListener);
        fatO6EditText.setOnTouchListener(mTouchListener);
        proteinEditText.setOnTouchListener(mTouchListener);
        carboEditText .setOnTouchListener(mTouchListener);
     //   fiberEditText.setOnTouchListener(mTouchListener);
        energyEditText.setOnTouchListener(mTouchListener);

        /** CONVERTER SECTION*/
        convertedValue = (EditText) findViewById(R.id.edit_ConvertValue);
        tokcal = (RadioButton) findViewById(R.id.radioKJ2KCAL);
        tokJ = (RadioButton) findViewById(R.id.radioKCAL2KJ);
    }

        /** Get user input from editor and save new food into database*/
        private void saveFood(){
        String nameString = nameEditText.getText().toString().trim();
        String fatTotalString = fatTotalEditText.getText().toString().trim();
        String fatOmega3String = fatO3EditText.getText().toString().trim();
        String fatOmega6String = fatO6EditText.getText().toString().trim();
        String proteinsString = proteinEditText.getText().toString().trim();
        String carbohydratesString = carboEditText.getText().toString().trim();
      //  String fibersString = fiberEditText.getText().toString().trim();
        String energyString = energyEditText.getText().toString().trim();

            if (mCurrentFoodUri == null &&
                    TextUtils.isEmpty(nameString) &&
                    TextUtils.isEmpty(fatTotalString) &&
                    TextUtils.isEmpty(fatOmega3String) &&
                    TextUtils.isEmpty(fatOmega6String) &&
                    TextUtils.isEmpty(proteinsString) &&
                    TextUtils.isEmpty(carbohydratesString) &&
                 //   TextUtils.isEmpty(fibersString) &&
                    TextUtils.isEmpty(energyString)){
                return;
            }

    /** Create ContentValues objects where column names are the keys and the attributes from the editor are the values*/
        ContentValues values = new ContentValues();
        values.put(FoodEntry.COLUMN_FOOD_NAME, nameString);
        values.put(FoodEntry.COLUMN_FAT_TOTAL, fatTotalString);
        values.put(FoodEntry.COLUMN_OMEGA3, fatOmega3String);
        values.put(FoodEntry.COLUMN_OMEGA6, fatOmega6String);
        values.put(FoodEntry.COLUMN_PROTEINS, proteinsString);
        values.put(FoodEntry.COLUMN_CARBOHYDRATES, carbohydratesString);
      //  values.put(FoodEntry.COLUMN_FIBERS, fibersString);
        values.put(FoodEntry.COLUMN_ENERGY, energyString);



            /** If omegas, carbo, energy, fibers are not provided by user, don't try to parse. Use 0 as default  */
            /** !!!!!!! DO FOR OTHERS INPUTS !!!!!!!*/
        int noDataProvided = 0;
            if (!TextUtils.isEmpty(proteinsString)){
                noDataProvided = Integer.parseInt(proteinsString);
            }
            values.put(FoodEntry.COLUMN_PROTEINS, noDataProvided);

            /** Determine if this is a new or existing food*/
            if (mCurrentFoodUri == null){
                /** This is a new FOOD*/
                Uri newUri = getContentResolver().insert(FoodEntry.CONTENT_URI, values);
                if (newUri == null){
                    Toast.makeText(this, "Pogreška kod unosa", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(this, "Unos spremnjen", Toast.LENGTH_SHORT).show();
                }
            } else {
                /** This is existing food*/
                int rowsAffected = getContentResolver().update(mCurrentFoodUri, values, null, null);

                if (rowsAffected == 0){
                    Toast.makeText(this, "Pogreška kod izmjene", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Izmjena spremnjena", Toast.LENGTH_SHORT).show();
                }
            }
    }

    /**This method is called after invalidateOptionsMenu*/
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mCurrentFoodUri == null){
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    /** This method adds menu items to the top bar */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                saveFood();
                finish();
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                if (!mFoodHasChanged){
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                /** If there are unsaved data, setup a dialog to warn user*/
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** This method is called when the back button is pressed*/
    @Override
    public void onBackPressed() {
        if (!mFoodHasChanged){
        super.onBackPressed();
        return;
    }
        /** If there are unsaved changes, setup a dialog to warn user*/
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };
    showUnsavedChangesDialog(discardButtonClickListener);
}


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String [] projection = {
                FoodEntry._ID,
                FoodEntry.COLUMN_FOOD_NAME,
                FoodEntry.COLUMN_FAT_TOTAL,
                FoodEntry.COLUMN_OMEGA3,
                FoodEntry.COLUMN_OMEGA6,
                FoodEntry.COLUMN_PROTEINS,
                FoodEntry.COLUMN_CARBOHYDRATES,
                FoodEntry.COLUMN_ENERGY

        };
        return new CursorLoader(this, mCurrentFoodUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1){
            return;
        }

        if (cursor.moveToFirst()){
            int nameColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_FOOD_NAME);
            double fatColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_FAT_TOTAL);
            double omega3ColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_OMEGA3);
            double omega6ColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_OMEGA6);
            double proteinsColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_PROTEINS);
            double carboColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_CARBOHYDRATES);
           // double fibersColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_FIBERS);
            int energyColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_ENERGY);

            String name = cursor.getString(nameColumnIndex);
            double fat = cursor.getDouble ((int) fatColumnIndex);
            double omega3 = cursor.getDouble ((int) omega3ColumnIndex);
            double omega6 = cursor.getDouble((int)omega6ColumnIndex);
            double proteins = cursor.getDouble((int)proteinsColumnIndex);
            double carbo = cursor.getDouble((int)carboColumnIndex);
          //  double fibers = cursor.getDouble((int)fibersColumnIndex);
            int energy = cursor.getInt ((energyColumnIndex));

            nameEditText.setText(name);
            fatTotalEditText.setText(Double.toString(fat));
            fatO3EditText.setText(Double.toString(omega3));
            fatO6EditText.setText(Double.toString(omega6));
            proteinEditText.setText(Double.toString(proteins));
            carboEditText.setText(Double.toString(carbo));
           // fiberEditText.setText(Double.toString(fibers));
            energyEditText.setText(Integer.toString(energy));


        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        nameEditText.setText("");
        fatO3EditText.setText("");
        fatO6EditText.setText("");
        proteinEditText.setText("");
        carboEditText.setText("");
        energyEditText.setText("");
       // fiberEditText.setText("");
    }

    private void showUnsavedChangesDialog(
        DialogInterface.OnClickListener discardButtonClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Odbaci unesene promjene i izađi?");
        builder.setPositiveButton("Odbaci", discardButtonClickListener);
        builder.setNegativeButton("Nastavi sa uređivanjem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null){
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /** Promt the user to confirm that he want to delete food*/
    private void showDeleteConfirmationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Obriši ovu namirnicu?");
        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFood();
            }
        });
        builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null){
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /** Perform the delete action*/
    private void deleteFood(){
        if (mCurrentFoodUri != null){
            int rowsDeleted = getContentResolver().delete(mCurrentFoodUri, null, null);

            if (rowsDeleted == 0){
                Toast.makeText(this, "Pogreška pri brisanju", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Obrisano", Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

    public void convert(View v){
        double value = new Double (convertedValue.getText().toString());
        if (tokJ.isChecked()) {
            value = UnitConverter.kcal2kj(value);
        }  else{
            value = UnitConverter.kj2kcal(value);}
        convertedValue.setText(new Double(value).toString());
    }

}
