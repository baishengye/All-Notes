package com.bo.a1_counter.activity;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUser;

  private final SharedSQLiteStatement __preparedStmtOfDeleteUserByLastName;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `User`(`firstName`,`lastName`,`age`,`id`) VALUES (?,?,?,nullif(?, 0))";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        if (value.getFirstName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getFirstName());
        }
        if (value.getLastName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getLastName());
        }
        stmt.bindLong(3, value.getAge());
        stmt.bindLong(4, value.getId());
      }
    };
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `User` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `User` SET `firstName` = ?,`lastName` = ?,`age` = ?,`id` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        if (value.getFirstName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getFirstName());
        }
        if (value.getLastName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getLastName());
        }
        stmt.bindLong(3, value.getAge());
        stmt.bindLong(4, value.getId());
        stmt.bindLong(5, value.getId());
      }
    };
    this.__preparedStmtOfDeleteUserByLastName = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from User where lastName=?";
        return _query;
      }
    };
  }

  @Override
  public long insertUser(final User user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfUser.insertAndReturnId(user);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteUser(final User user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfUser.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateUser(final User user) {
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
  public int deleteUserByLastName(final String lastName) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteUserByLastName.acquire();
    int _argIndex = 1;
    if (lastName == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, lastName);
    }
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteUserByLastName.release(_stmt);
    }
  }

  @Override
  public List<User> loadAllUsers() {
    final String _sql = "select * from User";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
      final int _cursorIndexOfLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "lastName");
      final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final User _item;
        final String _tmpFirstName;
        _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
        final String _tmpLastName;
        _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
        final int _tmpAge;
        _tmpAge = _cursor.getInt(_cursorIndexOfAge);
        _item = new User(_tmpFirstName,_tmpLastName,_tmpAge);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<User> loadUsersOrderThan(final int age) {
    final String _sql = "select * from User where age>?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, age);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
      final int _cursorIndexOfLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "lastName");
      final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final User _item;
        final String _tmpFirstName;
        _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
        final String _tmpLastName;
        _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
        final int _tmpAge;
        _tmpAge = _cursor.getInt(_cursorIndexOfAge);
        _item = new User(_tmpFirstName,_tmpLastName,_tmpAge);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
