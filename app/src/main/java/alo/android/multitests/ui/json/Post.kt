package alo.android.multitests.ui.json

class Post {
    var postHeading: String? = null
    var postUrl: String? = null
    var postAuthor: String? = null
    var postTag: List<String>? = null
    
    constructor() : super() {}
    
    constructor(PostHeading: String, PostUrl: String, PostAuthor: String, tags: List<String>) : super() {
        this.postHeading = PostHeading
        this.postUrl = PostUrl
        this.postAuthor = PostAuthor
        this.postTag = tags
    }
    
}

data class MyPost(var postHeading: String? = null,
                  var postUrl: String? = null,
                  var postAuthor: String? = null,
                  var postTag: List<String>? = null)
