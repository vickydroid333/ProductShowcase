package com.vickydroid.productshowcase.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ImagePath(
    val square: String,
    val wide: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(square)
        parcel.writeString(wide)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImagePath> {
        override fun createFromParcel(parcel: Parcel): ImagePath {
            return ImagePath(parcel)
        }

        override fun newArray(size: Int): Array<ImagePath?> {
            return arrayOfNulls(size)
        }
    }
}

@Entity(tableName = "product")
data class Product(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String?,
    val price: Double?,
    val availableLanguages: List<String>?,
    val sampleReports: Map<String, String>?,
    val imagePath: ImagePath? // ImagePath remains the same
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.createStringArrayList(),
        parcel.readHashMap(String::class.java.classLoader) as? Map<String, String>,
        parcel.readParcelable(ImagePath::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeValue(price)
        parcel.writeStringList(availableLanguages)
        parcel.writeMap(sampleReports)
        parcel.writeParcelable(imagePath, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}



data class ProductResponse(
    @SerializedName("products")
    val products: Map<String, Product>
)



