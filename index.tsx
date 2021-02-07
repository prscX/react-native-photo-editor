import { NativeModules } from 'react-native'

const { RNPhotoEditor } = NativeModules

export interface PhotoEditorProps {
    path: string
    colors?: string[]
    stickersTitle?: string[]
    stickers?: any[]
    hiddenControls?: ('text' | 'clear' | 'draw' | 'save' | 'share' | 'sticker' | 'crop')[]

    onDone?: (imagePath: string) => void
    onCancel?: (resultCode: number) => void
}

export default class PhotoEditor {
    private static defaultProps = {
        stickers: [],
        hiddenControls: [],
        stickersTitle: [],
        colors: [
            '#000000',
            '#808080',
            '#a9a9a9',
            '#FFFFFE',
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
    }

    static Edit({
        stickers,
        hiddenControls,
        colors,
        onDone,
        onCancel,
        stickersTitle,
        ...props
    }: PhotoEditorProps) {
        let mulptipleStickers = false;
        if (stickers === undefined) {
            stickers = this.defaultProps.stickers
        } else {
            mulptipleStickers = Array.isArray(stickers?.[0]);
        }
        if (hiddenControls === undefined) {
            hiddenControls = this.defaultProps.hiddenControls
        }
        if (colors === undefined) {
            colors = this.defaultProps.colors
        }
        if (stickersTitle === undefined) {
            stickersTitle = this.defaultProps.stickersTitle
        }

        RNPhotoEditor.Edit(
            { colors, hiddenControls, stickers, mulptipleStickers, stickersTitle, ...props },
            (imagePath: string) => {
                onDone && onDone(imagePath)
            },
            (resultCode: number) => {
                onCancel && onCancel(resultCode)
            }
        )
    }
}
