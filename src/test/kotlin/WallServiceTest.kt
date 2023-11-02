import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun add() {
        val post1 = WallService.add(Post(text = "post1_text"))
        assertNotEquals(0, post1.id)
    }

    @Test
    fun updateExistingPost() {
        val post1 = WallService.add(Post(text = "post1_text"))
        val postUpdated = post1.copy(text = "post1_updatedText")
        val result = WallService.update(postUpdated)
        assertEquals(true, result)
    }

    @Test
    fun updateNonExistingPost() {
        val post1 = WallService.add(Post(text = "post1_text"))
        val postUpdated = post1.copy(id = 10, text = "post1_updatedText")
        val result = WallService.update(postUpdated)
        assertEquals(false, result)
    }
}