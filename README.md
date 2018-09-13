<h1 align="center">

<p align="center">
  <img src="https://lh3.googleusercontent.com/dsJXfHnUx0qvZIB_80F-q0iN18eIqmx6g10bmsVN8R6nEnLQDKvJ9lXCbnPCgDEZMw=s180"/>
</p>

<p align="center">
  <a href="https://www.npmjs.com/package/react-native-photo-editor"><img src="http://img.shields.io/npm/v/react-native-photo-editor.svg?style=flat" /></a>
  <a href="https://github.com/prscX/react-native-photo-editor/pulls"><img alt="PRs Welcome" src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg" /></a>
  <a href="https://github.com/prscX/react-native-photo-editor#License"><img src="https://img.shields.io/npm/l/react-native-photo-editor.svg?style=flat" /></a>
</p>


    ReactNative: Native Photo Editor (Android/iOS)

If this project has helped you out, please support us with a star 🌟
</h1>
This library is a React Native bridge around native photo editor libraries. It allows you to edit any photo by providing below set of features:

- Cropping
- Adding Images -Stickers-
- Adding Text with Colors
- Drawing with Colors
- Scaling and Rotating Objects
- Deleting Objects
- Saving to Photos and Sharing
- Cool Animations

<img src="assets/hero.gif" />

## 📖 Getting started

`$ npm install react-native-photo-editor --save`

`$ react-native link react-native-photo-editor`

* Android
  * Please add below script in your build.gradle

```
buildscript {
    repositories {
        jcenter()
        maven { url "https://maven.google.com" }
        maven { url "https://jitpack.io" }
        ...
    }
}

allprojects {
    repositories {
        mavenLocal()
        jcenter()
        maven { url "https://maven.google.com" }
        maven { url "https://jitpack.io" }
        ...
    }
}
```

  * Please add below script in your app/build.gradle

```

android {
    ...

    defaultConfig {
        ...
        renderscriptSupportModeEnabled true
    }
}
```

> **Note:** Android SDK 27 > is supported

* iOS
    > **iOS Prerequisite:** Please make sure CocoaPods is installed on your system

  * After `react-native link react-native-photo-editor`, please verify `node_modules/react-native-photo-editor/ios/` contains `Pods` folder. If does not exist please execute `pod install` command on `node_modules/react-native-photo-editor/ios/`, if any error => try `pod repo update` then `pod install`
  * After verification, open your project and create a folder 'RNPhotoEditor' under Libraries.
  * Drag `node_modules/react-native-photo-editor/ios/pods/Pods.xcodeproject` into RNPhotoEditor, as well as the RNPhotoEditor.xcodeproject if it does not exist.
  * Add the `iOSPhotoEditor.framework` into your project's `Embedded Binaries` and make sure the framework is also in linked libraries.
  * Go to your project's `Build Settings -> Frameworks Search Path` and add `${BUILT_PRODUCTS_DIR}/iOSPhotoEditor` non-recursive.
  * Add below property to your info.list

```
	<key>NSPhotoLibraryAddUsageDescription</key>
	<string>Application needs permission to write photos...</string>
```

  * Now build your iOS app through Xcode

## 💻 Usage

import { RNPhotoEditor } from 'react-native-photo-editor'

* API Way

```
RNPhotoEditor.Edit({
    path: RNFS.DocumentDirectoryPath + "/photo.jpg"
});
```

* React Way

```
<RNPhotoEditor path={''} />
```

## 💡 Props

- **General(iOS & Android)**

| Prop                   | Type                | Default | Note                                             |
| ---------------------- | ------------------- | ------- | ------------------------------------------------ |
| `path: mandatory`     | `string`            |         | Specify image path you want to edit                 |
| `hiddenControls`                | `array`            |         | Specify editor controls you want to hide `[clear, crop, draw, save, share, sticker, text]`                        |
| `stickers`          | `array`            |         | Specify stickers you want to show in stickers picker                  |
| `colors`     | `array: HEX-COLOR` |    `[#000000, #808080, #a9a9a9, #FFFFFF, #0000ff, #00ff00, #ff0000, #ffff00, #ffa500, #800080, #00ffff, #a52a2a, #ff00ff]`     | Specify colors you want to show for draw/text              |
| `onDone`    | `func` |         | Specify done callback            |
| `onCancel`        | `func`            |      | Specify cancel callback       |


## ✨ Credits

- Android Photo Editor: [eventtus/photo-editor-android](https://github.com/eventtus/photo-editor-android)
- iOS Photo Editor: [eventtus/photo-editor](https://github.com/eventtus/photo-editor)

## 🤔 How to contribute
Have an idea? Found a bug? Please raise to [ISSUES](https://github.com/prscX/react-native-photo-editor/issues).
Contributions are welcome and are greatly appreciated! Every little bit helps, and credit will always be given.

## 💫 Where is this library used?
If you are using this library in one of your projects, add it in this list below. ✨


## 📜 License
This library is provided under the Apache 2 License.

RNPhotoEditor @ [prscX](https://github.com/prscX)

## 💖 Support my projects
I open-source almost everything I can, and I try to reply everyone needing help using these projects. Obviously, this takes time. You can integrate and use these projects in your applications for free! You can even change the source code and redistribute (even resell it).

However, if you get some profit from this or just want to encourage me to continue creating stuff, there are few ways you can do it:
* Starring and sharing the projects you like 🚀
* If you're feeling especially charitable, please follow [prscX](https://github.com/prscX) on GitHub.

  <a href="https://www.buymeacoffee.com/prscX" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>

  Thanks! ❤️
  <br/>
  [prscX.github.io](https://prscx.github.io)
  <br/>
  </ Pranav >
