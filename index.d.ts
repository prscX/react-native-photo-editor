export interface PhotoEditorProps {
    path: string
    colors?: string[]
    stickers?: string[]
    hiddenControls?: ('text' | 'clear' | 'draw' | 'save' | 'share' | 'sticker' | 'crop')[]

    onDone?: (imagePath: string) => void
    onCancel?: (resultCode: number) => void
}

export default class PhotoEditor {
    static Edit({
                    stickers,
                    hiddenControls,
                    colors,
                    onDone,
                    onCancel,
                    ...props
                }: PhotoEditorProps): void
}
