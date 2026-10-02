package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.DesktopItemEntity
import com.example.data.model.FolderEntity
import com.example.data.model.LauncherConfigEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LauncherDao {

    @Query("SELECT * FROM desktop_items WHERE folderId IS NULL ORDER BY page ASC, cellY ASC, cellX ASC")
    fun getRootDesktopItems(): Flow<List<DesktopItemEntity>>

    @Query("SELECT * FROM desktop_items WHERE folderId = :folderId ORDER BY cellY ASC, cellX ASC")
    fun getItemsInFolder(folderId: Long): Flow<List<DesktopItemEntity>>

    @Query("SELECT * FROM desktop_items WHERE folderId = :folderId")
    suspend fun getItemsInFolderSync(folderId: Long): List<DesktopItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDesktopItem(item: DesktopItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDesktopItems(items: List<DesktopItemEntity>)

    @Update
    suspend fun updateDesktopItem(item: DesktopItemEntity)

    @Query("DELETE FROM desktop_items WHERE id = :id")
    suspend fun deleteDesktopItem(id: Long)

    @Query("DELETE FROM desktop_items WHERE folderId = :folderId")
    suspend fun deleteItemsByFolderId(folderId: Long)

    // Folders
    @Query("SELECT * FROM folders ORDER BY page ASC, cellY ASC, cellX ASC")
    fun getAllFolders(): Flow<List<FolderEntity>>

    @Query("SELECT * FROM folders WHERE id = :id LIMIT 1")
    suspend fun getFolderById(id: Long): FolderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolder(folder: FolderEntity): Long

    @Update
    suspend fun updateFolder(folder: FolderEntity)

    @Query("DELETE FROM folders WHERE id = :id")
    suspend fun deleteFolder(id: Long)

    // Config
    @Query("SELECT * FROM launcher_config WHERE id = 1 LIMIT 1")
    fun getLauncherConfig(): Flow<LauncherConfigEntity?>

    @Query("SELECT * FROM launcher_config WHERE id = 1 LIMIT 1")
    suspend fun getLauncherConfigSync(): LauncherConfigEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setLauncherConfig(config: LauncherConfigEntity)
}
