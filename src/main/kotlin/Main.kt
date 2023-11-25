import java.lang.RuntimeException
import kotlin.reflect.typeOf

//import org.graalvm.compiler.asm.sparc.SPARCAssembler.Bpc

fun main() {
    val attachments = arrayListOf(
        AudioAttachment(),
        VideoAttachment()
    )
    val post1 = WallService.add(Post(text = "post1_text",
        copyHistory = null,
        attachments = attachments))
    val post2 = WallService.add(Post(text = "post2_text",
        copyHistory = null,
        attachments = attachments))
    val post2Updated = post2.copy(text = "post2_text_updated")
    WallService.update(post2Updated)
    WallService.createComment(1, Comment(text = "FirstComment"))
    WallService.createComment(2, Comment(text = "Comment to second post"))
    WallService.createComment(1, Comment(text = "SecondComment"))
    WallService.reportComment(1, 1, 7)
    WallService.read()
}

class PostNotFoundException(message: String) : RuntimeException(message)

class CommentNotFoundException(message: String) : RuntimeException(message)

class NonExistentReasonException(message: String) : RuntimeException(message)