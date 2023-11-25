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