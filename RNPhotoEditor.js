import React, { PureComponent } from "react";
import { ViewPropTypes, NativeModules, Platform } from "react-native";
import PropTypes from "prop-types";

const { RNPhotoEditor } = NativeModules;

class PhotoEditor extends PureComponent {
  static propTypes = {
    ...ViewPropTypes,

    path: PropTypes.string,

    onDone: PropTypes.func,
    onCancel: PropTypes.func
  };

  static defaultProps = {};

  static Edit(props) {
    RNPhotoEditor.Edit(props, path => {
        props.onDone && props.onDone(path);
      }, () => {
        props.onCancel && props.onCancel();
      });
  }

//   componentDidMount() {
//     this._show();
//   }

//   componentDidUpdate() {
//     this._show();
//   }

//   _show() {
//     if (this.props.visible) {
//       PhotoEditor.Show({
//         filter: this.props.filter,
//         filterDirectories: this.props.filterDirectories,
//         path: this.props.path,
//         hiddenFiles: this.props.hiddenFiles,
//         closeMenu: this.props.closeMenu,
//         title: this.props.title,
//         editable: this.props.editable,
//         onDone: this.props.onDone,
//         onCancel: this.props.onCancel
//       });
//     }
//   }

//   render() {
//     return null;
//   }
}

export { PhotoEditor as RNPhotoEditor }
