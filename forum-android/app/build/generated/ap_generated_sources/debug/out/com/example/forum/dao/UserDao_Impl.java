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
import com.example.forum.model.pojo.User;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __updateAdapterOfUser;

  private final SharedSQLiteStatement __preparedStmtOfDeleteUser;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `User` (`id`,`name`,`pass`,`grade`,`gender`,`birthday`,`avatar`,`available`,`token`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getPass() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPass());
        }
        stmt.bindLong(4, value.getGrade());
        stmt.bindLong(5, value.getGender());
        if (value.getBirthday() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getBirthday());
        }
        if (value.getAvatar() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getAvatar());
        }
        final int _tmp = value.isAvailable() ? 1 : 0;
        stmt.bindLong(8, _tmp);
        if (value.getToken() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getToken());
        }
      }
    };
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `User` SET `id` = ?,`name` = ?,`pass` = ?,`grade` = ?,`gender` = ?,`birthday` = ?,`avatar` = ?,`available` = ?,`token` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getPass() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPass());
        }
        stmt.bindLong(4, value.getGrade());
        stmt.bindLong(5, value.getGender());
        if (value.getBirthday() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getBirthday());
        }
        if (value.getAvatar() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getAvatar());
        }
        final int _tmp = value.isAvailable() ? 1 : 0;
        stmt.bindLong(8, _tmp);
        if (value.getToken() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getToken());
        }
        stmt.bindLong(10, value.getId());
      }
    };
    this.__preparedStmtOfDeleteUser = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from User where 1=1";
        return _query;
      }
    };
  }

  @Override
  public void insert(final User user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser.insert(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final User user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfUser.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteUser() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteUser.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteUser.release(_stmt);
    }
  }

  @Override
  public User query() {
    final String _sql = "select * from User";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfPass = CursorUtil.getColumnIndexOrThrow(_cursor, "pass");
      final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
      final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
      final int _cursorIndexOfBirthday = CursorUtil.getColumnIndexOrThrow(_cursor, "birthday");
      final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
      final int _cursorIndexOfAvailable = CursorUtil.getColumnIndexOrThrow(_cursor, "available");
      final int _cursorIndexOfToken = CursorUtil.getColumnIndexOrThrow(_cursor, "token");
      final User _result;
      if(_cursor.moveToFirst()) {
        _result = new User();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _result.setName(_tmpName);
        final String _tmpPass;
        if (_cursor.isNull(_cursorIndexOfPass)) {
          _tmpPass = null;
        } else {
          _tmpPass = _cursor.getString(_cursorIndexOfPass);
        }
        _result.setPass(_tmpPass);
        final byte _tmpGrade;
        _tmpGrade = (byte) _cursor.getShort(_cursorIndexOfGrade);
        _result.setGrade(_tmpGrade);
        final byte _tmpGender;
        _tmpGender = (byte) _cursor.getShort(_cursorIndexOfGender);
        _result.setGender(_tmpGender);
        final String _tmpBirthday;
        if (_cursor.isNull(_cursorIndexOfBirthday)) {
          _tmpBirthday = null;
        } else {
          _tmpBirthday = _cursor.getString(_cursorIndexOfBirthday);
        }
        _result.setBirthday(_tmpBirthday);
        final String _tmpAvatar;
        if (_cursor.isNull(_cursorIndexOfAvatar)) {
          _tmpAvatar = null;
        } else {
          _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
        }
        _result.setAvatar(_tmpAvatar);
        final boolean _tmpAvailable;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfAvailable);
        _tmpAvailable = _tmp != 0;
        _result.setAvailable(_tmpAvailable);
        final String _tmpToken;
        if (_cursor.isNull(_cursorIndexOfToken)) {
          _tmpToken = null;
        } else {
          _tmpToken = _cursor.getString(_cursorIndexOfToken);
        }
        _result.setToken(_tmpToken);
      } else {
        _result = null;
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
