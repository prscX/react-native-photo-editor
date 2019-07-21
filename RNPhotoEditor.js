import { NativeModules } from 'react-native'

const { RNPhotoEditor } = NativeModules

const defaultColors = [
    '#000000',
    '#808080',
    '#a9a9a9',
    '#FFFFFF',
    '#0000ff',
    '#00ff00',
    '#ff0000',
    '#ffff00',
    '#ffa500',
    '#800080',
    '#00ffff',
    '#a52a2a',
    '#ff00ff'
]

type Props = {
    colors?: Array<string>,
    hiddenControls?: Array<string>,
    onCancel?: any => void,
    onDone?: any => void,
    path: string,
    stickers?: Array<string>
}

export default function PhotoEditor(props: Props) {
    const {
        colors = defaultColors,
        hiddenControls = [],
        onCancel = () => {},
        onDone = () => {},
        path,
        stickers = []
    } = props
    RNPhotoEditor.Edit(
        {
            colors,
            hiddenControls,
            onCancel,
            onDone,
            path,
            stickers
        },
        onDone,
        onCancel
    )
}

export { PhotoEditor as RNPhotoEditor }
