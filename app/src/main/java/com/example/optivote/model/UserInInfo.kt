package com.example.optivote.model

object UserInInfo{
     var id : Long = 0
     var name:String=""
     var email:String=""
     var phone:String=""
     var password:String=""
     var image:String=""
     var signInId:String=""

     fun buildImageUrl(imageUrl: String): String {
          return imageUrl.replace("http://127.0.0.1", "http://192.168.50.69")

     }


}
