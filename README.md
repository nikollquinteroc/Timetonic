# Timetonic

Timetonic is a technical assessment that consists of a book viewing app built with [Jetpack Compose](https://developer.android.com/jetpack/compose).

To try out this sample app, use the latest stable version
of [Android Studio](https://developer.android.com/studio).
You can clone this repository or import the
project from Android Studio following the steps
[here](https://developer.android.com/jetpack/compose/setup#sample).

This app showcases:

* Login screen
* Landing page screen: List of books
* Detail screen: A book

## Screenshots
![44shots_so](https://github.com/nikollquinteroc/Timetonic/assets/136136120/fc3f0ce2-c101-4f0b-a41f-32535193e05f)


## Technologies used

### Lenguajes y Frameworks

* Kotlin
* Jetpack Compose

### Loading images

* Coil

### Architecture

* MVVM (Model-View-ViewModel)

### Reactive Programming

* Coroutines
* Flows

### Networking
 
* Retrofit

### Version Control

* Git
* GitHub


## Data

The public API documentation can be found at [Timetonic API Documentation](https://timetonic.com/live/api.php?doc#getAllBooks_demo)

Use those endpoints in order to authenticate:

* /createAppkey
* /createOauthkey
* /createSesskey

Fetch the books from the API using the stored session token and using
this endpoint: 

* /getAllBooks
* /getBookInfo

### Test user

* Email: android.developer@timetonic.com
* Password: Android.developer1
