fun main() {
    val post1 = WallService.add(Post(text = "post1_text"))
    val post2 = WallService.add(Post(text = "post2_text"))
    val post2Updated = post2.copy(text = "post2_text_updated")
    WallService.update(post2Updated)
    WallService.read()

}

//ctrl+alt+l
data class Post(
    val id: Int = 0,
    val ownerID: Int = 0,
    val fromID: Int = 0,
    val createdBy: Int = 0,
    val text: String,
    val replyOwnerID: Int = 0,
    val replyPostID: Int = 0,
    val friendsOnly: Boolean = false,
    val likes: Likes = Likes(0, false, true, true),
    val postType: String = "post",
    val signerID: Int = 0
)

data class Likes(val count: Int, val userLikes: Boolean, val canLike: Boolean, val canPublish: Boolean)

object WallService {
    private var posts = emptyArray<Post>()
    private var nextID = 1

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
    }

    fun read() {
        for (post in posts) {
            println(post.id)
            println(post.text)
        }
    }

}
