package com.szymonstasik.kalkulatorsredniejwazonej

import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverage
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverageDao
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverageDatabase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class WeightedAverageDatabaseTest {

    private lateinit var weightedAverageDao: WeightedAverageDao
    private lateinit var db: WeightedAverageDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, WeightedAverageDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        weightedAverageDao = db.weightedAverageDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetWeightedAverage() {
        val weightedAverage = WeightedAverage()
        weightedAverage.name = "test"
        weightedAverageDao.insert(weightedAverage)
        var weightedAverageFromDb = weightedAverageDao.getWeightedAverageWithId(weightedAverage.id)
        Assert.assertEquals(weightedAverageFromDb.name, "test")
    }
}