package com.easylink.nj.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.easylink.nj.bean.db.Cart;
import com.easylink.nj.bean.db.Order;
import com.easylink.nj.bean.db.User;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class DBManager {

    private static DBManager mDBMgr;

    private DBManager() {

    }

    public static synchronized DBManager getInstance() {

        if (mDBMgr == null)
            mDBMgr = new DBManager();
        return mDBMgr;
    }

    public synchronized Cart getCart(String productId) {

        try {

            return new Select().from(Cart.class).where("productId = ?", productId).executeSingle();
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public synchronized List<Cart> getCarts() {

        try {

            return new Select().from(Cart.class).orderBy("time DESC").execute();
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public synchronized boolean hasCart(String productId) {

        try {

            return new Select().from(Cart.class).where("productId = ?", productId).exists();
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public synchronized int getCartCount() {

        SQLiteDatabase db = ActiveAndroid.getDatabase();
        if (db == null)
            return 0;
        String sql = "SELECT total(count) FROM Cart";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor == null)
            return 0;
        if (!cursor.isFirst())
            cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    public synchronized void clearCart() {

        try {
            new Delete().from(Cart.class).execute();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public synchronized void clearOrder() {

        try {
            new Delete().from(Order.class).execute();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public synchronized void clearUser() {

        try {
            new Delete().from(User.class).execute();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
