package com.example.stjepan.zavrsnirad_v1.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.stjepan.zavrsnirad_v1.data.FoodContract.FoodEntry;


public class FoodProvider extends ContentProvider {

    public static final String LOG_TAG = FoodProvider.class.getSimpleName();
    /**
     * URI matcher code for the content URI for the foodlist table
     */
    private static final int FOOD = 100;

    /**
     * URI matcher code for the content URI for a single food in the foodlist table
     */
    private static final int FOOD_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        URI_MATCHER.addURI(FoodContract.CONTENT_AUTHORITY, FoodContract.PATH_FOOD, FOOD);
        URI_MATCHER.addURI(FoodContract.CONTENT_AUTHORITY, FoodContract.PATH_FOOD + "/#", FOOD_ID);
    }

    private FoodDbHelper foodDbHelper;

    @Override
    public boolean onCreate() {
        foodDbHelper = new FoodDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase database = foodDbHelper.getReadableDatabase();
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case FOOD:
                cursor = database.query(FoodEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, FoodEntry.COLUMN_FOOD_NAME + " ASC");
                break;
            case FOOD_ID:
                selection = FoodEntry._ID_FOODS + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(FoodEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, FoodEntry.COLUMN_FOOD_NAME + " ASC");
                break;
           default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = URI_MATCHER.match(uri);

        switch (match) {
            case FOOD:

                return insertFood(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a food into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertFood(Uri uri, ContentValues values) {
        String name = values.getAsString(FoodEntry.COLUMN_FOOD_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Food requires a name");
        }

        Double fat = values.getAsDouble(FoodEntry.COLUMN_FAT_TOTAL);
        if (fat != null && fat < 0) {
            throw new IllegalArgumentException("Food requires valid fat value");
        }

        SQLiteDatabase database = foodDbHelper.getWritableDatabase();

        //Insert new food with the given values
        long id = database.insert(FoodEntry.TABLE_NAME, null, values);

        //If the id is -1 then the insertion failed
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        //Notify all listeners that the data has changed for the food content URI
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = foodDbHelper.getWritableDatabase();


        int rowsDeleted;
        final int match = URI_MATCHER.match(uri);

        switch (match) {
            case FOOD:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(FoodEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FOOD_ID:
                // Delete a single row given by the ID in the URI
                selection = FoodEntry._ID_FOODS + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(FoodEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case FOOD:
                return updateFood(uri, contentValues, selection, selectionArgs);
            case FOOD_ID:
                selection = FoodEntry._ID_FOODS + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateFood(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateFood(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(FoodEntry.COLUMN_FOOD_NAME)) {
            String name = values.getAsString(FoodEntry.COLUMN_FOOD_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Food requires a name");
            }
        }

        if (values.containsKey(FoodEntry.COLUMN_FAT_TOTAL)) {
            Integer fat = values.getAsInteger(FoodEntry.COLUMN_FAT_TOTAL);
            if (fat != null && fat < 0) {
                throw new IllegalArgumentException("Food requires valid fat value");
            }
        }
        /** If there are not values to update, then dont try to update the database*/
        if (values.size() == 0) {
            return 0;
        }

        /** Otherwise get writable database to update the data*/
        SQLiteDatabase database = foodDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(FoodEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }

    @Override
    public String getType(Uri uri) {
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case FOOD:
                return FoodEntry.CONTENT_LIST_TYPE;
            case FOOD_ID:
                return FoodEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }

    }
}
