package com.example.forum.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.forum.model.pojo.Plate;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PlateDao_Impl implements PlateDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Plate> __insertionAdapterOfPlate;

  private final EntityDeletionOrUpdateAdapter<Plate> __updateAdapterOfPlate;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public PlateDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlate = new EntityInsertionAdapter<Plate>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Plate` (`plateId`,`name`,`userId`,`postCount`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Plate value) {
        stmt.bindLong(1, value.getPlateId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getUserId());
        stmt.bindLong(4, value.getPostCount());
      }
    };
    this.__updateAdapterOfPlate = new EntityDeletionOrUpdateAdapter<Plate>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Plate` SET `plateId` = ?,`name` = ?,`userId` = ?,`postCount` = ? WHERE `plateId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Plate value) {
        stmt.bindLong(1, value.getPlateId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getUserId());
        stmt.bindLong(4, value.getPostCount());
        stmt.bindLong(5, value.getPlateId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from Plate where 1=1";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Plate plate) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPlate.insert(plate);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Plate plate) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfPlate.handle(plate);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<Plate> queryAll() {
    final String _sql = "select * from Plate";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfPlateId = CursorUtil.getColumnIndexOrThrow(_cursor, "plateId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfPostCount = CursorUtil.getColumnIndexOrThrow(_cursor, "postCount");
      final List<Plate> _result = new ArrayList<Plate>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Plate _item;
        _item = new Plate();
        final long _tmpPlateId;
        _tmpPlateId = _cursor.getLong(_cursorIndexOfPlateId);
        _item.setPlateId(_tmpPlateId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _item.setName(_tmpName);
        final long _tmpUserId;
        _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
        _item.setUserId(_tmpUserId);
        final int _tmpPostCount;
        _tmpPostCount = _cursor.getInt(_cursorIndexOfPostCount);
        _item.setPostCount(_tmpPostCount);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
