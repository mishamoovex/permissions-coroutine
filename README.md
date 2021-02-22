# Android app permission checks based on kotlin-coroutine and android ActivityResultAPI

DOC references:
 
ActivityResultAPI: https://developer.android.com/training/basics/intents/result
  
Coroutines: https://developer.android.com/kotlin/coroutines?gclid=CjwKCAiAyc2BBhAaEiwA44-wW2rA4cGUwJftu-1qS9asWGR9KfhLWBTiyV62wwkmcInGu2cBknIKhhoCMAUQAvD_BwE&gclsrc=aw.ds

Current vesion: 1.0.0

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.mishamoovex:permissions-coroutine:VERSION'
	}
 
 Spet 3. You should add user-permissions into the Android app manifest file
 
 Step 4. If you want to use a specific module put in your Gradle file permissions which you are interested in from the list listed below
 
          implementation 'com.github.mishamoovex.permissions-coroutine:base:VESION'
          implementation 'com.github.mishamoovex.permissions-coroutine:location:VESION'
          implementation 'com.github.mishamoovex.permissions-coroutine:locationProvider:VESION'
          implementation 'com.github.mishamoovex.permissions-coroutine:camera:VESION
	 
NOTE!!!  You should use these coroutine-based permission checks only in OnCreate(Activity) OnViewCreated(Fragment) methods. Check the sample app to see how to integrate it into your project together with the Jetpack lifecycle components.
