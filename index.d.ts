export interface PhotoEditorProps {
    path: string
    stickersTitle?: string[]
    mulptipleStickers: boolean
    colors?: string[]
    stickers?: any[]
    hiddenControls?: ('text' | 'clear' | 'draw' | 'save' | 'share' | 'sticker' | 'crop')[]

    onDone?: (imagePath: string) => void
    onCancel?: (resultCode: number) => void
}

export default class PhotoEditor {
    static Edit({
                    stickers,
                    hiddenControls,
                    mulptipleStickers,
                    stickersTitle,
                    colors,
                    onDone,
                    onCancel,
                    ...props
                }: PhotoEditorProps): void
}
