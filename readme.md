  Wendy Xu
  20631406 w85xu
  openjdk version "11.0.5" 2019-10-15
  macOS 10.14.5 (MacBook Pro 2017)
  Android 10.0 (Q) API 29 Revision 4 SDK

Load icon made by Good Ware from www.flaticon.com
Load set icon made by Freepik from www.flaticon.com
Clear icon made by Kiranshastry Lineal from www.flaticon.com
License: https://www.freepikcompany.com/legal#nav-flaticon-agreement

Notes:
    - To view all images during the grid view, swipe to the left of the filter rating bar
	  so that it is set to a rating of 0
    - Rating bars of images can be cleared (set to rating of 0) by swiping to the left on
 	  them
    - To return to the grid view of images after viewing a single fullscreen image by 
	  tapping on its thumbnail, simply use the Android's built-in back button
    - New images loaded are added to the end of the grid view (scroll down to see it)
	- If images are not showing up when added, please check for the following error 
	  under run:

		"java.net.SocketException: socket failed: EPERM (Operation not permitted)"

		Please uninstall app and reinstall.
		This error only occurred when I was heavily changing the code. It no longer
	    occurred after I completed the app and was testing it. So I will still put this
		here in case it happens. For some reason, uninstalling and reinstalling always
		fixed the error.

Sample code used:
    - 8.Android/6.MVC2/app/src/main/java/com/example/cs349/mvc2/MainActivity.java
	- 8.Android/6.MVC2/app/src/main/java/com/example/cs349/mvc2/SecondActivity.java
    - 8.Android/6.MVC2/app/src/main/java/com/example/cs349/mvc2/Model.java
    - 8.Android/1.HelloAndroid/app/src/main/AndroidManifest.xml
    - 8.Android/1.HelloAndroid/app/src/main/res/layout/activity_main.xml
	- 8.Android/1.HelloAndroid/app/src/main/res/layout/activity_second.xml
	- https://stackoverflow.com/questions/5776851/load-image-from-url
