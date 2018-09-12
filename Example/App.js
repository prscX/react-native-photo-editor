
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  TouchableOpacity
} from 'react-native';
import resolveAssetSource from "react-native/Libraries/Image/resolveAssetSource";

import { RNPhotoEditor } from 'react-native-photo-editor'

import RNFS from 'react-native-fs'
import RNFetchBlob from 'rn-fetch-blob'

import photo from './assets/photo.jpg'

export default class App extends Component<Props> {
  constructor(props) {
    super(props)

    this.state = {
      visible: false
    }
  }

  _onPress() {
    let filter;
    if (Platform.OS === 'ios') {
      filter = [];
    } else if (Platform.OS === 'android') {
      filter = ".*\\.*";
    }

    RNPhotoEditor.Edit({
      path: RNFS.DocumentDirectoryPath + "/photo.jpg",
      stickers: [
        "sticker0",
        "sticker1",
        "sticker2",
        "sticker3",
        "sticker4",
        "sticker5",
        "sticker6",
        "sticker7",
        "sticker8",
        "sticker9",
        "sticker10"
      ],
      hiddenControls: [],
      colors: undefined
    });
  }

  componentDidMount() {
    let photoPath = RNFS.DocumentDirectoryPath + "/photo.jpg";
    let binaryFile = resolveAssetSource(photo)

    RNFetchBlob.config({ fileCache: true })
      .fetch("GET", binaryFile.uri)
      .then(resp => {
        RNFS.moveFile(resp.path(), photoPath)
          .then(success => {
            console.log("FILE WRITTEN!");
          })
          .catch(err => {
            console.log(err.message);
          });
      })
      .catch(err => {
        console.log(err.message);
      });
  }

  render() {
    return <View style={styles.container}>
      <TouchableOpacity onPress={() => {
        this._onPress()

        // this.setState({ visible: true });
      }}>
        <Text>Click</Text>
      </TouchableOpacity>
    </View>;
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  }
});