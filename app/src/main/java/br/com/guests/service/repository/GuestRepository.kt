package br.com.guests.service.repository

import android.content.ContentValues
import android.content.Context
import br.com.guests.service.constants.DatabaseConstants
import br.com.guests.service.model.GuestModel

class GuestRepository private constructor(context: Context){

    private var mGuestDataBaseHelper: GuestDataBaseHelper = GuestDataBaseHelper(context)

    companion object{

        private lateinit var repository: GuestRepository

        fun getInstance(context: Context) : GuestRepository{
            //se não foi inicializado, inicialize agora
            if(!::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun getAll() : List<GuestModel>{
        val list: MutableList<GuestModel> = ArrayList()
        val db = mGuestDataBaseHelper.readableDatabase

        val projection = arrayOf(
            DatabaseConstants.GUEST.COLUMNS.ID,
            DatabaseConstants.GUEST.COLUMNS.NAME,
            DatabaseConstants.GUEST.COLUMNS.PRESENCE
        )

        val cursor = db.query(DatabaseConstants.GUEST.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        if(cursor != null && cursor.count > 0){
           while(cursor.moveToNext()){
               val name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME))
               val presence = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)) == 1
               val id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID))

               list.add(GuestModel(id, name, presence))
           }
        }

        cursor?.close()
        return list
    }

    fun getPresent() : List<GuestModel>{
        val list: MutableList<GuestModel> = ArrayList()

        val db = mGuestDataBaseHelper.readableDatabase

        val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null)

        if(cursor != null && cursor.count > 0){
            while(cursor.moveToNext()){
                val name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME))
                val presence = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)) == 1
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID))

                list.add(GuestModel(id, name, presence))
            }
        }

        cursor?.close()
        return list
    }

    fun getAbsent() : List<GuestModel>{
        val list: MutableList<GuestModel> = ArrayList()

        val db = mGuestDataBaseHelper.readableDatabase

        val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 0", null)

        if(cursor != null && cursor.count > 0){
            while(cursor.moveToNext()){
                val name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME))
                val presence = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)) == 1
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID))

                list.add(GuestModel(id, name, presence))
            }
        }

        cursor?.close()
        return list
    }

    fun get(id: Int) : GuestModel? {

        var guest: GuestModel? = null

            val db = mGuestDataBaseHelper.readableDatabase

            val projection = arrayOf(
                DatabaseConstants.GUEST.COLUMNS.NAME,
                DatabaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DatabaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(DatabaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            if(cursor != null && cursor.count > 0){
                cursor.moveToFirst()
                val name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME))
                val presence = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)) == 1

                guest = GuestModel(id, name, presence)

            }

            cursor?.close()
            return guest

    }

    //CRUD

    fun save(guest: GuestModel) : Boolean{
      return try{
          val db = mGuestDataBaseHelper.writableDatabase
          val contentValues = ContentValues()
          contentValues.put(DatabaseConstants.GUEST.COLUMNS.NAME, guest.name)
          contentValues.put(DatabaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)

          db.insert(DatabaseConstants.GUEST.TABLE_NAME, null, contentValues)
          true
      }catch(e:Exception){
          false
      }
    }

    fun update(guest: GuestModel) : Boolean{
        return try{
            val db = mGuestDataBaseHelper.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValues.put(DatabaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)

            val selection = DatabaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DatabaseConstants.GUEST.TABLE_NAME, contentValues, selection, args)
            true
        }catch(e:Exception){
            false
        }
    }

    fun delete(id: Int) : Boolean{
        return try{
            val db = mGuestDataBaseHelper.writableDatabase
            val selection = DatabaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DatabaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        }catch(e:Exception){
            false
        }
    }
}