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