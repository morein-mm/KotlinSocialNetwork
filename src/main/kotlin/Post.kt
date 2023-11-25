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
    val likes: Likes = Likes(0, false, true, true),
    val reposts: Reposts = Reposts(),
    val views: Views = Views(),
    val postType: String = "post",
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