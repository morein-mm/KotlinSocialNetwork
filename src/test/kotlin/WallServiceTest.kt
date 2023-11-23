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
        val attachments = arrayListOf(
            AudioAttachment(),
            VideoAttachment()
        )
        val post1 = WallService.add(Post(text = "post1_text",
            copyHistory = null,
            attachments = attachments))
        assertNotEquals(0, post1.id)
    }

    @Test
    fun updateExistingPost() {
        val attachments = arrayListOf(
            AudioAttachment(),
            VideoAttachment()
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
        val attachments = arrayListOf(
            AudioAttachment(),
            VideoAttachment()
        )
        val post1 = WallService.add(Post(text = "post1_text",
            copyHistory = null,
            attachments = attachments))
        val postUpdated = post1.copy(id = 10, text = "post1_updatedText")
        val result = WallService.update(postUpdated)
        assertEquals(false, result)
    }

    @Test(expected = PostNotFoundException::class)
    fun createCommentShouldThrow() {
        WallService.createComment(1, Comment(text = "FirstComment"))
    }

    @Test
    fun createCommentShouldntThrow() {
        val post1 = WallService.add(Post(text = "post1_text",
            copyHistory = null,
            attachments = null))
        val result = WallService.createComment(1, Comment(text = "FirstComment")).id
        assertEquals(1, result)
    }
}