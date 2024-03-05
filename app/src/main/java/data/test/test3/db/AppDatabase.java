package data.test.test3.db;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import data.test.test3.db.dao.BookDao;
import data.test.test3.db.entity.BookEntity;

@Database(entities = {BookEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookDao bookDao();

    private static AppDatabase sInstance;
    private static final String DB_NAME = "FUMIC";

    public static synchronized AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME)
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            // Read data from CSV and insert into database
                            Executors.newSingleThreadExecutor().execute(() -> {
                                readAndInsertDataFromCSV(context);

                            });
                        }

                        @Override
                        public void onOpen(SupportSQLiteDatabase db) {
                            super.onOpen(db);

                            Executors.newSingleThreadExecutor().execute(() -> {
                                //readAndInsertDataFromCSV(context);

                            });
                        }
                    })
                    .build();
        }
        return sInstance;
    }

    private static void readAndInsertDataFromCSV(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("test3.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    int id = Integer.parseInt(data[0]);
                    String title = data[1];
                    String author = data[2];
                    // Insert data into database
                    sInstance.bookDao().insertBook(new BookEntity(id, title, author));
                }
            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*@Database(entities = {BookEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookDao bookDao();

    private static AppDatabase sInstance;
    private static final String DB_NAME = "FUMIC";

    /*public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    public static synchronized AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .createFromAsset("test3.csv")
                    .build();
            Log.d(TAG, "Database has been created: " + DB_NAME);
        }
        return sInstance;
    }

    public static void readCSV(Context context, String fileName) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Log.d("CSV Data", data[0] + "," + data[1]);
            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final RoomDatabase.Callback callback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    Executors.newSingleThreadExecutor().execute(() -> {
                        BookDao dao = sInstance.bookDao();
                        try {
                            dao.deleteAllBooks();
                        } catch (Exception e) {
                            Log.e("AppDatabase", "Error inserting data", e);
                        }
                    });
                }
                
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {

                }
                
            };

    private static AppDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DB_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            BookDao dao = sInstance.bookDao();
                            try {
                                dao.deleteAllBooks();
                            } catch (Exception e) {
                                Log.e("AppDatabase", "Error inserting data", e);
                            }
                        });
                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            BookDao dao = sInstance.bookDao();
                            try {
                                dao.deleteAllBooks();
                            } catch (Exception e) {
                                Log.e("AppDatabase", "Error inserting data", e);
                            }
                        });
                    }
                }).build();
    }

    private static void insertData(final AppDatabase database, final BookEntity book) {
        database.runInTransaction(() -> {
            database.bookDao().insertBook(book);
        });
    }




}*/
