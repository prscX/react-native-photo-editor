import React, { Component } from 'react'
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native'
import resolveAssetSource from 'react-native/Libraries/Image/resolveAssetSource'
import { RNPhotoEditor } from 'react-native-photo-editor'
import RNFS from 'react-native-fs'
import RNFetchBlob from 'rn-fetch-blob'

import photo from './assets/photo.jpg'

type Props = {
    colors?: Array<string>,
    hiddenControls?: Array<string>,
    onCancel?: any => void,
    onDone?: any => void,
    path: string,
    stickers?: Array<string>
}

export default class App extends Component<Props> {
    _onPress = () => {
        RNPhotoEditor.Edit({
          path: RNFS.DocumentDirectoryPath + '/photo.jpg',
          stickers: [
            'sticker0',
            'sticker1',
            'sticker2',
            'sticker3',
            'sticker4',
            'sticker5',
            'sticker6',
            'sticker7',
            'sticker8',
            'sticker9',
            'sticker10',
          ],
        //   hiddenControls: ['clear', 'crop', 'draw', 'save', 'share', 'sticker', 'text'],
          colors: undefined,
          onDone: () => {
            console.log('on done');
          },
          onCancel: () => {
            console.log('on cancel');
          },
        });
    }

    componentDidMount() {
        let photoPath = RNFS.DocumentDirectoryPath + '/photo.jpg'
        let binaryFile = resolveAssetSource(photo)

        RNFetchBlob.config({ fileCache: true })
            .fetch('GET', binaryFile.uri)
            .then(resp => {
                RNFS.moveFile(resp.path(), photoPath)
                    .then(() => {
                        console.log('FILE WRITTEN!')
                    })
                    .catch(err => {
                        console.log(err.message)
                    })
            })
            .catch(err => {
                console.log(err.message)
            })
    }

    render() {
        return (
            <View style={styles.container}>
                <TouchableOpacity onPress={this._onPress}>
                    <Text>Click</Text>
                </TouchableOpacity>
            </View>
        )
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF'
    }
})
