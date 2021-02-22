# permissions-coroutine

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
 
 Step 3. If you want to use a specific module put in your Gradle file permissions which you are interested in from the list listed below
 
          implementation 'com.github.mishamoovex.permissions-coroutine:base:VESION'
          implementation 'com.github.mishamoovex.permissions-coroutine:location:VESION'
          implementation 'com.github.mishamoovex.permissions-coroutine:locationProvider:VESION'
          implementation 'com.github.mishamoovex.permissions-coroutine:camera:VESION'
