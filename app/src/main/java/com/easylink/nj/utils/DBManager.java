package com.easylink.nj.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.easylink.nj.bean.db.Address;
import com.easylink.nj.bean.db.Cart;
import com.easylink.nj.bean.db.Order;

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

            return new Select().from(Cart.class).where("orderId IS NULL AND productId = ?", productId).executeSingle();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public synchronized List<Cart> getCarts() {

        try {

            return new Select().from(Cart.class).where("orderId IS NULL").orderBy(Table.DEFAULT_ID_NAME + " DESC").execute();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public synchronized int getCartCount() {

        SQLiteDatabase db = ActiveAndroid.getDatabase();
        if (db == null)
            return 0;
        String sql = "SELECT total(count) FROM Cart WHERE orderId IS NULL";
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

    public synchronized void clearAddress() {

        try {
            new Delete().from(Address.class).execute();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public synchronized Address getDefaultAddress() {

        try {

            return new Select().from(Address.class).orderBy(Table.DEFAULT_ID_NAME + " DESC").limit(1).where("isDefault = ?", true).executeSingle();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public synchronized List<Address> getAddresses() {

        try {

            return new Select().from(Address.class).orderBy(Table.DEFAULT_ID_NAME + " DESC").execute();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public synchronized Address getAddress(long id) {

        try {

            return new Select().from(Address.class).where(Table.DEFAULT_ID_NAME + " = ?", id).executeSingle();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public synchronized Order getOrder() {

        try {

            Order order = new Select().from(Order.class).orderBy("time DESC").limit(1).executeSingle();

            if (order != null)
                order.carts = new Select().from(Cart.class).where("orderId = ?", order.orderId).orderBy(Table.DEFAULT_ID_NAME + " DESC").execute();

            return order;
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}
