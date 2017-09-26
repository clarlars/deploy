/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.os;

import android.annotation.UserIdInt;

/**
 * Representation of a user on the device.
 */
public final class UserHandle implements Parcelable {

    /** @hide An undefined user id */
    public static final @UserIdInt int USER_NULL = -10000;

    /** @hide A user id constant to indicate the "system" user of the device */
    public static final @UserIdInt int USER_SYSTEM = 0;

    /** @hide A user handle to indicate the "system" user of the device */
    public static final UserHandle SYSTEM = new UserHandle(USER_SYSTEM);

    final int mHandle;

    /**
     * Returns the user id for a given uid.
     * @hide
     */
    private static @UserIdInt int getUserId() {
        return UserHandle.USER_SYSTEM;
    }

    /** @hide */
    public static @UserIdInt int getCallingUserId() {
        return getUserId();
    }

    /** @hide */
    public UserHandle(int h) {
        mHandle = h;
    }

    @Override
    public String toString() {
        return "UserHandle{" + mHandle + "}";
    }

    @Override
    public boolean equals(Object obj) {
        try {
            if (obj != null) {
                UserHandle other = (UserHandle)obj;
                return mHandle == other.mHandle;
            }
        } catch (ClassCastException e) {
        }
        return false;
    }

    @Override
    public int hashCode() {
        return mHandle;
    }
    
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mHandle);
    }

    /**
     * Write a UserHandle to a Parcel, handling null pointers.  Must be
     * read with {@link #readFromParcel(Parcel)}.
     * 
     * @param h The UserHandle to be written.
     * @param out The Parcel in which the UserHandle will be placed.
     * 
     * @see #readFromParcel(Parcel)
     */
    public static void writeToParcel(UserHandle h, Parcel out) {
        if (h != null) {
            h.writeToParcel(out, 0);
        } else {
            out.writeInt(USER_NULL);
        }
    }
    
    /**
     * Read a UserHandle from a Parcel that was previously written
     * with {@link #writeToParcel(UserHandle, Parcel)}, returning either
     * a null or new object as appropriate.
     * 
     * @param in The Parcel from which to read the UserHandle
     * @return Returns a new UserHandle matching the previously written
     * object, or null if a null had been written.
     * 
     * @see #writeToParcel(UserHandle, Parcel)
     */
    public static UserHandle readFromParcel(Parcel in) {
        int h = in.readInt();
        return h != USER_NULL ? new UserHandle(h) : null;
    }
    
    public static final Parcelable.Creator<UserHandle> CREATOR
            = new Parcelable.Creator<UserHandle>() {
        public UserHandle createFromParcel(Parcel in) {
            return new UserHandle(in);
        }

        public UserHandle[] newArray(int size) {
            return new UserHandle[size];
        }
    };

    /**
     * Instantiate a new UserHandle from the data in a Parcel that was
     * previously written with {@link #writeToParcel(Parcel, int)}.  Note that you
     * must not use this with data written by
     * {@link #writeToParcel(UserHandle, Parcel)} since it is not possible
     * to handle a null UserHandle here.
     * 
     * @param in The Parcel containing the previously written UserHandle,
     * positioned at the location in the buffer where it was written.
     */
    public UserHandle(Parcel in) {
        mHandle = in.readInt();
    }
}
