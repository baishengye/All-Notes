package com.jinxin.mvvm.db;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.jinxin.mvvm.model.User;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __deletionAdapterOfUser;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `user` (`id`,`name`,`avatar`,`followers`,`following`,`blog`,`company`,`bio;`,`location;`,`htmlUrl;`) VALUES (?,?,?,?,?,?,?,?,?,?)";
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
        stmt.bindLong(4, value.followers);
        stmt.bindLong(5, value.following);
        if (value.blog == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.blog);
        }
        if (value.company == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.company);
        }
        if (value.bio == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.bio);
        }
        if (value.location == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.location);
        }
        if (value.htmlUrl == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.htmlUrl);
        }
      }
    };
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `user` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.id);
      }
    };
  }

  @Override
  public void insertUser(final User user) {
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
  public void deleteStudent(final User user) {
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
  public LiveData<User> getUserByName(final String name) {
    final String _sql = "select * from user where name = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"user"}, false, new Callable<User>() {
      @Override
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
          final int _cursorIndexOfFollowers = CursorUtil.getColumnIndexOrThrow(_cursor, "followers");
          final int _cursorIndexOfFollowing = CursorUtil.getColumnIndexOrThrow(_cursor, "following");
          final int _cursorIndexOfBlog = CursorUtil.getColumnIndexOrThrow(_cursor, "blog");
          final int _cursorIndexOfCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "company");
          final int _cursorIndexOfBio = CursorUtil.getColumnIndexOrThrow(_cursor, "bio;");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location;");
          final int _cursorIndexOfHtmlUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "htmlUrl;");
          final User _result;
          if(_cursor.moveToFirst()) {
            _result = new User();
            _result.id = _cursor.getInt(_cursorIndexOfId);
            _result.name = _cursor.getString(_cursorIndexOfName);
            _result.avatar = _cursor.getString(_cursorIndexOfAvatar);
            _result.followers = _cursor.getInt(_cursorIndexOfFollowers);
            _result.following = _cursor.getInt(_cursorIndexOfFollowing);
            _result.blog = _cursor.getString(_cursorIndexOfBlog);
            _result.company = _cursor.getString(_cursorIndexOfCompany);
            _result.bio = _cursor.getString(_cursorIndexOfBio);
            _result.location = _cursor.getString(_cursorIndexOfLocation);
            _result.htmlUrl = _cursor.getString(_cursorIndexOfHtmlUrl);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
