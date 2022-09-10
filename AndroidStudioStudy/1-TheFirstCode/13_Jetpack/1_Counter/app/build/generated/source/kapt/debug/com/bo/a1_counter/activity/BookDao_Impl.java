package com.bo.a1_counter.activity;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class BookDao_Impl implements BookDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfBook;

  public BookDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBook = new EntityInsertionAdapter<Book>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Book`(`name`,`pages`,`author`,`id`) VALUES (?,?,?,nullif(?, 0))";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Book value) {
        if (value.getName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getName());
        }
        stmt.bindLong(2, value.getPages());
        if (value.getAuthor() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAuthor());
        }
        stmt.bindLong(4, value.getId());
      }
    };
  }

  @Override
  public long insertBook(final Book book) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfBook.insertAndReturnId(book);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Book> loadAllBooks() {
    final String _sql = "select * from Book";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfPages = CursorUtil.getColumnIndexOrThrow(_cursor, "pages");
      final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final List<Book> _result = new ArrayList<Book>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Book _item;
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final int _tmpPages;
        _tmpPages = _cursor.getInt(_cursorIndexOfPages);
        final String _tmpAuthor;
        _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
        _item = new Book(_tmpName,_tmpPages,_tmpAuthor);
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
