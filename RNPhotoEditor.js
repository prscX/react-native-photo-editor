import React, { PureComponent } from "react";
import { ViewPropTypes, NativeModules, Platform } from "react-native";
import PropTypes from "prop-types";

const { RNPhotoEditor } = NativeModules;

class PhotoEditor extends PureComponent {
  static propTypes = {
    ...ViewPropTypes,

    path: PropTypes.string,
    stickers: PropTypes.array,
    controls: PropTypes.array,
    colors: PropTypes.array,

    onDone: PropTypes.func,
    onCancel: PropTypes.func
  };

  static defaultProps = {
    stickers: [],
    hiddenControls: [],
    colors: [
      "#000000",
      "#808080",
      "#a9a9a9",
      "#FFFFFE",
      "#0000ff",
      "#00ff00",
      "#ff0000",
      "#ffff00",
      "#ffa500",
      "#800080",
      "#00ffff",
      "#a52a2a",
      "#ff00ff"
    ]
  };

  static Edit(props) {
    if (props.stickers === undefined)
      props.stickers = PhotoEditor.defaultProps.stickers;
    if (props.hiddenControls === undefined) props.hiddenControls = PhotoEditor.defaultProps.hiddenControls;
    if (props.colors === undefined) props.colors = PhotoEditor.defaultProps.colors

    RNPhotoEditor.Edit(
      props,
      (...args) => {
        props.onDone && props.onDone(...args);
      },
      (...args) => {
        props.onCancel && props.onCancel(...args);
      }
    );
  }
}

export { PhotoEditor as RNPhotoEditor }