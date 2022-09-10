// IBookInterface.aidl
package com.xiaoyingbo.idle;

// Declare any non-default types here with import statements
parcelable Book;
interface IBookInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    oneway void setBook(in Book book);

    Book getBook();
}