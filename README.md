# android-moviedb

Client project for [The Movie Database API](https://developers.themoviedb.org/3) with upcoming movies list developed using **Android SDK**.

### Tools and Build
This project was built using Android Studio 2.3 RC 1 and Android Tools 2.3.0-rc1. To edit this project, is recomended to use the *Import Project* from Android Studio, since the local project settings are not commited to this repository.
The application can also be built using command line. In a machine with Java 8 or newer installed and configured, checkout the root project and run `gradlew assembleDebug`. The resulting APK can be found in `project/app/build/outputs/apk`.

### Third-party Libraries 
Some third-party libraries are used in the project to allow better development speed and smaller amount of code written. Those libraries are:

* [Android Support Library](https://developer.android.com/topic/libraries/support-library/index.html): Library for basic project structure and compatibility, used in almost any Android Project.
* [ConstraintLayout](https://developer.android.com/training/constraint-layout/index.html): New layout manager from Android SDK that allows faster development even for complex screen layouts. ConstraintLayout allows complex organization and measurement of it's children views. For example, in file `row_movie.xml`, an ImageView is defined as a *square* view by setting the proportion to `1:1`(property `app:layout_constraintDimensionRatio="h,1:1"`). Traditional layout managers don't offer this kind of option, requiring custom views or similar. This project uses the 1.0 version, the first stable from ConstraintLayout.
* [Retrofit](https://square.github.io/retrofit/): Library for consuming REST services in Android applications. Based on [OkHttp](http://square.github.io/okhttp/) network stack, Retrofit allows great flexibility and hability to connect converters and request adapters fast and easily.
* [Picasso](http://square.github.io/picasso/): Small and light library for image loading and caching, also based on [OkHttp](http://square.github.io/okhttp/) network stack.
* [RxJava](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid): Allows the use of [reactive extensions](http://reactivex.io/) in Android applications, allowing simplified multitasking but also powerful observable chains.
* [Butter Knife](http://jakewharton.github.io/butterknife/): Generates bindings for views, eliminating the need for `findViewById` calls in view layer.
* [AutoValue](https://github.com/google/auto/tree/master/value) and extensions [auto-value-gson](https://github.com/rharter/auto-value-gson), [auto-value-parcel](https://github.com/rharter/auto-value-parcel) and [auto-value-with](https://github.com/gabrielittner/auto-value-with): Build time code generators that remove the need for creating boilerplate code for value classes. The Gson extension also improve the REST integration performance, since adapters are generated and no reflection is made by Gson at runtime.
