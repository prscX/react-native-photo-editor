
#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif

#import <UIKit/UIKit.h>
@import iOSPhotoEditor;

@interface RNPhotoEditor : NSObject <RCTBridgeModule, PhotoEditorDelegate>

@end
