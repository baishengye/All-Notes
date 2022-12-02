package com.jinxin.paging.boundarycallback.db;

import android.database.Cursor;
import androidx.paging.DataSource;
import androidx.paging.DataSource.Factory;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.paging.LimitOffsetDataSource;
import androidx.room.util.CursorUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.jinxin.paging.boundarycallback.model.User;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final SharedSQLiteStatement __preparedStmtOfClear;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `user` (`id`,`name`,`avatar`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.id);
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
        if (value.avatar == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.avatar);
        }
      }
    };
    this.__preparedStmtOfClear = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from user";
        return _query;
      }
    };
  }

  @Override
  public void insertUsers(final List<User> users) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser.insert(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void clear() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfClear.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfClear.release(_stmt);
    }
  }

  @Override
  public DataSource.Factory<Integer, User> getUserList() {
    final String _sql = "select * from user";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new DataSource.Factory<Integer, User>() {
      @Override
      public LimitOffsetDataSource<User> create() {
        return new LimitOffsetDataSource<User>(__db, _statement, false , "user") {
          @Override
          protected List<User> convertRows(Cursor cursor) {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(cursor, "id");
            final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(cursor, "name");
            final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(cursor, "avatar");
            final List<User> _res = new ArrayList<User>(cursor.getCount());
            while(cursor.moveToNext()) {
              final User _item;
              _item = new User();
              _item.id = cursor.getInt(_cursorIndexOfId);
              _item.name = cursor.getString(_cursorIndexOfName);
              _item.avatar = cursor.getString(_cursorIndexOfAvatar);
              _res.add(_item);
            }
            return _res;
          }
        };
      }
    };
  }
}
