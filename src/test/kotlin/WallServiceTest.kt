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
        val attachments = mapOf(
            Audio() to AudioAttachment(id=1),
            Video() to VideoAttachment(id=1, image=null, firstFrame = null)
        )
        val post1 = WallService.add(Post(text = "post1_text",
            copyHistory = null,
            attachments = attachments))
        assertNotEquals(0, post1.id)
    }

    @Test
    fun updateExistingPost() {
        val attachments = mapOf(
            Audio() to AudioAttachment(id=1),
            Video() to VideoAttachment(id=1, image=null, firstFrame = null)
        )
        val post1 = WallService.add(Post(text = "post1_text",
            copyHistory = null,
            attachments = attachments))
        val postUpdated = post1.copy(text = "post1_updatedText")
        val result = WallService.update(postUpdated)
        assertEquals(true, result)
    }

    @Test
    fun updateNonExistingPost() {
        val attachments = mapOf(
            Audio() to AudioAttachment(id=1),
            Video() to VideoAttachment(id=1, image=null, firstFrame = null)
        )
        val post1 = WallService.add(Post(text = "post1_text",
            copyHistory = null,
            attachments = attachments))
        val postUpdated = post1.copy(id = 10, text = "post1_updatedText")
        val result = WallService.update(postUpdated)
        assertEquals(false, result)
    }
}