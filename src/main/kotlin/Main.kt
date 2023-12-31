import java.lang.RuntimeException
import kotlin.reflect.typeOf

//import org.graalvm.compiler.asm.sparc.SPARCAssembler.Bpcc

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
    //WallService.read()
    WallService.createComment(1, Comment(text = "FirstComment"))
    WallService.createComment(2, Comment(text = "Comment to second post"))
    WallService.createComment(1, Comment(text = "SecondComment"))
    //WallService.createComment(10, Comment(text = "CommentToUnexistingPost"))
    WallService.reportComment(1, 1, 7)
    WallService.read()

}

//ctrl+alt+l
data class Post(
    val id: Int = 0,
    val ownerID: Int = 0,
    val fromID: Int = 0,
    val createdBy: Int = 0,
    val date: Int = 0,
    val text: String,
    val replyOwnerID: Int = 0,
    val replyPostID: Int = 0,
    val friendsOnly: Boolean = false,
    //val comments: Array<Comment>?,
    val likes: Likes = Likes(0, false, true, true),
    val reposts: Reposts = Reposts(),
    val views: Views = Views(),
    val postType: String = "post",
    //postSource - нет описания в документации
    val attachments: List<Attachment>?,
    val geo: Geo? = Geo(),
    val signerID: Int = 0,
    val copyHistory: Array<Post>?,
    val canPin: Boolean = true,
    val canDelete: Boolean = true,
    val canEdit: Boolean = true,
    val isPinned: Boolean = false,
    val markedAsAds: Boolean = false,
    val isFavourite: Boolean = false,
    val donut: Donut? = Donut(),
    val postponedID: Int = 0
)

data class Likes(
    val count: Int,
    val userLikes: Boolean,
    val canLike: Boolean,
    val canPublish: Boolean
)

data class Comment(
    val id: Int = 0,
    val fromID: Int = 0,
    val date: Int = 0,
    val text: String = "",
    val replyToUser: Int = 0,
)

data class Reposts(
    val count: Int = 0,
    val userReposted: Boolean = false
)

data class Views(
    val count: Int = 0
)

data class Geo(
    val type: String = "",
    val coordinates: String = "",
    val place: String = ""
)
data class Donut(
    val isDonut: Boolean = false,
    val paidDuration: Int = 0,
    val placeholder: String = "",
    val canPublishFreeCopy: Boolean = false,
    val editMode: String = ""
)

interface Attachment {
    val type: String;
}

data class AudioAttachment(
    override val type: String = "Audio",
    val audio: Audio = Audio()
): Attachment

data class Audio(
    val id: Int = 0,
    val ownerID: Int = 0,
    val artist: String = "",
    val title: String = "",
    val duration: Int = 0,
    val url: String = "",
    val lyricsID: Int = 0,
    val albumID: Int = 0,
    val genreID: Int = 0,
    val date: Int = 0,
    val noSearch: Boolean = false,
    val isHq: Boolean = false
)

data class PhotoAttachment(
    override val type: String = "Photo",
    val photo: Photo = Photo(sizes = null)
): Attachment

data class Photo(
    val id: Int = 0,
    val albumID: Int = 0,
    val ownerID: Int = 0,
    val userID: Int = 0,
    val text: String = "",
    val date: Int = 0,
    val sizes: Array<PhotoSizes>?,
    val width: Int = 0,
    val height: Int = 0
)

class PhotoSizes(
    val type: String = "",
    val url: String = "",
    val width: Int = 0,
    val height: Int = 0
)

data class VideoAttachment(
    override val type: String = "Video",
    val video: Video = Video(image = null, firstFrame = null)
): Attachment

data class Video(
    val id: Int = 0,
    val ownerID: Int = 0,
    val title: String = "",
    val description: String = "",
    val duration: Int = 0,
    val image: Array<VideoImage>?,
    val firstFrame: Array<VideoFirstFrame>?,
    val date: Int = 0,
    val addingDate: Int = 0,
    val views: Int = 0,
    val localViews: Int = 0,
    val comments: Int = 0,
    val player: String = "",
    val platform: String = "",
    val canAdd: Boolean = false,
    val isPrivate: Boolean = false,
    val accessKey: String = "",
    val isFavorite: Boolean = false,
    val canComment: Boolean = false,
    val canEdit: Boolean = false,
    val canLike: Boolean = false,
    val canRepost: Boolean = false,
    val canSubscribe: Boolean = false,
    val canAddToFaves: Boolean = false,
    val canAttachLink: Boolean = false,
    val width: Int = 0,
    val height: Int = 0,
    val userID: Int = 0,
    val converting: Boolean = false,
    val added: Boolean = false,
    val isSubscribed: Boolean = false,
    val repeat: Int = 0,
   // val type: String = "",
    val balance: Int = 0,
    val liveStatus: String = "",
)

class VideoImage(
    val height: Int = 0,
    val url: String = "",
    val width: Int = 0,
    val withPadding: Int = 0
)

class VideoFirstFrame(
    val height: Int = 0,
    val url: String = "",
    val width: Int = 0
)

data class ReportComment(
    val ownerID: Int,
    val commentID: Int,
    val reason: Int
)



object WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var nextID = 1
    private var nextCommentID = 1
    private var reportComments = emptyArray<ReportComment>()


    fun reportComment(ownerID: Int, commentID: Int, reason: Int) {
        getCommentByID(commentID)
        if (reason !in 0..8) {
            throw NonExistentReasonException("Non-existent reason")
        }
        reportComments += ReportComment(ownerID, commentID, reason)
    }

    fun getCommentByID(commentID: Int) : Comment {
        for ((index, comment) in comments.withIndex()) {
            if (comment.id == commentID) {
                return comment
            }
        }
        throw CommentNotFoundException("Comment not found")
    }

    fun getReportCommentsCount() : Int {
        return reportComments.count()
    }

    fun add(post: Post): Post {
        posts += post.copy(id = nextID)
        nextID++
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((index, postFromArray) in posts.withIndex()) {
            if (postFromArray.id == post.id) {
                posts[index] = post.copy()
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray()
        nextID = 1
        comments = emptyArray()
        nextCommentID = 1
        reportComments = emptyArray()
    }


    fun getPostByID(postID: Int) : Post {
        for ((index, post) in posts.withIndex()) {
            if (post.id == postID) {
                return post
            }
        }
        throw PostNotFoundException("Post not found")
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        val post = getPostByID(postId)
        comments += comment.copy(id = nextCommentID)
        nextCommentID++
        return comments.last()
   }


    fun read() {
        println("ПОСТЫ")
        for (post in posts) {
            println("id: " + post.id)
            println("текст: " + post.text)
            println("--------")
        }
        println()
        println("КОММЕНТАРИИ")
        for (comment in comments) {
            println("id: " + comment.id)
            println("текст: " + comment.text)
            println("--------")
        }
        println()
        println("РЕПОРТЫ")
        for (report in reportComments) {
            println("ownerId: " + report.ownerID)
            println("commentId: " + report.commentID)
            println("reason: " + report.reason)
            println("--------")
        }
    }

}

class PostNotFoundException(message: String) : RuntimeException(message)

class CommentNotFoundException(message: String) : RuntimeException(message)

class NonExistentReasonException(message: String) : RuntimeException(message)